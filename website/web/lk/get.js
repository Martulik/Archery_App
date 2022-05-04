// function getData(url, field_id) {
//     fetch(url).then(response => {
//         return response.text();
//     }).then(data => {
//         document.getElementById(field_id).value = data;
//         // input = document.getElementById(id);
//         console.log(data);
//         // id.value = data;
//     }).catch(err => {
//         alert("Fail");
//         console.log(err);
//     });
// }
//
// getData("http://localhost:8080/archery/profile/getFirstName",'inputFirstname');
// getData("http://localhost:8080/archery/profile/getLastName",'inputLastName');
// getData("http://localhost:8080/archery/test/studentList",'inputEmailAddress');
// getData("http://localhost:8080/archery/profile/getPhoneNumber",'inputPhone');
// getData("http://localhost:8080/archery/test/studentList",'inputBirthday');
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
//             const tokenData = req.json();
//             saveToken(JSON.stringify(tokenData));
//             document.location.href = "../index2.html";
//         }
//     }
//     req.send(JSON.stringify({login: user, password: pass}));
//
// }

function saveToken(token) {
    localStorage.setItem('tokenData', JSON.stringify(token));
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

// export
async function fetchWithAuth(url, options) { //не понимаю что именно возвращает (что такое options?)

    const loginUrl = '../login/login.html'; // url страницы для авторизации //правильный ли путь?
    var tokenData_t = null; // объявляем локальную переменную tokenData
    var access_t = null;
    var expires = null;
    if (localStorage.tokenData) { // если в sessionStorage присутствует tokenData, то берем её
        console.log("token exist");
        tokenData_t = localStorage.tokenData;
        console.log(localStorage.tokenData);
        access_t = JSON.parse(localStorage.getItem('tokenData'))['access_token'];
        console.log(access_t);
        expires = JSON.parse(localStorage.getItem('tokenData'))['expires_in'];
        console.log(expires);

    } else {
        console.log("localStorage.authToken = null")
        return fetch(url, options); // возвращаем изначальную функцию, но уже с валидным токеном в headers

        // return window.location.replace(loginUrl); // если токен отсутствует, то перенаправляем пользователя на страницу авторизации
    }

    if (!options.headers) { // если в запросе отсутствует headers, то задаем их
        console.log("there aren't headers");
        options.headers = {};
    }

    if (tokenData_t) {
        console.log("tokenData exist");
        if (Date.now() >= expires) { // проверяем не истек ли срок жизни токена
            console.log("token isn't valid");
            try {
                const newToken = await refreshToken(access_t); // если истек, то обновляем токен с помощью refresh_token
                saveToken(newToken);
            } catch (err) { // если тут что-то пошло не так, то перенаправляем пользователя на страницу авторизации
                return window.location.replace(loginUrl);
            }
        }
        options.headers.Authorization = `Bearer ${access_t}`; // добавляем токен в headers запроса
    }
    return fetch(url, options); // возвращаем изначальную функцию, но уже с валидным токеном в headers
}

function getData(url, field_id) {
    // var access_t = null;
    // var ls = localStorage.getItem('tokenData');
    // console.log(ls);
    // var tokenData = localStorage.tokenData;
    // console.log(tokenData);
    // var token = tokenData.access_token;
    // console.log(token);
    // var js = JSON.parse(ls);
    // console.log(js);
    // var access_t = js.access_token;
    // console.log(access_t);
    var access_t = JSON.parse(localStorage.getItem('tokenData'))['access_token']; //убедиться что работает
    // access_t = JSON.parse(localStorage.getItem('token'))['access_token'];
    fetchWithAuth(url,
        {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Token: ${access_t}` //убедиться что работает
            }
        }).then(response => {//не уверена что так надо
        // fetch(response).then(response => {
        return response.text();
    }).then(data => {
        document.getElementById(field_id).value = data;
        // input = document.getElementById(id);
        console.log(data);
        // id.value = data;
    }).catch(err => {
        // alert("Fail");
        console.log(err);
    });
}

getData("http://localhost:8080/archery/profile/getFirstName", 'inputFirstname');
// getData("http://localhost:8080/archery/profile/getLastName", 'inputLastName');
// getData("http://localhost:8080/archery/test/studentList", 'inputEmailAddress');
// getData("http://localhost:8080/archery/profile/getPhoneNumber", 'inputPhone');
// getData("http://localhost:8080/archery/test/studentList", 'inputBirthday');

