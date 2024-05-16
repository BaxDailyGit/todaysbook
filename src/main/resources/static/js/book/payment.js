function checkStock(bookId) {
    fetch('/payment/stock/' + bookId)
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 실패');
            }
            return response.json();
        })
        .then(data => {
            // 서버에서 받은 재고 정보를 처리합니다.
            var stock = data.stock;
            console.log("재고: " + stock);

            // bookName 추출
            var bookName = document.querySelector('.title').textContent.trim();

            // quantity 추출 (예시에서는 수량 입력란에 입력된 값을 가져옵니다)
            var quantity = document.querySelector('.count-input').value;

            if (stock == 0) {
                alert('품절 상태 입니다');
                return;
            } else if (quantity == 0) {
                alert('수량을 확인해 주세요');
                return;
            } else if (quantity > stock) {
                alert('현재 남아 있는 재고는 ' + stock + '개 입니다.\n'
                    + '남아 있는 책을 모두 구매하시려면 ' + (quantity - stock) + '만큼 줄여 주세요');
                return;
            }

            // price 추출
            var priceText = document.querySelector('.price').textContent.trim();
            var price = parseInt(priceText.replace(/[,원]/g, ''));

            let role = document.querySelector('#role').value;

            const selectedBook = [];
            selectedBook.push({
                bookId: bookId,
                cartBookId: 0,
                bookName: bookName,
                quantity: quantity,
                price: price * quantity,
                mileage: setMileage(price * quantity, role)
            });

            // 서버로 선택된 책 정보를 전송합니다. (여기서는 fetch를 사용하여 POST 요청으로 보냅니다.)
            fetch('/payment/info', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(selectedBook)
            })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(msg => {
                            alert(msg);
                        })
                    } else {
                        return response.text();
                    }
                }) // 서버로부터의 응답 body를 텍스트로 읽어옴
                .then(data => {
                    if (data) {
                        window.location.href = data; // 페이지를 리다이렉트할 URL로 이동
                    }else{
                        window.location.href = "/login";
                    }
                })
                .catch(error => {
                    // 오류가 발생한 경우 처리합니다.
                    console.error('Error sending selected books to server:', error);
                });
            // 여기서 재고(stock)에 대한 작업을 수행할 수 있습니다.
        })
        .catch(error => {
            // 에러 처리 코드를 여기에 추가합니다.
            console.error("서버 요청 중 에러 발생:", error);
        });


}

function purchaseItem() {
    var bookId = document.querySelector('.book-id').value;
    checkStock(bookId);
}

function setMileage(price, role) {

    var mileageRate;

    switch (role) {
        case 'ROLE_BRONZE':
            mileageRate = 0.03;
            break;
        case 'ROLE_SILVER':
            mileageRate = 0.05;
            break;
        case 'ROLE_GOLD':
            mileageRate = 0.07;
            break;
        case 'ROLE_DIAMOND':
            mileageRate = 0.10;
            break;
        default:
            mileageRate = 0.03; // 기본값은 브론즈
            break;
    }

    return price * mileageRate;
}