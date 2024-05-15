function clickNextPageButton(endPage, keyword) {

    location.href = "/mypage/review?page=" + endPage;

}

function clickPrevPageButton(startPage, keyword) {

    location.href = "/mypage/review?page=" + (startPage - 6);

}

function clickPageButton(button, keyword) {

    const page = button.textContent;

    location.href = "/mypage/review?page=" + (page - 1);
}

