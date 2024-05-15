document.addEventListener('DOMContentLoaded', starEvents);

function reviewWordCount(textarea){

    const commentForm = textarea.closest('.review-form');
    const commentTextCount = commentForm.querySelector('.comment-text-count');

    countBytes(textarea, commentTextCount, 500);
}

function editReviewWordCount(textarea){

    const commentForm = textarea.closest('.edit-text-area-div');

    const commentTextCount = commentForm.querySelector('.comment-text-count');
    countBytes(textarea, commentTextCount, 500);
}

function clickLike(button, reviewId) {

    button.classList.toggle('liked');
    button.parentElement.classList.toggle('on');

    let url = button.classList.contains('liked') ? '/review/add_like' : '/review/delete_like';

    let type = button.classList.contains('liked') ? 'POST' : 'DELETE';
    let message = button.classList.contains('liked') ?
        '추천 완료' : '추천 취소 완료';


    $.ajax({

        type: type,
        url: url,
        data: {
            reviewId: reviewId
        },
        success: function (response) {

            alert(message);

            let newLikeCount = response;
            let likeCountElement = button.parentElement.querySelector('.like-count');
            likeCountElement.innerHTML = newLikeCount;

            starEvents();
        },
        error: function (error) {

            alert("로그인이 필요한 기능입니다.");
            location.href="/login";
        }
    });
}

function clickDislike(button, reviewId) {

    button.classList.toggle('disliked');
    button.parentElement.classList.toggle('on');

    let url = button.classList.contains('disliked') ? '/review/add_dislike' : '/review/delete_dislike';

    let type = button.classList.contains('disliked') ? 'POST' : 'DELETE';
    let message = button.classList.contains('disliked') ?
        '비추천 완료' : '비추천 취소 완료';

    $.ajax({

        type: type,
        url: url,
        data: {
            reviewId: reviewId
        },
        success: function (response) {

            alert(message);

            let newDislikeCount = response;
            let dislikeCountElement = button.parentElement.querySelector('.dislike-count');
            dislikeCountElement.innerHTML = newDislikeCount;

            starEvents();
        },
        error: function (error) {

            alert("로그인이 필요한 기능입니다.");
            location.href="/login";
        }
    });
}

function clickAddReview(button) {

    const url = "/review/add";
    const method = "POST";

    const reviewItem = button.closest('.review-form');

    const content = reviewItem.querySelector('.review-input').value;
    const bookId = document.querySelector('.book-id').value;
    const score = parseInt(reviewItem.querySelector('.review-score').textContent);

    if (content.trim() === "") {
        alert("리뷰 내용을 입력해주세요.");
        return false;
    }

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({
            content: content,
            bookId: bookId,
            score: score
        }),
        success: function (data) {
            alert("리뷰가 등록 되었습니다.");
            $('.review-area').html(data);

            starEvents();
        },
        error: function (error) {
            if (error.status === 400) {
                alert("Bad Request: "+ error.responseText);
            }else if(error.status === 401){
                alert("Unauthorized: "+error.responseText);
                location.href="/login";
            }else{
                alert("error: "+error.responseText);
            }
        }
    });
}

function deleteReview(button) {

    const url = "/review/delete";
    const method = "DELETE";

    const reviewItem = button.closest('.review');

    const bookId = document.querySelector('.book-id').value;
    const reviewId = reviewItem.querySelector('.review-id').value;

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({
            bookId: bookId,
            reviewId: reviewId
        }),
        success: function (data) {
            alert("리뷰가 삭제 되었습니다.");
            $('.review-area').html(data);

            starEvents();
        },
        error: function (error) {
            if (error.status === 400) {
                alert("Bad Request: "+ error.responseText);
            }else if(error.status === 401){
                alert("Unauthorized: "+error.responseText);
                location.href="/login";
            }else{
                alert("error: "+error.responseText);
            }
        }
    });
}

function editReview(button) {

    const reviewItem = button.closest('.review');
    const btnDiv = reviewItem.querySelector('.edit-button-div');
    const reviewContent = reviewItem.querySelector('pre');
    const reviewScoreDiv = reviewItem.querySelector('.review-score-div');
    const editScoreDiv = reviewItem.querySelector('.edit-score-div');

    const content = reviewContent.textContent;

    const editTextDiv = reviewItem.querySelector('.edit-text-area-div');

    reviewScoreDiv.style.display = 'none';
    editScoreDiv.style.display = 'flex';

    reviewContent.style.display = 'none';
    btnDiv.style.display= 'none';
    editTextDiv.style.display = 'flex';

    const editTextArea = editTextDiv.querySelector('.edit-review-input');
    editTextArea.textContent = content;
    editReviewWordCount(editTextArea);
}

