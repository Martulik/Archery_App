// function time() {
//     let d = new Date();
//     document.body.innerHTML = "<h1>Today’s date is " + d + "</h1>"
// }
function login() {

    var user = "kate_boriso2002@mail.ru";
    var pass = "pwd1";

    var req = new XMLHttpRequest();

    req.open("POST", "http://localhost:8080/archery/auth/signIn");
    req.setRequestHeader('Content-Type', 'application/json');
    req.withCredentials = true;
    req.send(JSON.stringify({login: user, password: pass}));
    req.onload = function () {
        if (req.status !== 200) {
            alert("Fail");
        } else {
            alert("Success");
            location.href = "../../lk.html";
        }
    }
}

function getStudents() {
    console.log("first attempt");
    var apiUrl = "http://localhost:8080/archery/test/studentList";
    fetch(apiUrl).then(response => {
        return response.json();
    }).then(data => {
        // Work with JSON data here
        alert("Success");
        console.log(data);
    }).catch(err => {
        alert("Fail");
        console.log(err);
    });
}