

var popupPhoneAddButton = document.querySelector(".popupPhone .addButton");
var deletePhoneFromTableButton = document.querySelector(".deletePhone");
contactsTable.addEventListener("click", function (event) {
    var target = event.target;
    if(target.className != "contactNameAnchor"){
        return;
    }
    console.log(target.previousElementSibling.firstChild.value);
    showContact(target.previousElementSibling.firstChild.value);
});

deletePhoneFromTableButton.addEventListener("click", deletePhoneFromTable);
popupPhoneAddButton.addEventListener("click", addPhoneToTable);

