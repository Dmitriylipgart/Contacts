window.onload = init;
window.Promise = ES6Promise;

var contactsTable = document.querySelector(".contactsTable");
var Page = {
    contactId: 0,
    first: 1,
    currentPage: 1,
    range: 5,
    totalPages: 0,
    recordsToShow: 10,
    search: false,

    doPaging: function (recordsCount) {
        this.totalPages = Math.ceil(recordsCount / this.recordsToShow);
        var paging = [];
        var last;
        if (this.currentPage < (this.range / 2) + 1) {
            this.first = 1;
        } else if (this.currentPage >= (this.totalPages - (this.range / 2) )) {
            this.first = Math.floor(this.totalPages - this.range + 1);

        } else {
            this.first = (this.currentPage - Math.floor(this.range / 2));
        }
        last = this.totalPages < this.range ? this.totalPages : this.first + this.range - 1;

        for (var i = this.first; i <= last; i++) {
            paging.push(i);
        }
        return paging;
    }
};


var attachmentFiles = [];
var attachmentFilesToDelete = [];

function init() {
    showContactTableHeader();
    showContactList(Page.currentPage);
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
    if (document.querySelector(".recordListAnchors")) {
        document.querySelector(".recordListAnchors").setAttribute("style", "display: none");
    }
    document.forms.contactForm.reset();
    var contactPhoto = document.querySelector(".contactPhoto");
    contactPhoto.removeAttribute("style");
    contactPhoto.firstElementChild.setAttribute("style", "display: inline-block");

    document.querySelector(".menuSearchButton").setAttribute("style", "display: none");
    document.querySelector(".emailBtn").setAttribute("style", "display: none");
    clearPhonesTable();
    clearAttachmentsTable();
    attachmentFiles = [];
    document.querySelector(".radio-group").setAttribute("style", "display: none");
    contactsTable.setAttribute("style", "display: none");
    document.querySelector(".contactFormWrapper").setAttribute("style", "display: block");
    newContactButton.setAttribute("style", "display: none");
    deleteContactButton.setAttribute("style", "display: none");
    updatePhoneButton.setAttribute("style", "display: none");
    deletePhoneFromTableButton.setAttribute("style", "display: none");
    updateAttachmentButton.setAttribute("style", "display: none");
    deleteAttachmentFromTableButton.setAttribute("style", "display: none");
    var phoneTable = document.querySelector(".phones tbody");
    var attachmentTable = document.querySelector(".attachments tbody");
    phoneTable.addEventListener("click", disablePhonesButtons);
    attachmentTable.addEventListener("click", disableAttachmentButtons);
}

function showNoRecordsMessage() {
    var message = document.querySelector("h3");
    message.setAttribute("style", "display: block");
}

function hideNoRecordsMessage() {
    var message = document.querySelector("h3");
    message.setAttribute("style", "display: none");
}

function clearContactTable() {
    while (contactsTable.childNodes.length > 1) {
        contactsTable.removeChild(contactsTable.lastChild);
    }
}
function clearPhonesTable() {
    var phonesTable = document.querySelector(".phones tbody");
    while (phonesTable.childNodes.length > 1) {
        phonesTable.removeChild(phonesTable.lastChild);
    }
}

function clearAttachmentsTable() {
    var attachmentsTable = document.querySelector(".attachments tbody");
    while (attachmentsTable.childNodes.length > 1) {
        attachmentsTable.removeChild(attachmentsTable.lastChild);
    }
}

function clearAncorsDiv() {
    var anchorsDiv = document.querySelector(".recordListAnchors");
    while (anchorsDiv.childNodes.length > 0) {
        anchorsDiv.removeChild(anchorsDiv.firstChild);
    }
}

function startSearch() {
    showContactListByParams(1);
}

function showContactListByParams(page) {
    Page.search = true;
    Page.currentPage = page;

    var options = getSearchParams();
    fetch('contacts/search/count', options).then(json).then(function (data) {
        var recordsCount = data;
        if (recordsCount < 1) {
            showNoRecordsMessage();
        } else {
            hideNoRecordsMessage();
            showPagination(recordsCount, "showContactListByParams");
            fetch('contacts/search?page=' + Page.currentPage + '&' + 'size=' + Page.recordsToShow, options).then(json).then(showContactTable);
        }
    });
}