function editReviewCancel(button) {

    const reviewItem = button.closest('.review');
    const btnDiv = reviewItem.querySelector('.edit-button-div');
    const reviewContent = reviewItem.querySelector('pre');

    const reviewScoreDiv = reviewItem.querySelector('.review-score-div');
    const editScoreDiv = reviewItem.querySelector('.edit-score-div');
    const editTextDiv = reviewItem.querySelector('.edit-text-area-div');

    reviewScoreDiv.style.display = 'flex';
    editScoreDiv.style.display = 'none';

    reviewContent.style.display = 'block';
    btnDiv.style.display= 'flex';
    editTextDiv.style.display = 'none';
}

function updateReview(button) {

    const url = "/review/update";
    const method = "PUT";

    const reviewItem = button.closest('.review');

    const bookId = document.querySelector('.book-id').value;
    const reviewId = reviewItem.querySelector('.review-id').value;
    const content = reviewItem.querySelector('.edit-review-input').value;
    const score = parseInt(reviewItem.querySelector('.edit-review-score').textContent);

    if (content.trim() === "") {
        alert("리뷰 내용을 입력해주세요.");
        return false;
    }

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({
            bookId: bookId,
            content: content,
            reviewId: reviewId,
            score: score
        }),
        success: function (data) {
            alert("리뷰가 수정 되었습니다.");
            $('.review-area').html(data);

            starEvents();
        },
        error: function (error) {
            if (error.status === 400) {
                alert("Bad Request: "+ error.responseText);
            }else if(error.status === 401){
                alert("Unauthorized: "+error.responseText);
            }else{
                alert("error: "+error.responseText);
            }
        }
    });
}

function reviewOrderBy(orderBy) {

    const url = "/review";
    const method = "GET";

    const bookId = document.querySelector('.book-id').value;

    $.ajax({
        url: url,
        type: method,
        data: {
            bookId: bookId,
            orderBy: orderBy
        },
        success: function (data) {
            $('.review-area').html(data);

            starEvents();
        },
        error: function (error) {
            throw new Error('불러오기 실패');
        }
    });
}

function starEvents() {

    const reviewStars = document.querySelectorAll('.review-star');
    const reviewScore = document.querySelector('.review-score');
    let isReviewClicked = false;
    let previousReviewValue = 0;

    reviewStars.forEach(star => {
        star.addEventListener('mousedown', function () {
            isReviewClicked = true;
            const value = this.getAttribute('data-value');
            if (value === previousReviewValue) {
                reviewScore.textContent = '0';
                highlightStars(reviewStars, 0);
                previousReviewValue = 0;
            } else {
                reviewScore.textContent = value;
                highlightStars(reviewStars, value);
                previousReviewValue = value;
            }
        });

        star.addEventListener('mouseover', function () {
            if (isReviewClicked) {
                const value = this.getAttribute('data-value');
                reviewScore.textContent = value;
                highlightStars(reviewStars, value);
            }
        });

        star.addEventListener('mouseup', function () {
            isReviewClicked = false;
        });
    });

    const editReviewStars = document.querySelectorAll('.edit-review-star');
    const editReviewScore = document.querySelector('.edit-review-score');
    let isEditClicked = false;

    if(editReviewScore) {

        let previousEditValue = editReviewScore.textContent;

        highlightStars(editReviewStars, previousEditValue);

        editReviewStars.forEach(star => {
            star.addEventListener('mousedown', function () {
                isEditClicked = true;
                const value = this.getAttribute('data-value');
                if (value === previousEditValue) {
                    editReviewScore.textContent = '0';
                    highlightStars(editReviewStars, 0);
                    previousEditValue = 0;
                } else {
                    editReviewScore.textContent = value;
                    highlightStars(editReviewStars, value);
                    previousEditValue = value;
                }
            });

            star.addEventListener('mouseover', function () {
                if (isEditClicked) {
                    const value = this.getAttribute('data-value');
                    editReviewScore.textContent = value;
                    highlightStars(editReviewStars, value);
                }
            });

            star.addEventListener('mouseup', function () {
                isEditClicked = false;
            });
        });
    }
}

function highlightStars(stars, value) {
    stars.forEach(star => {
        if (parseInt(star.getAttribute('data-value')) <= value) {
            star.src = '/images/star.png';
        } else {
            star.src = '/images/blank_star.png';
        }
    });
}
