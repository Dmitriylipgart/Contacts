
var form = document.forms.contactForm;

var contactsTable = document.querySelector(".contactsTable");
var contactForm = document.querySelector(".contactForm");
var newContactBtn = document.querySelector(".newContactBtn");
var headerMessage = document.querySelector("h1");

function showContactForm() {

    contactsTable.setAttribute("style", "display: none");
    contactForm.setAttribute("style", "display: block");
    newContactBtn.setAttribute("style", "display: none");
    headerMessage.innerHTML = "Заполните данные контакта";

}


function showContactList(){
    contactsTable.setAttribute("style", "display: block");
    contactForm.setAttribute("style", "display: none");
    newContactBtn.setAttribute("style", "display: block");
    headerMessage.innerHTML = "Журнал контактов";
}

var form = document.forms.contactForm;

function addContact(){
    'use strict';
    var contact = {};
    contact.firstName = form.firstName.value;
    contact.lastName = form.lastName.value;
    contact.middleName = form.middleName.value;
    contact.birthDate = form.birthDate.value;
    contact.sex = form.sex.value;
    contact.citizenship = form.citizenship.value;
    contact.familyStatus = form.familyStatus.value;
    contact.webSite = form.webSite.value;
    contact.email = form.email.value;
    contact.job = form.job.value;
    contact.country = form.country.value;
    contact.city = form.city.value;
    contact.address = form.address.value;
    contact.zipCode = form.zipCode.value;

    function status(response) {
        showContactList();
        if (response.status >= 200 && response.status < 300) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(new Error(response.statusText));
        }
    }
    //
    // function json(response) {
    //     return response.json();
    // }

    fetch('/contact', {method: 'post', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(contact)}).then(status);









}