function showContactList(page) {
    Page.currentPage = page;
    Page.search = false;
    var recordsCount = 0;
    if (document.querySelector("h3")) {
        document.querySelector("h3").setAttribute("style", "display: none");
    }
    document.querySelector(".menuSearchButton").setAttribute("style", "display: block");
    document.querySelector(".emailBtn").setAttribute("style", "display: block");
    document.querySelector(".radio-group").setAttribute("style", "display: block");
    document.querySelector(".contactFormWrapper").setAttribute("style", "display: none");
    document.querySelector(".newContactButton").setAttribute("style", "display: block");
    document.querySelector(".deleteContactButton").setAttribute("style", "display: block");

    fetch('contacts/count').then(json).then(function (data) {
        recordsCount = data;
         if (recordsCount < 1) {
            showNoRecordsMessage();
        } else {
            hideNoRecordsMessage();
            showPagination(recordsCount, "showContactList");
            fetch('contacts?page=' + Page.currentPage + '&' + 'size=' + Page.recordsToShow).then(json).then(showContactTable);
        }
    });
}

function showContactTable(data) {
    clearContactTable();

    if (data.length < 1 && Page.currentPage > 1) {
        Page.currentPage -= 1;
        showContactList(Page.currentPage);
    }
    else if(data.length < 1){
        showNoRecordsMessage();
    }else{
        contactsTable.setAttribute("style", "display: block");
        for (var i = 0; i < data.length; i++) {
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
            if(birthDate != "0000-00-00"){
            td3.innerHTML = moment(birthDate, 'YYYY-MM-DD').format('DD-MM-YYYY');
            }else{
            td3.innerHTML = "";
            }
            td4.innerHTML = job;
            td5.innerHTML = country + ", " + city + ", " + address;
            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            contactsTable.appendChild(tr);
        }
    }

}

function showPagination(recordsCount, showFunction) {
    var anchorsDiv = document.querySelector(".recordListAnchors");
    var pagination = Page.doPaging(recordsCount);
    if (!anchorsDiv) {
        anchorsDiv = document.createElement("div");
        document.querySelector(".wrapper").insertBefore(anchorsDiv, contactsTable);
        anchorsDiv.setAttribute("class", "recordListAnchors");
    } else {
        clearAncorsDiv();
        document.querySelector(".recordListAnchors").setAttribute("style", "display: block");
    }
    var first = document.createElement('a');
    first.setAttribute('onclick', showFunction + '(1)');
    first.setAttribute('class', 'anchor');
    var arrowLeft = document.createElement('i');
    arrowLeft.setAttribute('class', 'fas fa-arrow-circle-left');
    first.appendChild(arrowLeft);
    anchorsDiv.appendChild(first);
    for (var i = 0; i < pagination.length; i++) {
        var anchor = document.createElement('a');
        anchor.setAttribute('class', 'anchor');
        anchor.setAttribute('onclick', showFunction + '(' + pagination[i] + ')');
        anchor.innerHTML = pagination[i];
        if(pagination[i] == Page.currentPage){
            anchor.classList.add('currentPageAnchor');
        }
        anchorsDiv.appendChild(anchor);
    }
    var last = document.createElement('a');
    last.setAttribute('onclick', showFunction + '(' + Page.totalPages + ')');
    last.setAttribute('class', 'anchor');
    var arrowRight = document.createElement('i');
    arrowRight.setAttribute('class', 'fas fa-arrow-circle-right');
    last.appendChild(arrowRight);
    anchorsDiv.appendChild(last);
    anchorsDiv.setAttribute("style", "display: block");
}

function showContactTableHeader() {
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
    var phoneTable = document.querySelector(".phones tbody");

    // if (phoneTable.querySelectorAll("input:checked").length > 0) {
    //     var tr = phoneTable.querySelector("input:checked").parentNode.parentNode;
    //     tr.parentNode.removeChild(tr);
    // }
    var phoneForm = document.forms.phoneForm;
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
    if (phone instanceof MouseEvent) {
        td2.innerHTML = phoneForm.countryId.value + "(" + phoneForm.operatorId.value
            + ")" + phoneForm.phoneNumber.value;
        td3.innerHTML = phoneForm.phoneDescription.value;
        td4.innerHTML = phoneForm.phoneComment.value;
    } else {
        td2.innerHTML = phone.phoneNumber;
        td3.innerHTML = phone.phoneDescription;
        td4.innerHTML = phone.phoneComment;
    }
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    phoneTable.appendChild(tr);
    document.querySelector(".popupPhoneOpen").checked = false;
}

function fillPhoneForm() {
    var phoneForm = document.forms.phoneForm;
    var phoneTable = document.querySelector(".phones tbody");
    var tableData = phoneTable.querySelector("input:checked").parentNode.parentNode.children;
    var num = tableData[1].innerHTML;
    phoneForm.countryId.value = num.substring(0, num.indexOf("("));
    phoneForm.operatorId.value = num.substring(num.indexOf("(") + 1, num.indexOf(")"));
    phoneForm.phoneNumber.value = num.substring(num.indexOf(")") + 1);
    phoneForm.phoneDescription.value = tableData[2].innerHTML;
    phoneForm.phoneComment.value = tableData[3].innerHTML;
}



