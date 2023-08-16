const singInBtn = document.querySelector('.signin-btn')
const singUpBtn = document.querySelector('.signup-btn')
const formBox = document.querySelector('.form-box')
const body = document.body

singUpBtn.addEventListener('click', function () {
    formBox.classList.add('active');
    body.classList.add('active');
})

singInBtn.addEventListener('click', function () {
    formBox.classList.remove('active');
    body.classList.remove('active');
})

function toggleIconForPassLogin() {
    const input = document.getElementById('password_for_login');
    const icon = document.getElementById('icon_pass_login');

    if (input.value) {
        icon.style.display = 'none';
    } else {
        icon.style.display = 'block';
    }
}

function toggleIconForPassReg() {
    const input = document.getElementById('password_for_reg');
    const icon = document.getElementById('icon_pass_reg');

    if (input.value) {
        icon.style.display = 'none';
    } else {
        icon.style.display = 'block';
    }
}
function toggleIconForUsernameLogin() {
    const input = document.getElementById('username_for_login');
    const icon = document.getElementById('icon_user_login');

    if (input.value) {
        icon.style.display = 'none';
    } else {
        icon.style.display = 'block';
    }
}

function toggleIconForUsernameReg() {
    const input = document.getElementById('username_for_reg');
    const icon = document.getElementById('icon_user_reg');

    if (input.value) {
        icon.style.display = 'none';
    } else {
        icon.style.display = 'block';
    }
}

function toggleIconForEmail() {
    const input = document.getElementById('email');
    const icon = document.getElementById('icon_email');

    if (input.value) {
        icon.style.display = 'none';
    } else {
        icon.style.display = 'block';
    }
}