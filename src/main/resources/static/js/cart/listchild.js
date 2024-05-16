
// document.addEventListener("DOMContentLoaded", function () {
//     // 페이지 로딩 시 총 상품 가격과 배송료를 업데이트합니다.
//     updateTotalPrice();
// });
//
// // 총 주문 금액과 배송료를 업데이트하는 함수
// function updateTotalPrice() {
//     var totalPriceElement = document.getElementById("totalOrderAmount");
//     var totalPrice = parseInt(totalPriceElement.textContent.replace('원', ''));
//
//     var deliveryFeeElement = document.getElementById("deliveryFee");
//     var deliveryFee = totalPrice >= 20000 ? "무료" : "3000원";
//     deliveryFeeElement.textContent = deliveryFee;
//
//     var totalPriceDisplayElement = document.getElementById("totalPriceDisplay");
//     var totalPriceWithDelivery = totalPrice + (deliveryFee === "무료" ? 0 : 3000);
//     totalPriceDisplayElement.textContent = totalPriceWithDelivery + "원";
// }
//
//
// //마일리지
//
//
//
//
// ///
// // 최대 사용 가능한 마일리지 비율 (총 상품 가격의 30%)
// const MAX_MILEAGE_RATIO = 0.3;
//
// // 마일리지 입력란에 입력이 발생할 때마다 호출되는 함수
// function handleMileageInput() {
//     // 마일리지 입력란에서 입력된 값을 가져옴
//     let mileageInput = document.getElementById("mileage-input");
//     let usedMileage = parseInt(mileageInput.value);
//
//     // 보유 마일리지를 가져옴
//     let totalMileageElement = document.getElementById("mileage");
//     let totalMileage = parseFloat(totalMileageElement.textContent.replace('M', ''));
//
//     // 결제 금액을 가져옴
//     let totalPriceElement = document.getElementById("totalOrderAmount");
//     let totalPrice = parseInt(totalPriceElement.textContent.replace('원', ''));
//
//     // 배송료를 가져옴
//     let deliveryChargeElement = document.getElementById("deliveryFee");
//     let deliveryCharge = parseInt(deliveryChargeElement.textContent.replace('원', ''));
//
//     // 최대 사용 가능한 마일리지 계산 (총 상품 가격의 30%)
//     let maxMileage = totalPrice * MAX_MILEAGE_RATIO;
//
//     // 사용된 마일리지가 보유 마일리지를 초과하는 경우
//     if (usedMileage > totalMileage) {
//         alert("보유 마일리지를 초과합니다!");
//         // 마일리지 입력란을 초기화합니다.
//         mileageInput.value = '';
//         usedMileage = 0; // 사용된 마일리지를 0으로 설정
//     }
//
//     // 사용된 마일리지가 최대 사용 가능한 마일리지를 초과하는 경우
//     if (usedMileage > maxMileage) {
//         alert("최대 사용 가능한 마일리지를 초과합니다!");
//         // 마일리지 입력란을 초기화합니다.
//         mileageInput.value = '';
//         usedMileage = 0; // 사용된 마일리지를 0으로 설정
//     }
//
//     // 사용마일리지가 총 상품가격보다 같거나 큰 경우 알림 표시
//     if (usedMileage >= totalPrice) {
//         alert("사용마일리지는 총 상품 가격보다 작아야 합니다.");
//         // 마일리지 입력란을 초기화합니다.
//         mileageInput.value = '';
//         usedMileage = 0; // 사용된 마일리지를 0으로 설정
//     }
//
//     // 배송료가 무료인 경우에는 0으로 설정
//     if (isNaN(deliveryCharge)) {
//         deliveryCharge = 0;
//     }
//
//     // 총 결제 금액을 계산 (마일리지 적용 후 배송료 포함)
//     let totalPriceAfterMileage = isNaN(usedMileage) ? totalPrice + deliveryCharge : totalPrice - usedMileage + deliveryCharge;
//
//     // 총 결제 금액을 업데이트
//     let totalPriceDisplayElement = document.getElementById("totalPriceDisplay");
//     totalPriceDisplayElement.textContent = totalPriceAfterMileage + "원";
// }
//
// // 마일리지 입력란에 이벤트 리스너를 추가
// let mileageInput = document.getElementById("mileage-input");
// mileageInput.addEventListener('input', handleMileageInput);
//
// // 전액 사용 체크박스의 상태에 따라 처리하는 함수
// function handleUseAllMileageCheckbox() {
//     // 전액 사용 체크박스의 상태를 가져옴
//     let useAllMileageCheckbox = document.getElementById("use-all-mileage-checkbox");
//     let isChecked = useAllMileageCheckbox.checked;
//
//     // 보유 마일리지를 가져옴
//     let totalMileageElement = document.getElementById("mileage");
//     let totalMileage = parseFloat(totalMileageElement.textContent.replace('M', ''));
//
//     // 결제 금액을 가져옴
//     let totalPriceElement = document.getElementById("totalOrderAmount");
//     let totalPrice = parseInt(totalPriceElement.textContent.replace('원', ''));
//
//     // 최대 사용 가능한 마일리지 계산 (총 상품 가격의 30%)
//     let maxMileage = totalPrice * MAX_MILEAGE_RATIO;
//
//     // 만약 체크박스가 체크되어 있다면,
//     if (isChecked) {
//         // 보유 마일리지가 총 상품 가격의 30%보다 작은 경우
//         if (totalMileage < maxMileage) {
//             // 보유 마일리지를 모두 적용
//             let mileageInput = document.getElementById("mileage-input");
//             mileageInput.value = totalMileage;
//         } else {
//             // 최대 사용 가능한 마일리지를 입력
//             let mileageInput = document.getElementById("mileage-input");
//             mileageInput.value = maxMileage;
//         }
//
//         // 마일리지 입력 이벤트를 발생시켜 결제 금액을 업데이트
//         handleMileageInput();
//     } else {
//         // 체크박스가 체크되어 있지 않다면, 마일리지 입력란을 비웁니다.
//         let mileageInput = document.getElementById("mileage-input");
//         mileageInput.value = '';
//
//         // 마일리지 입력 이벤트를 발생시켜 결제 금액을 업데이트
//         handleMileageInput();
//     }
// }
//
// // 전액 사용 체크박스에 이벤트 리스너를 추가
// let useAllMileageCheckbox = document.getElementById("use-all-mileage-checkbox");
// useAllMileageCheckbox.addEventListener('change', handleUseAllMileageCheckbox);
//

