function postAddressAndMileageInfo() {

    let user = document.getElementById('user').value;
    let postcode = document.getElementById('postcode').value;
    let address = document.getElementById('address').value;
    let detailAddress = document.getElementById('detailAddress').value;
    let usedMileage = document.querySelector('.mileage-input').value.replace(/,/g, '');
    let totalPrice = document.getElementById("totalPriceDisplay").value.replace(/,/g, '').replace('원', '');

    let dataToSend = {
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
                return response.text().then(text => {
                    throw new Error(text)
                });
            }
            return response.text();
        })
        .then(data => {
            window.location.href = data;
        })
        .catch(error => {
            alert(error.message);
        });
}