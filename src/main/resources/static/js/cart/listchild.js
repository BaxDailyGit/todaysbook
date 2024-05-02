document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로딩 시 총 상품 가격과 배송료를 업데이트합니다.
    updateTotalPrice();
});

// 총 주문 금액과 배송료를 업데이트하는 함수
function updateTotalPrice() {
    var totalPriceElement = document.getElementById("totalOrderAmount");
    var totalPrice = parseInt(totalPriceElement.textContent.replace('원', ''));

    var deliveryFeeElement = document.getElementById("deliveryFee");
    var deliveryFee = totalPrice >= 20000 ? "무료" : "3000원";
    deliveryFeeElement.textContent = deliveryFee;

    var totalPriceDisplayElement = document.getElementById("totalPriceDisplay");
    var totalPriceWithDelivery = totalPrice + (deliveryFee === "무료" ? 0 : 3000);
    totalPriceDisplayElement.textContent = totalPriceWithDelivery + "원";
}