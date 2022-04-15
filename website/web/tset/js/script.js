// function time() {
//     let d = new Date();
//     document.body.innerHTML = "<h1>Today’s date is " + d + "</h1>"
// }
function login() {

    var user = "kate_boriso2002@mail.ru";
    var pass = "pwd";

    var req = new XMLHttpRequest();
    console.log("first attempt");
    req.open("POST", "http://localhost:8080/archery/auth/signIn", true);
    console.log("2 attempt");
    req.setRequestHeader('Content-Type', 'application/json');
    req.withCredentials = true;
    req.send(JSON.stringify({login: user, password: pass}));
    console.log("3 attempt");

    if (req.status === 200) {
        location.href = "../../lk.html";
        //window.location = "../../lk.html";
    } else {
        alert("Some fail");
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
        // Do something for an error here
    });


    // if (req.status === 200) {
    //     location.href = "../../lk.html";
    //     //window.location = "../../lk.html";
    // } else {
    //     alert("Some fail");
    // }
}