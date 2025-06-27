document.addEventListener("DOMContentLoaded", function () {
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