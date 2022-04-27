function register() {
    var email = document.getElementById('email').value;
    console.log(email);
    var pass = document.getElementById('pass').value;
    console.log(pass);
    var phone_number = document.getElementById('phone_number').value;
    console.log(phone_number);
    var first_name = document.getElementById('first_name').value;
    console.log(first_name);
    var last_name = document.getElementById('last_name').value;
    console.log(last_name);
    var birth_date = document.getElementById('birth_date').value;
    console.log(birth_date);
    var req = new XMLHttpRequest();

    req.open("POST", "http://localhost:8080/archery/auth/register");
    req.setRequestHeader('Content-Type', 'application/json');
    req.withCredentials = true;
    req.onload = function () {
        if (req.status !== 200) {
            alert("Something went wrong, please try again");
        } else {
            console.log("Success");
            document.location.href = "../index2.html";
        }
    }
    req.send(JSON.stringify({ password_hash: pass, first_name: first_name,
        last_name: last_name, phone_number: phone_number, email: email,
        birth_date: birth_date}));
}