function deletePhoneFromTable() {
    var phoneTable = document.querySelector(".phones tbody");
    var elements = phoneTable.querySelectorAll("input:checked");
    elements.forEach(function (elem) {
        phoneTable.removeChild(elem.parentNode.parentNode);
    });
}

function getContactFromForm() {
    var contactForm = document.forms.contactForm;

    var phones = [];
    var attachments =[];
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
    var avatar = document.forms.avatarForm.avatarFile.files[0];
    if(avatar){
        contact.avatar = avatar.name;
    }
    if(!contact.avatar){
        contact.avatar = Page.avatar? Page.avatar: "";
    }
    var phoneTableRows = document.querySelector(".phones").rows;
    for (var i = 1; i < phoneTableRows.length; i++) {
        var phone = {};
        phone.phoneNumber = phoneTableRows[i].cells[1].innerHTML;
        phone.phoneDescription = phoneTableRows[i].cells[2].innerHTML;
        phone.phoneComment = phoneTableRows[i].cells[3].innerHTML;
        phones.push(phone);
    }
    var attachmentTableRows = document.querySelector(".attachments").rows;
    for (var i = 1; i < attachmentTableRows.length; i++) {
        var attachment = {};
        attachment.fileName = attachmentTableRows[i].cells[1].firstChild.innerHTML;
        var date = attachmentTableRows[i].cells[2].innerHTML;
        attachment.date = moment(date, 'DD-MM-YYYY').format('YYYY-MM-DD');
        attachment.comment = attachmentTableRows[i].cells[3].innerHTML;
        attachments.push(attachment);
    }

    contact.phones = phones;
    contact.attachments = attachments;
    return contact;
}
function addContact() {
    if(!validate()){
        return;
    }
    var contact = getContactFromForm();
    var formdata = new FormData();
    formdata.append("contact", JSON.stringify(contact));
    attachmentFiles.forEach(function (file) {
        formdata.append("files", file);
    });
    var avatar = document.forms.avatarForm.avatarFile.files[0];
    if(avatar){
        formdata.append("avatar", avatar);
    }

    var options = {
        method: 'post',
        body: formdata
    };

    fetch('contact', options).then(status).then(function () {
        resetValidationError();
        showContactList(1);
    });
}

function showContact(contactId) {

    Page.contactId = contactId;
    fetch('contact/' + contactId).then(json).then(function (data) {
        fillContactForm(data);
    });
    saveContactButton.setAttribute("style", "display: none");
    updateContactButton.setAttribute("style", "display: block");
    showContactForm();
}

function fillContactForm(contact) {
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
    var attachments = contact.attachments
    clearPhonesTable();
    clearAttachmentsTable();
    for (var i = 0; i < phones.length; i++) {
        addPhoneToTable(phones[i]);
    }
    for (var i = 0; i < attachments.length; i++) {
        addAttachmentToTable(attachments[i]);
    }
    var contactPhoto = document.querySelector(".contactPhoto");

    if(contact.avatar){
        console.log(contact.avatar);
        // Page.avatar = contact.avatar;
        contactPhoto.firstElementChild.setAttribute("style", "display:none");
        contactPhoto.setAttribute("style", "background: url('files/" + Page.contactId + "/avatar/" + contact.avatar
                                    + "') center center/contain;");
    }else {
        contactPhoto.firstElementChild.setAttribute("style", "display: inline-block");
    }
}

function updateContact() {
    var contact = getContactFromForm();
    contact.contactId = Page.contactId;
    var formdata = new FormData();
    formdata.append("contact", JSON.stringify(contact));

    attachmentFiles.forEach(function (file) {
        formdata.append("files", file);
    });
    if(attachmentFilesToDelete.length > 0){
        formdata.append("filesToDelete", JSON.stringify(attachmentFilesToDelete));
    }
    var avatar = document.forms.avatarForm.avatarFile.files[0];
    if(avatar){
        formdata.append("avatar", avatar);
    }
    var options = {
        method: 'post',
        body: formdata
    };

    fetch('contact/update', options).then(status).then(function () {
        showContactList(Page.currentPage);
    });
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
    fetch('contacts', options).then(status).then(function () {
        showContactList(Page.currentPage);
    });
}

var radio = document.getElementsByName("selector");
for (var i = 0; i < radio.length; i++) {
    radio[i].onchange = changeRecordsToShow;
}


