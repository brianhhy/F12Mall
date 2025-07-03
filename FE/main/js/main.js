document.addEventListener("DOMContentLoaded", function () {
    // 인증 체크
    checkAuthStatus();

    // Carousel Slide
    const carousel = document.querySelector(".carousel-track");
    const slides = Array.from(carousel.children);
    const dots = document.querySelectorAll(".carousel-indicator .dot");

    let index = 0;
    const total = slides.length;

    function goToSlide(i) {
        carousel.style.transition = "transform 0.5s ease-in-out";
        carousel.style.transform = `translateX(-${100 * i}%)`;
        dots.forEach((dot, idx) => {
            dot.classList.toggle("active", idx === i);
        });
    }

    function resetToStart() {
        carousel.style.transition = "none";
        carousel.style.transform = `translateX(0%)`;
        index = 0;
        dots.forEach((dot, idx) => {
            dot.classList.toggle("active", idx === 0);
        });
    }

    dots.forEach((dot, i) => {
        dot.addEventListener("click", () => {
            index = i;
            goToSlide(index);
        });
    });

    setInterval(() => {
        index++;

        if (index < total) {
            goToSlide(index);
        } else {
            goToSlide(index);
            setTimeout(resetToStart, 510);
        }
    }, 8000);

    // Sort Dropdown
    const sortToggle = document.querySelector(".sort-toggle");
    const sortMenu = document.querySelector(".sort-menu");
    const sortOptions = document.querySelectorAll(".sort-option");
    const sortLabel = document.querySelector(".sort-label");

    if (sortToggle && sortMenu && sortLabel) {
        sortToggle.addEventListener("click", function (e) {
            e.stopPropagation();
            sortMenu.classList.toggle("active");
        });

        sortOptions.forEach(option => {
            option.addEventListener("click", function () {
                sortOptions.forEach(opt => opt.classList.remove("selected"));
                this.classList.add("selected");
                sortLabel.textContent = this.textContent;
                sortMenu.classList.remove("active");
            });
        });

        document.addEventListener("click", function () {
            sortMenu.classList.remove("active");
        });
    }

    // View Radio
    const viewCard = document.getElementById("view-card");
    const viewList = document.getElementById("view-list");
    const cardView = document.querySelector(".box-grid-card");
    const listView = document.querySelector(".box-coin-list");

    if (viewCard && viewList && cardView && listView) {
        function toggleView() {
            const boxSort = document.querySelector(".box-sort");

            if (viewCard.checked) {
                cardView.style.display = "grid";
                listView.style.display = "none";
                if (boxSort) boxSort.style.display = "block";
            } else if (viewList.checked) {
                cardView.style.display = "none";
                listView.style.display = "block";
                if (boxSort) boxSort.style.display = "none";
            }
        }

        toggleView();
        viewCard.addEventListener("change", toggleView);
        viewList.addEventListener("change", toggleView);
    }
});

// 인증 상태 확인 함수
async function checkAuthStatus() {
    console.log('인증 체크 시작...');
    try {
        const response = await fetch('/api/auth/check', {
            method: 'GET',
            credentials: 'include' // 쿠키 포함
        });
        
        console.log('인증 체크 응답:', response.status);
        
        if (response.status === 401) {
            console.log('인증되지 않음 - 로그인 페이지로 리다이렉트');
            // 인증되지 않은 경우 로그인 페이지로 리다이렉트
            window.location.href = '/login';
        } else if (response.status === 200) {
            console.log('인증됨 - 페이지 접근 허용');
        }
    } catch (error) {
        console.error('인증 체크 중 오류 발생:', error);
        // 네트워크 오류 등이 발생한 경우에도 로그인 페이지로 리다이렉트
        window.location.href = '/login';
    }
}