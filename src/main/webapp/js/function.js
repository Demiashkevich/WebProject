//Function Create Item
function createItem(clone, put){
    var toClone = document.getElementById(clone);
    var clonedNode = toClone.cloneNode(true);
    var insertPoint = document.getElementById(put);
    insertPoint.appendChild(clonedNode);
}
function removeItem(crew, marker){
    var count = document.getElementsByClassName(marker).length;
    if(count > 1){
        var parent = crew.parentElement;
        var parentNext = parent.parentElement;
        parentNext.remove();
    }
}
// Function Registration
function showInputError(container, errorMessage) {
    container.className = 'error-input-item';
    var elementDiv = document.createElement('div');
    elementDiv.className = "si-form-help error-input-fade-in";
    var parentElement = container.appendChild(elementDiv);
    var elementScan = document.createElement('scan');
    elementScan.className = "si-form-help-context";
    elementScan.innerHTML = errorMessage;
    parentElement.appendChild(elementScan);
}
function resetError(container) {
    container.className = 'si-item';
    if (container.lastChild.className == "si-form-help error-input-fade-in") {
        container.removeChild(container.lastChild);
    }
}
function validate() {
    var elements = document.registration_form.elements;
    var email = elements.email,
        password = elements.password,
        password_confirm = elements.password_confirm,
        firstName = elements.first_name,
        lastName = elements.last_name;

    if(emailValidate(email) & passwordValidate(password, password_confirm) &
        firstNameValidate(firstName) & lastNameValidate(lastName)){
        return true;
    }
    return false;
}
function emailValidate(email){
    resetError(email.parentNode);
    if (!email.value) {
        showInputError(email.parentNode, 'Enter email.');
        return false;
    }
    if(!(/.+@.+\..+/).test(email.value)){
        showInputError(email.parentNode, 'Enter a valid email address.');
        return false;
    }
    return true;
}
function passwordValidate(password, passwordConfirm){
    resetError(password.parentNode);
    resetError(passwordConfirm.parentNode);
    if (!password.value) {
        showInputError(password.parentNode, 'Enter password.');
        return false;
    }
    if (!(/^(?=.*\d+).*$/.test(password.value))){
        showInputError(password.parentNode, 'The password must contain numbers.');
        return false;
    }
    if (!(/^(?=.*[a-z]+).*$/.test(password.value))) {
        showInputError(password.parentNode, 'The password must contain letters.');
        return false;
    }
    if (!(/^(?=.*[A-Z]+).*$/.test(password.value))) {
        showInputError(password.parentNode, 'The password must contain an uppercase letter.');
        return false;
    }
    if (!(/^.{6,}$/.test(password.value))) {
        showInputError(password.parentNode, 'The password must contain more than 6 elements.');
        return false;
    }
    if (password.value != passwordConfirm.value) {
        showInputError(passwordConfirm.parentNode, 'The password don\'t match.');
        return false;
    }
    return true;
}
function firstNameValidate(firstName){
    resetError(firstName.parentNode);
    if (!firstName.value) {
        showInputError(firstName.parentNode, 'Enter first name.');
        return false;
    }
    if(!firstName.value.match("[A-zА-яЁё]+")){
        showInputError(firstName.parentNode, 'Enter a valid first name.');
        return false;
    }
    return true;
}
function lastNameValidate(lastName){
    resetError(lastName.parentNode);
    if (!lastName.value) {
        showInputError(lastName.parentNode, 'Enter last name.');
        return false;
    }
    if(!lastName.value.match("[A-zА-яЁё]+")){
        showInputError(lastName.parentNode, 'Enter a valid last name.');
        return false;
    }
    return true;
}

