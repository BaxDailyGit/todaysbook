function postAddressAndMileageInfo() {
    // 받으시는 분의 정보와 우편번호, 주소, 상세주소 값을 가져옴
    var user = document.getElementById('user').value;
    var postcode = document.getElementById('postcode').value;
    var address = document.getElementById('address').value;
    var detailAddress = document.getElementById('detailAddress').value;
    var usedMileage = document.querySelector('.mileage-input').value.replace(/,/g, '');
    var totalPrice = document.getElementById("totalPriceDisplay").value.replace(/,/g, '').replace('원', '');



    // JSON 형식으로 데이터를 구성
    var dataToSend = {
        user: user,
        postcode: postcode,
        address: address,
        detailAddress: detailAddress,
        usedMileage: usedMileage,
        totalPrice: totalPrice
    };

    fetch('/payment/card', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dataToSend)
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            window.location.href = data; // 페이지를 리다이렉트할 URL로 이동
        })
        .catch(error => {
            alert(error.message); // 오류 메시지를 경고창으로 표시
        });
}