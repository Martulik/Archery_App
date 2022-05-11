function saveToken(token) {
    localStorage.setItem('tokenData', JSON.stringify(token));
}

function refreshToken(token) {
    return fetch('http://localhost:8080/archery/auth/refreshToken', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({token}),
    })
        .then((res) => {
            if (res.status === 200) {
                const tokenData = res.json(); //не уверена что так
                saveToken(JSON.stringify(tokenData));
                return Promise.resolve();
            }
            return Promise.reject();
        });
}

// export
async function fetchWithAuth(url, options) {
    const loginUrl = '../../login/login.html';
    var tokenData_t = null;
    var access_t = null;
    var expires = null;
    if (localStorage.tokenData) {
        // console.log("token exist");
        tokenData_t = localStorage.tokenData;
        // console.log(localStorage.tokenData);
        access_t = JSON.parse(localStorage.getItem('tokenData'));
        // console.log(access_t);
        expires = JSON.parse(localStorage.getItem('expiresIn'));
        // console.log(expires);

    } else {
        console.log("localStorage.authToken = null");
        return window.location.replace(loginUrl);
    }

    if (!options.headers) {
        console.log("there aren't headers");
        options.headers = {}; // Нужно добавить необходимые заголовки
    }

    if (tokenData_t) {
        // console.log("tokenData exist");
        if (Date.now() >= expires) {
            console.log("token isn't valid");
            try {
                const newToken = await refreshToken(access_t);
                saveToken(newToken);
            } catch (err) {
                return window.location.replace(loginUrl);
            }
        }
        options.headers.Authorization = `Bearer ${access_t}`;
    }
    return fetch(url, options);
}

function getData(url) {
    let inf;
    fetchWithAuth(url,
        {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => {
        return response.text();
    }).then(data => {
        // document.getElementById(field_id).value = data;
        inf = data;
        // console.log(data);
    }).catch(err => {
        // alert("Fail");
        console.log(err);
    });
    return inf;
}

//
// getData("http://localhost:8080/archery/profile/getFirstName", 'inputFirstname');
// getData("http://localhost:8080/archery/profile/getLastName", 'inputLastName');
// getData("http://localhost:8080/archery/profile/getEmail", 'inputEmailAddress');
// getData("http://localhost:8080/archery/profile/getPhoneNumber", 'inputPhone');
// getData("http://localhost:8080/archery/profile/getBirthDate", 'inputBirthday');
//


function save_changes(selectedOption) {
    var value = selectedOption.value;
    console.log(value);
}

function addRow(id, user) {
    var tbody = document.getElementById(id).getElementsByTagName("tbody")[0];
    var row = document.createElement("tr")

    // name
    var td1 = document.createElement("td")
    row.appendChild(td1);
    let div1 = document.createElement("div");
    div1.className = "widget-26-job-title";
    td1.appendChild(div1);
    let a = document.createElement("a");
    a.setAttribute('href', '#');
    a.textContent = user.first_name + " " + user.last_name;
    div1.appendChild(a);

    // email
    var td2 = document.createElement("td")
    row.appendChild(td2);
    let div2 = document.createElement("div");
    div2.className = "widget-26-job-title";
    td2.appendChild(div2);
    let p1 = document.createElement("p");
    p1.className = "type m-0";
    p1.textContent = user.email;
    div2.appendChild(p1);

    // phone
    var td3 = document.createElement("td")
    row.appendChild(td3);
    let div3 = document.createElement("div");
    div3.className = "widget-26-job-title";
    td3.appendChild(div3);
    let p2 = document.createElement("p");
    p2.className = "type m-0";
    p2.textContent = user.phone_number;
    div3.appendChild(p2);

    // birth date
    var td4 = document.createElement("td")
    row.appendChild(td4);
    let div4 = document.createElement("div");
    div4.className = "widget-26-job-title";
    td4.appendChild(div4);
    let p3 = document.createElement("p");
    p3.className = "type m-0";
    p3.textContent = user.birth_date.slice(0, 10);
    div4.appendChild(p3);

    // rang
    var rangs = ["middles", "juniors", "seniors", "no rang"];
    var td5 = document.createElement("td")
    row.appendChild(td5);
    let div5 = document.createElement("div");
    let span = document.createElement("span");

    let div6 = document.createElement("div");
    div6.className = "row no-gutters form-control col-lg-3 col-md-3 col-sm-12 p-0";
    let select = document.createElement("select");
    select.id = 'sel';
    select.className = "form-control category-select";
    select.onchange = function () {
        console.log("document", document.getElementById("sel").value);
    };
    for (var i = 0; i < rangs.length; i++) {
        var option = document.createElement("option");
        option.value = rangs[i];
        option.text = rangs[i];
        // option.className = "widget-26-job-category bg-soft-warning";
        select.appendChild(option);
    }

    let rank = user.rank_name;
    if (rank != null) {
        if (rank.rank_name == rangs[0]) {
            div5.className = "widget-26-job-category bg-soft-warning";
            option.className = "widget-26-job-category bg-soft-warning";
            select[0].selected = true;
        } else if (rank.rank_name == rangs[1]) {
            div5.className = "widget-26-job-category bg-soft-success";
            select[1].selected = true;
            option.className = "widget-26-job-category bg-soft-success";
        } else if (rank.rank_name == rangs[2]) {
            div5.className = "widget-26-job-category bg-soft-danger";
            select[2].selected = true;
            option.className = "widget-26-job-category bg-soft-danger";
        }
        span.textContent = rank.rank_name;
    } else {
        div5.className = "widget-26-job-category bg-soft-secondary";
        span.textContent = rangs[3];
        select[3].selected = true;
        option.className = "widget-26-job-category bg-soft-secondary";
    }


    div5.appendChild(select);
    // td5.appendChild(select);
    td5.appendChild(div5);
    td5.appendChild(div6);
    // div5.appendChild(span);

    tbody.appendChild(row);
}


function getAllUsers() {
    let list;
    console.log("first attempt");
    var apiUrl = "http://localhost:8080/archery/test/studentList";
    fetch(apiUrl).then(response => {
        return response.json();
    }).then(data => {
        // Work with JSON data here
        console.log("Success getAllUsers");
        console.log(data);
        list = data;
    }).catch(err => {
        alert("Fail");
        console.log(err);
    });
    return list;
}

function addAllRows() {
    let array;
    var apiUrl = "http://localhost:8080/archery/test/studentList";
    fetch(apiUrl).then(response => {
        return response.json();
    }).then(data => {
        // Work with JSON data here
        console.log("Success getAllUsers");
        console.log(data);
        array = data;
        for (let i = 0; i < array.length; i++) {
            addRow('users', array[i]);
        }
    }).catch(err => {
        alert("Fail");
        console.log(err);
    });

    // var promise = new Promise((resolve, reject) => {
    //     array = getAllUsers();
    //     resolve();
    // });
    // promise.then(() => {
    //     console.log("in");
    //     console.log(array);
    //     for (let i = 0; i < 10; i++) {
    //         addRow('users', array[i]);
    //     }
    // });

// let data = JSON.parse(users);
    // console.log(users);
// let len = users.length;

}

addAllRows();

