function clickFavorite(button) {

    const image = button.querySelector('img');

    image.classList.toggle('favorite');

    let url = image.classList.contains('favorite') ? '/favorite_book/add' : '/favorite_book/delete';
    let bookId = document.querySelector('.book-id').value;

    let type = image.classList.contains('favorite') ? 'POST' : 'DELETE';
    let message = image.classList.contains('favorite') ?
        '찜하기 목록에 추가 되었습니다.' : '찜하기 목록에서 제거 되었습니다.';

    $.ajax({

        type: type,
        url: url,
        data: {
            bookId: bookId
        },
        success: function (response) {

            alert(message);
        },
        error: function (error) {

            alert("로그인이 필요한 기능입니다.");
            location.href="/login";
        }
    });
}

function addCart(button) {

    const bookId = button.dataset.id;
    const count = document.querySelector('.count-input').value;

    const url = "/cart/add";
    const method = "POST";

    $.ajax({

        url: url,
        type: method,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({
            bookId: bookId,
            count: count
        }),
        success: function (data) {
            const goToCart = confirm('장바구니에 상품이 추가되었습니다. 장바구니로 이동하시겠습니까?');
            if (goToCart) {
                location.href = "/cart/list";
            }
        },
        error: function (error) {

            if(error.status === 401) {
                alert(error.responseText);
                location.href="/login";
            } else {
                alert(error.responseText);
            }
        }
    });
}