// 총 주문 금액과 배송료를 업데이트하는 함수
function updateTotalPrice() {
    var totalPriceElement = document.getElementById("totalOrderAmount");
    var totalPrice = parseInt(totalPriceElement.textContent.replace('원', '').replace(/,/g, ''));
    totalPriceElement.textContent = numberWithCommas(totalPrice) + "원";


    var deliveryFeeElement = document.getElementById("deliveryFee");
    var deliveryFeeText = calculateDeliveryFee(totalPrice);
    var deliveryFee = deliveryFeeText === "무료" ? 0 : parseInt(deliveryFeeText.replace('원', '').replace(/,/g, ''));
    deliveryFeeElement.textContent = numberWithCommas(deliveryFee) + "원";

    var totalPriceDisplayElement = document.getElementById("totalPriceDisplay");
    var totalPriceWithDelivery = totalPrice + deliveryFee;
    totalPriceDisplayElement.value = numberWithCommas(totalPriceWithDelivery) + "원";



    // 보유 마일리지를 가져옴
    let totalMileageElement = document.getElementById("mileage");
    let totalMileage = parseInt(totalMileageElement.textContent.replace('M', '').replace(/,/g, ''));
    totalMileageElement.textContent = numberWithCommas(totalMileage)+'M';

    var priceElements = document.getElementsByClassName("price");
    for (var i = 0; i < priceElements.length; i++) {
        var priceText = priceElements[i].textContent.replace('원', '').replace(/,/g, '');
        priceElements[i].textContent = numberWithCommas(priceText) + "원";
    }

}
function calculateDeliveryFee(totalPrice) {
    return totalPrice >= 20000 ? "무료" : "3,000원";
}
// 최대 사용 가능한 마일리지 비율 (총 상품 가격의 30%)
const MAX_MILEAGE_RATIO = 0.3;

