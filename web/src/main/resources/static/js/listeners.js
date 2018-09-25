

var popupPhoneAddButton = document.querySelector(".popupPhone .addButton");
var newContactButton = document.querySelector(".newContact");
var deletePhoneFromTableButton = document.querySelector(".deletePhone");
contactsTable.addEventListener("click", function (event) {
    var target = event.target;
    if(target.className != "contactNameAnchor"){
        return;
    }
    showContact(target.previousElementSibling.firstChild.value);
});
newContactButton.addEventListener("click", addContact)
deletePhoneFromTableButton.addEventListener("click", deletePhoneFromTable);
popupPhoneAddButton.addEventListener("click", addPhoneToTable);

