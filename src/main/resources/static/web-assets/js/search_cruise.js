document.getElementById('searchButton').addEventListener('click', function () {
    // Tạo URL cơ bản và mảng tham số
    const baseUrl = '/search-cruise';
    const queryParams = [`page=1`, `size=5`];

    // Lấy giá trị từ ô tìm kiếm
    const searchInput = document.querySelector('.input-search');
    const title = searchInput ? searchInput.value : '';
    console.log('Search Input:', title);
    if (title) {
        queryParams.push(`title=${encodeURIComponent(title)}`);
    }

    // Lấy giá trị từ dropdown địa điểm
    const locationElement = document.getElementById('locationDropdown');
    console.log('Location Element:', locationElement); // Kiểm tra phần tử dropdown
    const selectedLocation = locationElement.textContent.trim() !== 'Tất cả địa điểm'
        ? locationElement.textContent.trim()
        : null;
    console.log('Location:', selectedLocation);
    if (selectedLocation) {
        queryParams.push(`location=${encodeURIComponent(selectedLocation)}`);
    }

    // Lấy giá trị từ dropdown mức giá
    const priceElement = document.getElementById('priceDropdown');
    console.log('Price Element:', priceElement); // Kiểm tra phần tử dropdown
    const selectedPrice = priceElement ? priceElement.textContent.trim() : 'Tất cả mức giá';
    let fromPrice = null;
    let toPrice = null;

    if (selectedPrice === 'Từ 1 đến 3 triệu') {
        fromPrice = 1000000;
        toPrice = 3000000;
    } else if (selectedPrice === 'Từ 3 đến 6 triệu') {
        fromPrice = 3000000;
        toPrice = 6000000;
    } else if (selectedPrice === 'Trên 6 triệu') {
        fromPrice = 6000000;
    }

    if (fromPrice !== null) {
        queryParams.push(`fromPrice=${fromPrice}`);
    }
    if (toPrice !== null) {
        queryParams.push(`toPrice=${toPrice}`);
    }

    // Kiểm tra nếu cả địa điểm và mức giá đều là "Tất cả"
    if (selectedLocation === null && (fromPrice === null && toPrice === null)) {
        console.log('Default URL, không có tham số tìm kiếm nào.');
        // Nếu không có tham số tìm kiếm, chuyển hướng đến URL mặc định
        window.location.href = baseUrl; // Chuyển hướng đến URL mặc định
    } else {
        // Tạo URL với các tham số query
        const url = `${baseUrl}?${queryParams.join('&')}`;
        console.log('Generated URL:', url);
        // Chuyển hướng đến URL
        window.location.href = url;
    }
});
window.onload = function () {
    const params = new URLSearchParams(window.location.search);

    // Khôi phục giá trị ô tìm kiếm
    const searchInput = document.querySelector('.input-search');
    if (params.has('title')) {
        searchInput.value = decodeURIComponent(params.get('title'));
    }

    // Khôi phục dropdown địa điểm
    const locationDropdown = document.getElementById('locationDropdown');
    const selectedLocation = params.get('location');
    if (selectedLocation) {
        locationDropdown.textContent = selectedLocation; // Cập nhật text của dropdown
    }

    // Khôi phục dropdown mức giá
    const priceDropdown = document.getElementById('priceDropdown');
    const selectedPrice = params.get('fromPrice') !== null ? params.get('fromPrice') : 'Tất cả mức giá';
    if (selectedPrice) {
        // Xác định mức giá đã chọn và cập nhật dropdown
        if (selectedPrice == 1000000) {
            priceDropdown.textContent = 'Từ 1 đến 3 triệu';
        } else if (selectedPrice == 3000000) {
            priceDropdown.textContent = 'Từ 3 đến 6 triệu';
        } else if (selectedPrice == 6000000) {
            priceDropdown.textContent = 'Trên 6 triệu';
        } else {
            priceDropdown.textContent = 'Tất cả mức giá';
        }
    }
};