// 마일리지 입력란에 입력이 발생할 때마다 호출되는 함수
function handleMileageInput() {
    // 마일리지 입력란에서 입력된 값을 가져옴
    let mileageInput = document.getElementById("mileage-input");
    let usedMileage = parseInt(mileageInput.value.replace(/,/g, ''));

    // 컴마 추가
    if (!isNaN(usedMileage)) {
        mileageInput.value = numberWithCommas(usedMileage);
    }

    // 보유 마일리지를 가져옴
    let totalMileageElement = document.getElementById("mileage");
    let totalMileage = parseInt(totalMileageElement.textContent.replace('M', '').replace(/,/g, ''));
    totalMileageElement.textContent = numberWithCommas(totalMileage)+'M';

    // 결제 금액을 가져옴
    let totalPriceElement = document.getElementById("totalOrderAmount");
    let totalPrice = parseInt(totalPriceElement.textContent.replace('원', '').replace(/,/g, ''));
    totalPriceElement.textContent = numberWithCommas(totalPrice)+'원';

    // 배송료를 가져옴
    let deliveryChargeElement = document.getElementById("deliveryFee");
    let deliveryCharge = parseInt(deliveryChargeElement.textContent.replace('원', '').replace(/,/g, ''));
    deliveryChargeElement.textContent = numberWithCommas(deliveryCharge)+'원';

    // 최대 사용 가능한 마일리지 계산 (총 상품 가격의 30%)
    let maxMileage = totalPrice * MAX_MILEAGE_RATIO;

    // 사용된 마일리지가 보유 마일리지를 초과하는 경우
    if (usedMileage > totalMileage) {
        alert("보유 마일리지를 초과합니다!");
        // 마일리지 입력란을 초기화합니다.
        mileageInput.value = numberWithCommas(totalMileage);
        usedMileage = totalMileage; // 사용된 마일리지를 보유 마일리지로 설정
    }

    // 사용된 마일리지가 최대 사용 가능한 마일리지를 초과하는 경우
    if (usedMileage > maxMileage) {
        alert("최대 사용 가능한 마일리지를 초과합니다!");
        // 마일리지 입력란을 초기화합니다.
        mileageInput.value = numberWithCommas(maxMileage);
        usedMileage = maxMileage; // 사용된 마일리지를 최대 사용 가능 마일리지로 설정
    }

    // 사용마일리지가 총 상품가격보다 같거나 큰 경우 알림 표시
    if (usedMileage >= totalPrice) {
        alert("사용마일리지는 총 상품 가격보다 작아야 합니다.");
        // 마일리지 입력란을 초기화합니다.
        mileageInput.value = '';
        usedMileage = 0; // 사용된 마일리지를 0으로 설정
    }

    // 배송료가 무료인 경우에는 0으로 설정
    if (isNaN(deliveryCharge)) {
        deliveryCharge = 0;
    }

    // 총 결제 금액을 계산 (마일리지 적용 후 배송료 포함)
    let totalPriceAfterMileage = isNaN(usedMileage) ? totalPrice + (deliveryCharge) : totalPrice - usedMileage + (deliveryCharge);

    // 총 결제 금액을 업데이트
    let totalPriceDisplayElement = document.getElementById("totalPriceDisplay");
    totalPriceDisplayElement.value = numberWithCommas(totalPriceAfterMileage) + "원";
}

// 마일리지 입력란에 이벤트 리스너를 추가
let mileageInput = document.getElementById("mileage-input");
mileageInput.addEventListener('input', handleMileageInput);
mileageInput.addEventListener("keydown", function (event){

    if(event.key === "-") {
        event.preventDefault();
    }
});

// 전액 사용 체크박스의 상태에 따라 처리하는 함수
function handleUseAllMileageCheckbox() {
    // 전액 사용 체크박스의 상태를 가져옴
    let useAllMileageCheckbox = document.getElementById("use-all-mileage-checkbox");
    let isChecked = useAllMileageCheckbox.checked;

    // 보유 마일리지를 가져옴
    // 보유 마일리지를 가져옴
    let totalMileageElement = document.getElementById("mileage");
    let totalMileage = parseInt(totalMileageElement.textContent.replace('M', '').replace(/,/g, ''));
    totalMileageElement.textContent = numberWithCommas(totalMileage);

    // 결제 금액을 가져옴
    let totalPriceElement = document.getElementById("totalOrderAmount");
    let totalPrice = parseInt(totalPriceElement.textContent.replace('원', '').replace(/,/g, ''));
    totalPriceElement.textContent = numberWithCommas(totalPrice);

    // 최대 사용 가능한 마일리지 계산 (총 상품 가격의 30%)
    let maxMileage = totalPrice * MAX_MILEAGE_RATIO;

    // 만약 체크박스가 체크되어 있다면,
    if (isChecked) {
        // 보유 마일리지가 총 상품 가격의 30%보다 작은 경우
        if (totalMileage < maxMileage) {
            // 보유 마일리지를 모두 적용
            let mileageInput = document.getElementById("mileage-input");
            mileageInput.value = numberWithCommas(totalMileage);
        } else {
            // 최대 사용 가능한 마일리지를 입력
            let mileageInput = document.getElementById("mileage-input");
            mileageInput.value = numberWithCommas(maxMileage);
        }

        // 마일리지 입력 이벤트를 발생시켜 결제 금액을 업데이트
        handleMileageInput();
    } else {
        // 체크박스가 체크되어 있지 않다면, 마일리지 입력란을 비웁니다.
        let mileageInput = document.getElementById("mileage-input");
        mileageInput.value = '';

        // 마일리지 입력 이벤트를 발생시켜 결제 금액을 업데이트
        handleMileageInput();
    }
}

// 전액 사용 체크박스에 이벤트 리스너를 추가
let useAllMileageCheckbox = document.getElementById("use-all-mileage-checkbox");
useAllMileageCheckbox.addEventListener('change', handleUseAllMileageCheckbox);

// 콤마 추가 함수
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

document.addEventListener("DOMContentLoaded", function () {
    // 페이지 로딩 시 총 상품 가격과 배송료를 업데이트합니다.
    updateTotalPrice();
});

