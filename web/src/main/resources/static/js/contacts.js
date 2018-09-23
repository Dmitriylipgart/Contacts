window.onload = init;

var contactsTable = document.querySelector(".contactsTable");
// contactsTable.__proto__ = new Component();
var recordsToShow = 10;
var currentPage = 1;

function init(){
    showContactTableHeader();
    showContactList(currentPage);
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
    if(document.querySelector(".recordListAnchors")){
     document.querySelector(".recordListAnchors").setAttribute("style", "display: none");
    }
    contactsTable.setAttribute("style", "display: none");
    document.querySelector(".contactForm").setAttribute("style", "display: block");
    document.querySelector(".newContactBtn").setAttribute("style", "display: none");
    document.querySelector(".delContactBtn").setAttribute("style", "display: none");
    document.querySelector("h1").innerHTML = "Заполните данные контакта";
}


function showNoRecordsMessage() {
    var message = document.createElement("h3");
    message.innerHTML = "Нет Контактов";
    document.querySelector(".wrapper").insertBefore(message, contactsTable);
    contactsTable.setAttribute("style", "display: none");
}

function clearContactTable(){
    while (contactsTable.childNodes.length > 1){
        contactsTable.removeChild(contactsTable.lastChild);
    }
}

function clearAncorsDiv(){
    var anchorsDiv = document.querySelector(".recordListAnchors");
    while (anchorsDiv.childNodes.length > 0){
        anchorsDiv.removeChild(anchorsDiv.firstChild);
    }
}

function showContactList(page){
    var recordsCount = 0;
    if(document.querySelector("h3")){
        document.querySelector("h3").setAttribute("style", "display: none");
    }
    document.querySelector(".contactForm").setAttribute("style", "display: none");
    document.querySelector(".newContactBtn").setAttribute("style", "display: inline-block");
    document.querySelector(".delContactBtn").setAttribute("style", "display: inline-block");
    document.querySelector("h1").innerHTML = "Журнал контактов";

    fetch('/contacts/count').then(json).then(function (data) {
        recordsCount = data;
        if(recordsCount < 1) {
            showNoRecordsMessage();
        } else {
            showContactTable(recordsCount, page);
        }
    });
}

function showContactTable(recordsCount, page) {

    contactsTable.setAttribute("style", "display: block");
    showPagination(recordsCount);
    clearContactTable();
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
            td1.setAttribute("class", "check");
            input.setAttribute("type", "checkbox");
            input.setAttribute("id", data[i].contactId);
            input.setAttribute("value", data[i].contactId);
            td1.appendChild(input);
            var td2 = document.createElement("td");
            td2.setAttribute("class", "contactNameAnchor");
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
    var numOfPages = Math.ceil(recordsCount/recordsToShow);
    if(!anchorsDiv){
        anchorsDiv = document.createElement("div");
        document.querySelector(".wrapper").insertBefore(anchorsDiv, contactsTable);
        anchorsDiv.setAttribute("class", "recordListAnchors");
    }else{
        clearAncorsDiv();
        document.querySelector(".recordListAnchors").setAttribute("style", "display: block");
    }
    for(var i = 1; i <= numOfPages; i++){
        var anchor = document.createElement('a');
        anchor.setAttribute('onclick', 'showContactList(' + i +')');
        anchor.setAttribute('class', 'anchor');
        anchor.innerHTML = i;
        anchorsDiv.appendChild(anchor);
    }
    anchorsDiv.setAttribute("style", "display: block");
}

function showContactTableHeader(){
    var tr = document.createElement("tr");
    var th1 = document.createElement("th");
    var th2 = document.createElement("th");
    var th3 = document.createElement("th");
    var th4 = document.createElement("th");
    var th5 = document.createElement("th");
    th1.innerHTML = "";
    th2.innerHTML = "Имя";
    th3.innerHTML = "Дата Рождения";
    th4.innerHTML = "Место Работы";
    th5.innerHTML = "Адрес";
    th1.setAttribute("class", "check");
    tr.appendChild(th1);
    tr.appendChild(th2);
    tr.appendChild(th3);
    tr.appendChild(th4);
    tr.appendChild(th5);
    contactsTable.appendChild(tr);
}

