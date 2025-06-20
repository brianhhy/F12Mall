document.addEventListener("DOMContentLoaded", () => {
  const includeTarget = document.querySelector("#include-header");
  if (includeTarget) {
    fetch("../common/header/header.html") // 상대경로 가능
      .then((res) => res.text())
      .then((data) => {
        includeTarget.innerHTML = data;
      });
  }
});
