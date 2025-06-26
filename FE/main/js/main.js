document.addEventListener("DOMContentLoaded", function () {
    // sort dropdown
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

    // view 전환
    const viewCard = document.getElementById("view-card");
    const viewList = document.getElementById("view-list");
    const cardView = document.querySelector(".box-grid-card");
    const listView = document.querySelector(".box-list-card");

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
