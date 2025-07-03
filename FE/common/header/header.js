import { CONFIG } from '/config.js';

document.addEventListener("DOMContentLoaded", () => {
  
  const includeTarget = document.querySelector("#include-header");
  if (includeTarget) {
    fetch("/common/header/header.html")
      .then((res) => res.text())
      .then((data) => {
        includeTarget.innerHTML = data;

        const profileImg = document.getElementById("profile-img");
        const boxMenu = document.getElementById("box-menu");

        console.log(profileImg, boxMenu);

        if (profileImg && boxMenu) {
          
          profileImg.addEventListener("click", (e) => {
            e.preventDefault();
            boxMenu.classList.toggle("hidden");
          });

          document.addEventListener("click", (event) => {
            if (!profileImg.contains(event.target) && !boxMenu.contains(event.target)) {
              boxMenu.classList.add("hidden");
            }
          });
          
          const mypageElement = document.querySelector(".mymenu");
          if (mypageElement) {
            mypageElement.addEventListener("click", () => {
              window.location.href = "http://localhost:8080/mypage";
            });
          }
        }

        
      });
  }
});
