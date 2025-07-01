document.addEventListener("DOMContentLoaded", () => {
  // 페이지 로드 시 세션 체크
  checkSession();
  
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
                  const response = await fetch("http://localhost:8081/auth/logout", {
                    method: "POST",
                    credentials: "include"
                  });
                  
                  if (response.ok) {
                    console.log("로그아웃 성공");
                    // 로그인 페이지로 리다이렉트
                    window.location.href = "http://localhost:3000/login";
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
              window.location.href = "http://localhost:3000/mypage";
            });
          }
        }

        
      });
  }
});

async function checkSession() {
  try {
    const response = await fetch("http://localhost:8081/auth/check", {
      method: "GET",
      credentials: "include"
    });
    
    if (response.ok) {
      const result = await response.text();
      console.log("세션 체크 성공:", result);
    } else {
      console.log("세션 없음 - 로그인 페이지로 리다이렉트");
      window.location.href = "http://localhost:3000/login/login.html";
    }
  } catch (error) {
    console.error("세션 체크 실패:", error);
    window.location.href = "http://localhost:3000/login/login.html";
  }
}
