// CONFIG 객체가 정의되지 않은 경우를 대비한 기본값
if (typeof CONFIG === 'undefined') {
  const CONFIG = {
    API_BASE_URL: 'http://localhost:8080'
  };
}

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

          const logoutElement = document.querySelector(".logout");
          if (logoutElement) {
                          logoutElement.addEventListener("click", async () => {
                try {
                  // 백엔드 로그아웃 API 호출
                  const response = await fetch(`${CONFIG.API_BASE_URL}/auth/logout`, {
                    method: "POST",
                    credentials: "include"
                  });
                  
                  if (response.ok) {
                    console.log("로그아웃 성공");
                    // 로그인 페이지로 리다이렉트
                    window.location.href = "http://localhost:3001/login";
                  } else {
                    console.error("로그아웃 실패");
                    // 실패해도 로그인 페이지로 이동
                    console.log("로그아웃 실패", response);
                  }
                } catch (error) {
                  console.error("로그아웃 요청 중 오류:", error);
                  console.log("로그아웃 요청 중 오류", error);
                }
              });
          }

          const mypageElement = document.querySelector(".mymenu");
          if (mypageElement) {
            mypageElement.addEventListener("click", () => {
              window.location.href = "http://localhost:3001/mypage";
            });
          }
        }

        
      });
  }
});
