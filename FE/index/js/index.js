document.addEventListener('DOMContentLoaded', () => {
  let fPressCount = 0;
  
  // F12 버튼 클릭 이벤트 리스너 추가
  const f12Btn = document.getElementById('f12-btn');
  if (f12Btn) {
    f12Btn.addEventListener('click', () => {
      fPressCount++;
      console.log(`f12 button clicked. Count: ${fPressCount}`);
      
      if (fPressCount === 12) {
        document.cookie = "secretToken=my-secret";
        window.location.href = 'http://localhost:3000/login';
        fPressCount = 0;
      }
    });
  }
  
  document.addEventListener('keydown', (event) => {
    if (event.key.toLowerCase() === 'f' && !event.ctrlKey && !event.metaKey && !event.altKey && !event.shiftKey) {
      fPressCount++;
      console.log(`f key pressed. Count: ${fPressCount}`);

    } else if (event.key.toLowerCase() !== 'f') {
      fPressCount = 0;
    }
    
    if (fPressCount === 12) {
      document.cookie = "secretToken=my-secret";
      window.location.href = 'http://localhost:3000/login';
      fPressCount = 0;
    }
  });
  
  // function handleSecretRouting() {
  //   async function fetchSecret(path) {
  //     const token = sessionStorage.getItem("secretToken");
  //     if (!token) {
  //       alert("먼저 인증이 필요합니다.");
  //       return;
  //     }
  //     try {
  //       const res = await fetch(path, {
  //         method: "GET",
  //         headers: {
  //           "X-Secret-Token": token,
  //         },
  //       });
  //       //const html = await res.text();
  //       //document.getElementById("result").innerHTML = html;
  //     } catch (err) {
  //       console.error("요청 실패:", err);
  //     }
  //   }
  //   const token = "my-secret";
  //   sessionStorage.setItem('secretToken', token);
  //   fetchSecret('/login');
  //   window.location.href = 'http://localhost:3000/login';
  // }
}); 

