function showError(container, errorMessage) {

    container.lastElementChild.innerHTML = errorMessage;
}

function resetValidationError() {
    var elements = document.querySelectorAll(".errorMessage");
    elements.forEach(function (element) {
        element.innerHTML = "";
        element.parentNode.children[1].classList.remove("validationError");
    });
}

function validate() {
    var result = true;
    var elems = document.forms.contactForm.elements;
    var regURL = /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/;
    var regEmail = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;

    if (!elems.lastName.value) {

        showError(elems.lastName.parentNode, "Укажите Фамилию");
        result = false;
    }
    if (!elems.firstName.value) {
        showError(elems.firstName.parentNode, 'Укажите Имя');
        result = false;
    }

    if(elems.webSite.value !== ""){
        if (!regURL.test(elems.webSite.value)) {
            showError(elems.webSite.parentNode, 'Неправильно указан сайт');
            result = false;
        }
    }

    if(elems.email.value !== ""){
        if (!regEmail.test(elems.email.value)) {
            showError(elems.email.parentNode, 'Неправильно указан Email');
            result = false;
        }
    }
    return result;
}
