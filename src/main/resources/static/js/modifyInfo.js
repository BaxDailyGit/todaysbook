function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById('address').value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('detailAddress').focus();
        }
    }).open();
}

function checkNicknameAvailability(nickname) {
    return fetch('/checkNicknameAvailability?nickname=' + encodeURIComponent(nickname), {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
}

function updateNickname(newNickname) {
    const requestBody = {
        nickName: newNickname
    };

    fetch('/user/update/nickname', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => {
                    if(response.status === 401) {
                        alert(msg);
                    } else if(response.status === 400) {
                        alert(msg);
                        throw new Error(msg);
                    } else if(response.status === 404) {
                        alert(msg);
                    }
                });
            } else {
                return response.text();
            }
        })
        .then(data => {

        })
        .catch(function(error) {
            console.error('Error:', error);
        });
}

function updateAddressInfo(address, detailAddress, zipcode) {
    const requestBody = {
        address: address + ", " + detailAddress,
        zipcode: zipcode
    };

    fetch('/user/update/addressInfo', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => {
                    if(response.status === 401) {
                        alert(msg);
                    } else if(response.status === 400) {
                        alert(msg);
                        throw new Error(msg);
                    } else if(response.status === 404) {
                        alert(msg);
                    }
                });
            } else {
                return response.text();
            }
        })
        .then(data => {

        })
        .catch(function(error) {
            console.error(error);
        });
}

let nicknameAvailability = false;

document.addEventListener('DOMContentLoaded', function () {
    const nicknameButton = document.querySelectorAll('.button')[0];
    const inputList = document.querySelectorAll('.input');

    nicknameButton.addEventListener('click', function(event) {
        event.preventDefault();

        const nickname = inputList[0].value;

        if(nickname.length > 8) {
            alert('닉네임의 길이는 최대 8글자입니다.');
            inputList[0].focus();
        } else if(nickname.length < 2 || nickname === '') {
            alert('닉네임의 길이는 최소 2글자입니다.');
            inputList[0].focus();
        } else if(nickname.length !== 0) {
            checkNicknameAvailability(nickname)
                .then(function (data) {
                    if (data.hasOwnProperty('available')) {
                        if (data.available) {
                            alert('사용 가능한 닉네임입니다.');
                            nicknameAvailability = true;
                        } else {
                            alert('이미 사용 중인 닉네임입니다.');
                            inputList[0].value = null;
                            inputList[0].focus();
                        }
                    } else {
                        throw new Error('Invalid response data');
                    }
                })
                .catch(function (error) {
                    console.error('회원 정보 수정 중 오류가 발생하였습니다.\n' + error.message);
                });
        }
    });
});

const submitButton = document.querySelector('.submit');
submitButton.addEventListener('click', function (event) {
    event.preventDefault();
    const inputList = document.querySelectorAll('.input');

    if (Array.from(inputList).some(input => input.value.trim() === '')) {
        alert('빈 칸을 빠짐없이 작성해주세요');
        return;
    }

    if (!nicknameAvailability) {
        alert('닉네임 중복 확인을 해주세요.');
        return;
    }

    updateNickname(inputList[0].value);
    updateAddressInfo(inputList[1].value, inputList[2].value, inputList[3].value);

    alert('회원 정보가 변경되었습니다.');
    window.location.href = '/';
});
