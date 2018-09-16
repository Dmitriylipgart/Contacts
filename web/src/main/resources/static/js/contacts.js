window.onload = init;

var form = document.forms.contactForm;
var contactsTable = document.querySelector(".contactsTable");
var contactForm = document.querySelector(".contactForm");
var newContactBtn = document.querySelector(".newContactBtn");
var headerMessage = document.querySelector("h1");
var recordsToShow = 10;
var page = 1;

function init(){
    showContactList(page);
}


function status(response) {
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response);
    } else {
        return Promise.reject(new Error(response.statusText));
    }
}

function json(response) {
    return response.json();
}

function showContactForm() {

    contactsTable.setAttribute("style", "display: none");
    contactForm.setAttribute("style", "display: block");
    newContactBtn.setAttribute("style", "display: none");
    headerMessage.innerHTML = "Заполните данные контакта";

}


function showContactList(page){
    var recordsCount;
    var numOfPages;
    var anchorsDiv = document.querySelector(".recordListAnchors");
    contactsTable.setAttribute("style", "display: block");
    contactForm.setAttribute("style", "display: none");
    newContactBtn.setAttribute("style", "display: block");
    headerMessage.innerHTML = "Журнал контактов";
    fetch('/contacts').then(json).then(function (data) {
        recordsCount = JSON.parse(data);
    });
    numOfPages = Math.ceil(recordsCount/recordsToShow);
    for(var i = 1; i <= numOfPages; i++){
        var anchor = document.createElement('a');
        anchor.setAttribute('onclick', 'showContactList(' + i +')');
        anchor.setAttribute('class', 'anchor');
        anchor.innerHTML = i;
        anchorsDiv.appendChild(anchor)
    }
    fetch('/contacts?page=' + page + '&' + 'size=' + recordsToShow).then(json).then(function (data) {
        console.log(data)
    });

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



    var options = {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(contact)
    }




    fetch('/contact', options).then(status);
}




