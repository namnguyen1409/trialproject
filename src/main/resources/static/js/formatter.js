formatPhone = (phone) => {
    return phone.replace(/(\d{4})(\d{3})(\d{3})/, '$1 $2 $3');
}

$('.phone').each(function() {
    $(this).text(formatPhone($(this).text()));
});

formatDate = (date) => {
    let d = new Date(date);
    return d.toLocaleDateString('vi-VN');
}

$('.date').each(function() {
    $(this).text(formatDate($(this).text()));
});

formatDateTime = (dateTime) => {
    let d = new Date(dateTime);
    return d.toLocaleString('vi-VN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });
}

$('.dateTime').each(function() {
    $(this).text(formatDateTime($(this).text()));
});


formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
};

$('.price').each(function() {
    let priceText = $(this).text().trim();
    let priceValue = parseFloat(priceText.replace(/\D/g, ''));
    if (!isNaN(priceValue)) {
        $(this).text(formatPrice(priceValue));
    }
});