function changeRecordsToShow() {
    Page.recordsToShow = this.value;
    Page.currentPage = 1;
    Page.search ? showContactListByParams(1): showContactList(1);
}

function getSearchParams(){
    var params = {};
    var form = document.forms.searchForm;
    params.first_name = form.first_name.value;
    params.last_name = form.last_name.value;
    params.middle_name = form.middle_name.value;
    params.birth_date_start = form.birth_date_start.value;
    params.birth_date_end = form.birth_date_end.value;
    params.sex = form.sex.value;
    params.citizenship = form.citizenship.value;
    params.family_status = form.family_status.value;
    params.country = form.country.value;
    params.city = form.city.value;
    params.address = form.address.value;
    params.zip_code = form.zip_code.value;

    var options = {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(params)
    };

    return options;
}

function addAttachmentToTable(attachment) {
    var attachmentTable = document.querySelector(".attachments tbody");
    var attachmentForm = document.forms.attachmentForm;
    var fileNameInput = document.querySelector(".popupAttachment #fileName");
    var tr = document.createElement("tr");
    var input = document.createElement("input");
    var td1 = document.createElement("td");
    var a = document.createElement("a");
    td1.setAttribute("class", "check");
    input.setAttribute("type", "checkbox");
    input.setAttribute("value", attachment.attachmentId);
    td1.appendChild(input);
    tr.appendChild(td1);
    var td2 = document.createElement("td");
    td2.setAttribute("name", "attachmentName");
    var td3 = document.createElement("td");
    td3.setAttribute("name", "attachmentDate");
    var td4 = document.createElement("td");
    td4.setAttribute("name", "attachmentComment");
    var today = moment();
    if (attachment instanceof MouseEvent) {
        a.innerHTML = fileNameInput.value;
        td2.appendChild(a);
        td3.innerHTML = today.format('DD-MM-YYYY');
        td4.innerHTML = attachmentForm.attachmentComment.value;
        attachmentFiles.push(attachmentForm.file.files[0]);
        document.querySelector(".popupAttachmentOpen").checked = false;
        attachmentForm.reset();
    } else {
        a.setAttribute("href", "files/" + Page.contactId + "/" + attachment.fileName);
        a.setAttribute("download", "");
        a.innerHTML = attachment.fileName;
        td2.appendChild(a);
        td3.innerHTML = moment(attachment.date,'YYYY-MM-DD').format('DD-MM-YYYY');
        td4.innerHTML = attachment.comment;
    }
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    attachmentTable.appendChild(tr);

}

function deleteAttachmentFromTable() {
    var attachmentTable = document.querySelector(".attachments tbody");
    var elements = attachmentTable.querySelectorAll("input:checked");
    elements.forEach(function (element) {
        var fileName = element.parentNode.parentNode.children[1].firstChild.innerHTML;
        if(element.value === "undefined"){
            attachmentFiles.forEach(function (file) {
                if(file.name === fileName){
                    attachmentFiles.splice(attachmentFiles.indexOf(file), 1);
                }
            });
        }
        else {
            var attachment = {};
            attachment.attachmentId = element.value;
            attachment.fileName = fileName;
            attachmentFilesToDelete.push(attachment);
        }
        attachmentTable.removeChild(element.parentNode.parentNode);
    });

}

function showEmailForm() {
    var elements = contactsTable.querySelectorAll("input:checked");
    var contactIdList = [];
    elements.forEach(function (elem) {
        contactIdList.push(elem.value);
    });
    var options = {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(contactIdList)
    };
    fetch('email/address', options).then(status).then(json).then(function (data) {
        showEmailAddresses(data);
    });
}


function showEmailAddresses(contacts) {
    var contactAddressField = document.forms.emailForm.emailAddress;
    var contactAddressList =[];
    contacts.forEach(function (contact) {
        contactAddressList.push(contact.email);
    });
    contactAddressField.value = contactAddressList.join(", ");
}

function fillEmailTextarea(event) {
    var templateName = event.target.value;
    fetch('email?template=' + templateName).then(status).then(function (response) {
        return response.text().then(function (text) {
            document.forms.emailForm.emailText.value = text;
        });
    });
}

function sendEmail(){
    var elements = contactsTable.querySelectorAll("input:checked");
    var contactIdList = [];
    elements.forEach(function (elem) {
        contactIdList.push(elem.value);
    });
    var emailForm = document.forms.emailForm;
    var email ={};
    email.contactIdList = contactIdList;
    email.header = emailForm.emailHeader.value;
    email.text = emailForm.emailText.value;

    var options = {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(email)
    };

    console.log(email);
    fetch('email', options).then(status);
}