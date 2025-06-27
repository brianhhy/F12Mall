document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.coin-item').forEach(target => {
    fetch('/mypage/component/coin.html')
      .then(res => res.text())
      .then(data => {
        target.innerHTML = data;
      });
  });
});
