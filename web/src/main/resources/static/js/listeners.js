var newContactButton = document.querySelector(".newContactButton");
var deleteContactButton = document.querySelector(".deleteContactButton");
var avatarInput = document.querySelector(".popupAvatar #avatarFile");
var deleteAttachmentFromTableButton = document.querySelector(".deleteAttachment");
var popupAttachmentAddButton = document.querySelector(".attachmentAddButton");
var popupPhoneAddButton = document.querySelector(".popupPhone .addButton");
var saveContactButton = document.querySelector(".saveContact");
var updateContactButton = document.querySelector(".updateContact");
var deletePhoneFromTableButton = document.querySelector(".deletePhone");
var updatePhoneButton = document.querySelector(".phoneUpdate");
var logo = document.querySelector(".logo");
var phoneAddButton = document.querySelector(".send.formButton");
var searchButton = document.querySelector(".searchForm .simpleButton");
var attachmentInput = document.querySelector(".popupAttachment #file");
var closeAvatarForm = document.querySelector(".popupAvatarClose");
var showEmailButton = document.querySelector(".emailBtn");
var selectTemplate = document.forms.emailForm.emailTemplate;
var sendEmailButton = document.querySelector(".sendEmailButton");
var closeContactFormButton = document.querySelector(".closeContactForm");

logo.addEventListener("click", function () {
   showContactList(1);
});

closeContactFormButton.addEventListener("click", function(){
    showContactList(1);
});

deleteContactButton.addEventListener("click", deleteContacts);
newContactButton.addEventListener("click", newContactOpen);
sendEmailButton.addEventListener("click", sendEmail);
selectTemplate.addEventListener("change", fillEmailTextarea);
attachmentInput.addEventListener("change", displayFileName);
deleteAttachmentFromTableButton.addEventListener("click", deleteAttachmentFromTable);
avatarInput.addEventListener("change", displayAvatarFileName);
popupPhoneAddButton.addEventListener("click", addPhoneToTable);
popupAttachmentAddButton.addEventListener("click", addAttachmentToTable);
saveContactButton.addEventListener("click", addContact);
updateContactButton.addEventListener("click", updateContact);
deletePhoneFromTableButton.addEventListener("click", deletePhoneFromTable);
showEmailButton.addEventListener("click", showEmailForm);

closeAvatarForm.addEventListener("click", function () {
    var avatarForm = document.forms.avatarForm;
    avatarForm.reset();
});

updatePhoneButton.addEventListener("click", fillPhoneForm);


phoneAddButton.addEventListener("click", function () {
    document.forms.phoneForm.reset();
    var phoneTable = document.querySelector(".phones tbody");
    var checkedElements = phoneTable.querySelectorAll("input:checked");
    if (checkedElements.length > 0) {
        phoneTable.querySelectorAll("input").forEach(function (elem) {
            elem.checked = false;
            elem.disabled = false;
        })
    }
});

searchButton.addEventListener("click", startSearch);

function addCheckEventListener() {
    var phoneTable = document.querySelector(".phones tbody");
    phoneTable.addEventListener("click", disableCheck)
}

contactsTable.addEventListener("click", function (event) {
    var target = event.target;
    if (target.className !== "contactNameAnchor") {
        return;
    }
    showContact(target.previousElementSibling.firstChild.value);
});

function disableCheck(event) {

    if (event.target.tagName = "input") {
        var phoneTable = document.querySelector(".phones tbody");
        var checkedElements = phoneTable.querySelectorAll("input:checked");

        if (checkedElements.length !== 1) {
            updatePhoneButton.setAttribute("class", "button update formButton phoneUpdate disabled");
        } else {
            updatePhoneButton.removeAttribute("class");
            updatePhoneButton.setAttribute("class", "button update formButton phoneUpdate");
        }
    }
}

function displayFileName() {
    var attachmentForm = document.forms.attachmentForm;
    var fileNameInput = document.querySelector(".popupAttachment #fileName");
    fileNameInput.value = attachmentForm.file.files[0].name;
}

function displayAvatarFileName() {
    var avatarForm = document.forms.avatarForm;
    var avatarFileNameInput = document.querySelector(".popupAvatar #avatarName");
    avatarFileNameInput.value = avatarForm.avatarFile.files[0].name;
}

function newContactOpen(){
    saveContactButton.setAttribute("style", "display: block");
    updateContactButton.setAttribute("style", "display: none");
    showContactForm();
}