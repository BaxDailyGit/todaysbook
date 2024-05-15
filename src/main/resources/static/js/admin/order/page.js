function clickNextPageButton(endPage, keyword) {
    const currentUrl = window.location.href;

    const date = getQueryParam('date');

    if (currentUrl.includes("date=")) {
        location.href="/admin/order?date="+ date + "&page=" + endPage;
    }else{
        location.href = "/admin/order?page=" + endPage;

    }
}

function clickPrevPageButton(startPage, keyword) {
    const currentUrl = window.location.href;

    const date = getQueryParam('date');

    if (currentUrl.includes("date=")) {
        location.href="/admin/order?date="+ date + "&page=" + (startPage - 6);
    }else{
        location.href = "/admin/order?page=" + (startPage - 6);

    }
}

function clickPageButton(button, keyword) {

    const page = button.textContent;
    const date = getQueryParam('date');
    const currentUrl = window.location.href;

    if (currentUrl.includes("date=")) {
        location.href="/admin/order?date="+ date + "&page=" + (page - 1);
    }else {
        location.href = "/admin/order?page=" + (page - 1);

    }
}