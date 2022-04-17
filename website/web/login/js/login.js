function login() {

    var user = document.getElementById('email').value();
    alert(user);
    var pass = document.getElementById('pass').value();
    alert(pass);
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