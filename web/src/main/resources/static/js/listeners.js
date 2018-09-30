var popupAttachmentAddButton = document.querySelector(".attachmentAddButton");
var popupPhoneAddButton = document.querySelector(".popupPhone .addButton");
var saveContactButton = document.querySelector(".saveContact");
var updateContactButton = document.querySelector(".updateContact");
var deletePhoneFromTableButton = document.querySelector(".deletePhone");
var newContactButton = document.querySelector(".newContactBtn");
var updatePhoneButton = document.querySelector(".update.formButton");
var logo = document.querySelector(".logo");
var phoneAddButton = document.querySelector(".send.formButton");
var searchButton = document.querySelector(".searchForm .simpleButton");

popupPhoneAddButton.addEventListener("click", addPhoneToTable);
popupAttachmentAddButton.addEventListener("click", addAttachmentToTable);

saveContactButton.addEventListener("click", addContact);
updateContactButton.addEventListener("click", updateContact);
deletePhoneFromTableButton.addEventListener("click", deletePhoneFromTable);

newContactButton.addEventListener("click", function () {
    saveContactButton.setAttribute("style", "display: block");
    updateContactButton.setAttribute("style", "display: none");
    showContactForm();
});

updatePhoneButton.addEventListener("click", fillPhoneForm);
logo.addEventListener("click", function () {
    showContactList(1);
});

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

searchButton.addEventListener("click", searchByParams);

function addCheckEventListener() {
    var phoneTable = document.querySelector(".phones tbody");
    phoneTable.addEventListener("click", disableCheck)
}

contactsTable.addEventListener("click", function (event) {
    var target = event.target;
    if (target.className != "contactNameAnchor") {
        return;
    }
    showContact(target.previousElementSibling.firstChild.value);
});

function disableCheck(event) {
    if (event.target.tagName = "input") {
        var phoneTable = document.querySelector(".phones tbody");
        var elements = phoneTable.querySelectorAll("input");
        var checkedElements = phoneTable.querySelectorAll("input:checked");
        if (checkedElements.length < 1) {
            elements.forEach(function (elem) {
                elem.disabled = false;
            });
        } else {
            elements.forEach(function (elem) {
                if (!elem.checked) {
                    elem.disabled = true;
                }
            });
        }
    }
}



