window.onload = init;

var form = document.forms.contactForm;
var contactsTable = document.querySelector(".contactsTable");
var contactForm = document.querySelector(".contactForm");
var newContactBtn = document.querySelector(".newContactBtn");
var headerMessage = document.querySelector("h1");
var form = document.forms.contactForm;
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
    document.querySelector(".recordListAnchors").setAttribute("style", "display: none");
    contactsTable.setAttribute("style", "display: none");
    contactForm.setAttribute("style", "display: block");
    newContactBtn.setAttribute("style", "display: none");
    headerMessage.innerHTML = "Заполните данные контакта";
}


function showContactList(page){
    var recordsCount = 31;
    var numOfPages;
    contactForm.setAttribute("style", "display: none");
    newContactBtn.setAttribute("style", "display: block");
    headerMessage.innerHTML = "Журнал контактов";
    fetch('/contacts/count').then(json).then(function (data) {
        recordsCount = data;
        console.log(data);
        if(recordsCount < 1) {
            showNoRecordsMessage();
        } else {
            showContactTable(recordsCount);

        }
    });
}

function showNoRecordsMessage() {
    var message = document.createElement("h3");
    message.innerHTML = "Нет Контактов";
    document.querySelector(".wrapper").insertBefore(message, contactsTable);
}

function showContactTable(recordsCount) {
    contactsTable.setAttribute("style", "display: block");
    showPagination(recordsCount);
    fetch('/contacts?page=' + page + '&' + 'size=' + recordsToShow).then(json).then(function (data) {
        for(var i = 0; i < data.length; i++){
            var tr = document.createElement("tr");
            var lastName = data[i].lastName;
            var firstName = data[i].firstName;
            var middleName = data[i].middleName;
            var birthDate = data[i].birthDate;
            var job = data[i].job;
            var country = data[i].country;
            var city = data[i].city;
            var address = data[i].address;
            var input = document.createElement("input");
            var td1 = document.createElement("td");
            input.setAttribute("type", "checkbox");
            td1.appendChild(input);
            var td2 = document.createElement("td");
            var td3 = document.createElement("td");
            var td4 = document.createElement("td");
            var td5 = document.createElement("td");
            td2.innerHTML = lastName + " " + firstName + " " + middleName;
            td3.innerHTML = birthDate;
            td4.innerHTML = job;
            td5.innerHTML = country + ", " + city + ", " + address;
            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            contactsTable.appendChild(tr);
        }
    });
}

function showPagination(recordsCount){
    var  anchorsDiv = document.querySelector(".recordListAnchors");
    if(!anchorsDiv){
        var numOfPages = Math.ceil(recordsCount/recordsToShow);
        anchorsDiv = document.createElement("div");
        document.querySelector(".wrapper").insertBefore(anchorsDiv, contactsTable);
        anchorsDiv.setAttribute("class", "recordListAnchors");
        for(var i = 1; i <= numOfPages; i++){
            var anchor = document.createElement('a');
            anchor.setAttribute('onclick', 'showContactList(' + i +')');
            anchor.setAttribute('class', 'anchor');
            anchor.innerHTML = i;
            anchorsDiv.appendChild(anchor);
        }
    }
}


function addContact(){
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




