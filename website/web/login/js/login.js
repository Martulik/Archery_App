// function login() {
//     var user = document.getElementById('email').value;
//     console.log(user);
//     var pass = document.getElementById('pass').value;
//     console.log(pass);
//     var req = new XMLHttpRequest();
//
//     req.open("POST", "http://localhost:8080/archery/auth/signIn", false);
//     req.setRequestHeader('Content-Type', 'application/json');
//     req.withCredentials = true;
//     req.onload = function () {
//         if (req.status !== 200) {
//             alert("Wrong email or password, please try again");
//         } else {
//             console.log("Success");
//             document.location.href = "../index2.html";
//         }
//     }
//     req.send(JSON.stringify({login: user, password: pass}));
// }

function saveToken(token) {
    sessionStorage.setItem('tokenData', JSON.stringify(token));
}

function login() {
    var access_t = JSON.parse(localStorage.getItem('token'))['access_token']; //убедиться что работает
    fetch('http://localhost:8080/archery/auth/signIn', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': `Token: ${access_t}` //убедиться что работает
        },
        body: JSON.stringify({
            login:  document.getElementById('email').value,
            password: document.getElementById('pass').value
        }),
    })
        .then((res) => {
            if (res.status === 200) {
                console.log("Success");
                const tokenData = res.json();
                saveToken(JSON.stringify(tokenData)); // сохраняем полученный токен в sessionStorage, с помощью функции, заданной ранее
                document.location.href = "../index2.html";
            } else {
                alert("Wrong email or password, please try again");
            }
        });
}


function refreshToken(token) {
    return fetch('http://localhost:8080/archery/auth/refreshToken', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            token,
        }),
    })
        .then((res) => {
            if (res.status === 200) {
                const tokenData = res.json();
                saveToken(JSON.stringify(tokenData)); // сохраняем полученный обновленный токен в sessionStorage, с помощью функции, заданной ранее
                return Promise.resolve();
            }
            return Promise.reject();
        });
}

export async function fetchWithAuth(url, options) { //не понимаю что именно возвращает (что такое options?)

    const loginUrl = '../login.html'; // url страницы для авторизации //правильный ли путь?
    var tokenData = null; // объявляем локальную переменную tokenData
    var access_t = null;
    var expires = null;
    if (sessionStorage.authToken) { // если в sessionStorage присутствует tokenData, то берем её
        console.log("token exist");
        tokenData = JSON.parse(localStorage.getItem('token'));
        access_t = JSON.parse(localStorage.getItem('token'))['access_token'];
        expires = JSON.parse(localStorage.getItem('token'))['expires_in'];

    } else {
        return window.location.replace(loginUrl); // если токен отсутствует, то перенаправляем пользователя на страницу авторизации
    }

    if (!options.headers) { // если в запросе отсутствует headers, то задаем их
        console.log("there aren't headers");
        options.headers = {};
    }

    if (tokenData) {
        console.log("tokenData exist");
        if (Date.now() >= expires) { // проверяем не истек ли срок жизни токена
            console.log("token isn't valid");
            try {
                const newToken = await refreshToken(access_t); // если истек, то обновляем токен с помощью refresh_token
                saveToken(newToken);
            } catch (err) { // если тут что-то пошло не так, то перенаправляем пользователя на страницу авторизации
                return  window.location.replace(loginUrl);
            }
        }
        options.headers.Authorization = `Bearer ${access_t}`; // добавляем токен в headers запроса
    }
    return fetch(url, options); // возвращаем изначальную функцию, но уже с валидным токеном в headers
}


