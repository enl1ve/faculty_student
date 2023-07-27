const singInBtn = document.querySelector('.signin-btn')
const singUpBtn = document.querySelector('.signup-btn')
const formBox = document.querySelector('.form-box')
const body = document.body

singUpBtn.addEventListener('click', function() {
    formBox.classList.add('active');
    body.classList.add('active');
})

singInBtn.addEventListener('click', function() {
    formBox.classList.remove('active');
    body.classList.remove('active');
})
