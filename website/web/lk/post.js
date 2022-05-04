//нужен запрос, это пока скелет
function save_changes() {
    var firstname = document.getElementById('inputFirstname').value;
    console.log(firstname);
    var lastName = document.getElementById('inputLastName').value;
    console.log(lastName);
    var emailAddress = document.getElementById('inputEmailAddress').value;
    console.log(emailAddress);
    var phone = document.getElementById('inputPhone').value;
    console.log(phone);
    var birth_date = document.getElementById('inputBirthday').value;
    console.log(birth_date);
    var req = new XMLHttpRequest();

    req.open("POST", "http://localhost:8080/archery/profile/updateAll");
    req.setRequestHeader('Content-Type', 'application/json');
    req.withCredentials = true;
    req.onload = function () {
        if (req.status !== 200) {
            alert("Something went wrong, please try again");
        } else {
            console.log("Success");
        }
    }
    req.send(JSON.stringify({
        first_name: firstname,
        last_name: lastName,
        phone_number: phone,
        email: emailAddress,
        birth_date: birth_date
    }));
}