function addPhoneToTable(phone) {
    var phoneForm = document.forms.phoneForm;
    var phoneTable = document.querySelector(".phones tbody");
    var tr = document.createElement("tr");
    var input = document.createElement("input");
    var td1 = document.createElement("td");
    td1.setAttribute("class", "check");
    input.setAttribute("type", "checkbox");
    td1.appendChild(input);
    tr.appendChild(td1);
    var td2 = document.createElement("td");
    td2.setAttribute("name", "phoneNumber");
    var td3 = document.createElement("td");
    td3.setAttribute("name", "phoneDescription");
    var td4 = document.createElement("td");
    td4.setAttribute("name", "phoneComment");
    if(phone){
        td2.innerHTML = phone.phoneNumber;
        td3.innerHTML = phone.phoneDescription;
        td4.innerHTML = phone.phoneComment;
    }else{
        td2.innerHTML = phoneForm.countryId.value + phoneForm.operatorId.value
            + phoneForm.phoneNumber.value;
        td3.innerHTML = phoneForm.phoneDescription.value;
        td4.innerHTML = phoneForm.phoneComment.value;
    }
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    phoneTable.appendChild(tr);
    document.querySelector(".popupPhoneOpen").checked = false;
}

function deletePhoneFromTable(){
    var phoneTable = document.querySelector(".phones tbody");
    var elements = phoneTable.querySelectorAll("input:checked");
    elements.forEach(function (elem) {
        phoneTable.removeChild(elem.parentNode.parentNode);
    });
}


function addContact(){
    var contactForm = document.forms.contactForm;
    var phones = [];
    var contact = {};
    contact.firstName = contactForm.firstName.value;
    contact.lastName = contactForm.lastName.value;
    contact.middleName = contactForm.middleName.value;
    contact.birthDate = contactForm.birthDate.value;
    contact.sex = contactForm.sex.value;
    contact.citizenship = contactForm.citizenship.value;
    contact.familyStatus = contactForm.familyStatus.value;
    contact.webSite = contactForm.webSite.value;
    contact.email = contactForm.email.value;
    contact.job = contactForm.job.value;
    contact.country = contactForm.country.value;
    contact.city = contactForm.city.value;
    contact.address = contactForm.address.value;
    contact.zipCode = contactForm.zipCode.value;
    var phoneTableRows = document.querySelector(".phones").rows;
    console.log(phoneTableRows);
    for(var i = 1; i < phoneTableRows.length; i++){
        var phone = {};
        console.log(phoneTableRows[i]);
        phone.phoneNumber = phoneTableRows[i].cells[1].innerHTML;
        phone.phoneDescription = phoneTableRows[i].cells[2].innerHTML;
        phone.phoneComment = phoneTableRows[i].cells[3].innerHTML;
        phones.push(phone);
    }
    contact.phones = phones;

    var options = {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(contact)
    };
    fetch('/contact', options).then(status).then(function () {
        showContactList(1);
    });
}

function showContact(contactId){
    fetch('/contact/' + contactId).then(json).then(function (data) {
        fillContactForm(data);
    });
    showContactForm();
}

function fillContactForm(contact){
    var contactForm = document.forms.contactForm;
    contactForm.firstName.value = contact.firstName;
    contactForm.lastName.value = contact.lastName;
    contactForm.middleName.value = contact.middleName;
    contactForm.birthDate.value = contact.birthDate;
    contactForm.sex.value = contact.sex;
    contactForm.citizenship.value = contact.citizenship;
    contactForm.familyStatus.value = contact.familyStatus;
    contactForm.webSite.value = contact.webSite;
    contactForm.email.value = contact.email;
    contactForm.job.value = contact.job;
    contactForm.country.value = contact.country;
    contactForm.city.value = contact.city;
    contactForm.address.value = contact.address;
    contactForm.zipCode.value = contact.zipCode;
    var phones = contact.phones;
    console.log(phones);
    for(var i = 0; i < phones.length; i++){
        console.log(phones[i]);
        addPhoneToTable(phones[i]);
    }

}

function deleteContacts() {
    var elements = contactsTable.querySelectorAll("input:checked");
    var contactIdList = [];
    elements.forEach(function (elem) {
        contactIdList.push(elem.value);
    });
    var options = {
        method: 'delete',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(contactIdList)
    };
    fetch('/contacts', options).then(status).then(function () {
        showContactList(1);
    });
}

var radio = document.getElementsByName("selector");
for(var i = 0; i < radio.length; i++){
    radio[i].onchange = changeRecordsToShow;
}


function changeRecordsToShow() {
    recordsToShow = this.value;
    showContactList(1);
}

