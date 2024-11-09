document.addEventListener('DOMContentLoaded', function () {
    // Lắng nghe sự kiện click cho các dropdown
    const locationItems = document.querySelectorAll('#locationDropdown + .dropdown-menu .dropdown-item');
    const priceItems = document.querySelectorAll('#priceDropdown + .dropdown-menu .dropdown-item');
    const nonSortItems = document.querySelectorAll('#nonSortDropdown + .dropdown-menu .dropdown-item');

    // Cập nhật nội dung của nút địa điểm
    locationItems.forEach(item => {
        item.addEventListener('click', function (event) {
            event.preventDefault(); // Ngăn chặn hành vi mặc định
            const button = document.getElementById('locationDropdown');
            button.innerHTML = `<i class="fas fa-location-dot me-2"></i>${this.textContent}`; // Cập nhật văn bản
        });
    });

    // Cập nhật nội dung của nút mức giá
    priceItems.forEach(item => {
        item.addEventListener('click', function (event) {
            event.preventDefault(); // Ngăn chặn hành vi mặc định
            const button = document.getElementById('priceDropdown');
            button.innerHTML = `<i class="fas fa-dollar-sign me-2"></i>${this.textContent}`; // Cập nhật văn bản
        });
    });

    // Cập nhật nội dung của nút sắp xếp
    nonSortItems.forEach(item => {
        item.addEventListener('click', function (event) {
            event.preventDefault(); // Ngăn chặn hành vi mặc định
            const button = document.getElementById('nonSortDropdown');
            button.innerHTML = `${this.textContent}`; // Cập nhật văn bản
        });
    });
});