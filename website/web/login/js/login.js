function login() {
    var user = document.getElementById('email').value;
    console.log(user);
    var pass = document.getElementById('pass').value;
    console.log(pass);
    var req = new XMLHttpRequest();

    req.open("POST", "http://localhost:8080/archery/auth/signIn", false);
    req.setRequestHeader('Accept', 'application/json');
    req.setRequestHeader('Content-Type', 'application/json');
    req.withCredentials = true;

    req.onload = function () {
        if (req.status !== 200) {
            alert("Wrong email or password, please try again");
        } else {
            console.log("Status is 200");
            setToken(req);
            console.log(JSON.parse(localStorage.getItem('tokenData')));
            console.log(JSON.parse(localStorage.getItem('expiresIn')));
            document.location.href = "../index2.html";
        }
    }
    req.send(JSON.stringify({login: user, password: pass}));
}

function setToken(tokenData) {
    console.log(tokenData.responseText);
    localStorage.setItem('tokenData', JSON.stringify(tokenData.responseText));
    localStorage.setItem('expiresIn', JSON.stringify(Date.now() + 180000)); //захардкодила, подумать как изменить (мб добавить новый метод)
}

