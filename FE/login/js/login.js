function toggleForms() {
    document.querySelector('.section-login').classList.toggle('hidden');
    document.querySelector('.section-signup').classList.toggle('hidden');
}

function showRightSignup() {
    document.querySelector('.container-right-signup').classList.remove('hidden');
    document.querySelector('.next-btn').style.display = 'none';
}
