function clickNextPageButton(endPage, keyword) {

    location.href = "/mypage/orderlist?page=" + endPage;

}

function clickPrevPageButton(startPage, keyword) {

    location.href = "/mypage/orderlist?page=" + (startPage - 6);

}

function clickPageButton(button, keyword) {

    const page = button.textContent;

    location.href = "/mypage/orderlist?page=" + (page - 1);
}

