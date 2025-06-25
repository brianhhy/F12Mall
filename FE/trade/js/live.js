// 세자리 수 단위로 쉼표를 찍는 함수
function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

// 페이지 로드 시 자동으로 포맷팅 적용
document.addEventListener('DOMContentLoaded', function() {
    const currentPriceElement = document.getElementById('current-price');
    const dailyChangeElement = document.getElementById('daily-change');
    
    if (currentPriceElement) {
        currentPriceElement.textContent = formatNumberWithCommas(parseInt(currentPriceElement.textContent));
        dailyChangeElement.textContent = formatNumberWithCommas(parseInt(dailyChangeElement.textContent));
    }
});