function deleteSelectedCartItems() {
    var unselectedItems = document.querySelectorAll('.cart-list-body input[type="checkbox"]:not(:checked)');
    var unselectedIds = [];
    unselectedItems.forEach(function(item) {
        unselectedIds.push(item.value);
    });

    if (unselectedIds.length === 0) {
        alert('삭제할 상품을 선택해주세요.');
        return;
    }

    if (confirm('선택되지 않은 품목들은 삭제하시겠습니까?')) {
        fetch('/cart/delete-unselected', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // JSON 형식으로 데이터 전송을 명시
            },
            body: JSON.stringify(unselectedIds) // JSON 형식으로 데이터 변환
        })
            .then(function(response) {
                if (!response.ok) {
                    throw Error(response.statusText);
                }
                return response.json();
            })
            .then(function(data) {
                if (data.success) {
                    window.location.reload();
                } else {
                    alert('삭제 중 오류가 발생했습니다.');
                }
            })
            .catch(function(error) {
                console.error('Error:', error);
                alert('서버 오류가 발생했습니다.');
            });
    }
}

//총 금액 실시간 업데이트

// 페이지 로딩 시 전체 상품 선택 및 총 주문 금액 초기화
document.addEventListener("DOMContentLoaded", function() {
    var allCheckBox = document.querySelectorAll('.cart-list-body input[type="checkbox"]');
    allCheckBox.forEach(function(item) {
        item.checked = true; // 모든 체크박스 선택
    });
    updateTotalPrice();
});


// 체크박스 상태 변경 시 총 주문 금액 업데이트
document.querySelectorAll('.cart-list-body input[type="checkbox"]').forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        updateTotalPrice();
    });
});

var totalPrice = 0;
var deliveryFee = "";
function updateTotalPrice() {
    var totalPriceElement = document.querySelector('.cart-list-bottom p:first-child');
    /*var */totalPrice = 0;

    // 체크된 상품의 가격을 합산하여 총 주문 금액 계산
    document.querySelectorAll('.cart-list-body input[type="checkbox"]:checked').forEach(function (item) {
        var cartItem = item.parentElement.parentElement; // 각 상품 리스트 아이템
        var itemPrice = parseInt(cartItem.querySelector('.price').innerText.replace('원', '')); // 상품 가격
        // var itemQuantity = parseInt(cartItem.querySelector('.quantity').innerText); // 상품 수량
        totalPrice += itemPrice
    });

    // 총 주문 금액을 업데이트하여 화면에 표시
    totalPriceElement.innerText = '총 주문 금액 ' + totalPrice + '원';


    // 총 주문 금액에 따른 배송료 업데이트
    var deliveryFeeElement = document.getElementById("deliveryFee");
    if (totalPrice >= 20000) {
        deliveryFeeElement.textContent = "무료";
    } else {
        deliveryFeeElement.textContent = "3000원";
    }
}

function deleteSelectedCartItemsIfNotChecked() {
    var selectedItems = document.querySelectorAll('.cart-list-body input[type="checkbox"]:checked');
    var allItems = document.querySelectorAll('.cart-list-body input[type="checkbox"]');

    if (selectedItems.length !== allItems.length) {
        if (confirm('선택되지 않은 품목은 삭제해주세요.')) {
            return true;
        } else {
            return false;
        }
    }
    // 모든 아이템이 선택되었을 경우에는 바로 주문 페이지로 이동
    location.href = '/payment/info';
}