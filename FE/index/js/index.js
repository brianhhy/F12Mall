document.addEventListener('DOMContentLoaded', () => {
  let fPressCount = 0;
  
  // 'F12 버튼' 클릭할 경우 카운트
  const f12Btn = document.getElementById('f12-btn');
  if (f12Btn) {
    f12Btn.addEventListener('click', () => {
      fPressCount++;
      console.log(`f12 button clicked. Count: ${fPressCount}`);
      
      if (fPressCount === 12) {
        document.cookie = "secretToken=my-secret";
        window.location.href = '/login';
        fPressCount = 0;
      }
    });
  }
  
  // 'f' 키 눌렀을 경우 카운트
  document.addEventListener('keydown', (event) => {
    if (event.key.toLowerCase() === 'f' && !event.ctrlKey && !event.metaKey && !event.altKey && !event.shiftKey) {
      fPressCount++;
      console.log(`f key pressed. Count: ${fPressCount}`);

    } else if (event.key.toLowerCase() !== 'f') {
      fPressCount = 0;
    }
    
    if (fPressCount === 12) {
      document.cookie = "secretToken=my-secret";
      window.location.href = '/login';
      fPressCount = 0;
    }
  });
}); 

