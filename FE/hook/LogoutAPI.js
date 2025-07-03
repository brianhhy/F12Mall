import { CONFIG } from '/config.js';

const API_LOGOUT_URL = `${CONFIG.API_BASE_URL}/auth/logout`;

async function handleLogout() {
    try {
        // 백엔드 로그아웃 API 호출
        const response = await fetch(API_LOGOUT_URL, {
            method: "POST",
            credentials: "include"
        });
        
        if (response.ok) {
            console.log("로그아웃 성공");
            // 로그인 페이지로 리다이렉트
            window.location.href = "/login";
        } else {
            console.error("로그아웃 실패");
            // 실패해도 로그인 페이지로 이동
            console.log("로그아웃 실패", response);
            window.location.href = "/login";
        }
    } catch (error) {
        console.error("로그아웃 요청 중 오류:", error);
        console.log("로그아웃 요청 중 오류", error);
        // 네트워크 오류 등이 발생해도 로그인 페이지로 이동
        window.location.href = "/login";
    }
}

// 전역 스코프에 함수 노출
window.handleLogout = handleLogout;

// DOM 로드 시 이벤트 리스너 설정
document.addEventListener('DOMContentLoaded', function() {
    // 헤더가 동적으로 로드되므로 약간의 지연 후 이벤트 리스너 등록
    setTimeout(() => {
        const logoutElement = document.querySelector(".logout");
        if (logoutElement) {
            logoutElement.addEventListener("click", handleLogout);
            console.log("로그아웃 이벤트 리스너 등록 완료");
        } else {
            console.log("로그아웃 요소를 찾을 수 없습니다");
        }
    }, 100);
});
