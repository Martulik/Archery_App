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
    var td_name = document.createElement("td")
    row.appendChild(td_name);
    let div_name = document.createElement("div");
    div_name.className = "widget-26-job-title";
    td_name.appendChild(div_name);
    let a = document.createElement("a");
    // a.setAttribute('location.href', '../../index.html');
    a.href = '../../lk/lk.html';
    // a.onclick = function (){};
    a.textContent = user.first_name + " " + user.last_name;
    div_name.appendChild(a);

    // // email
    // var td2 = document.createElement("td")
    // row.appendChild(td2);
    // let div2 = document.createElement("div");
    // div2.className = "widget-26-job-title";
    // td2.appendChild(div2);
    // let p1 = document.createElement("p");
    // p1.className = "type m-0";
    // p1.textContent = user.email;
    // div2.appendChild(p1);
    //
    // // phone
    // var td3 = document.createElement("td")
    // row.appendChild(td3);
    // let div3 = document.createElement("div");
    // div3.className = "widget-26-job-title";
    // td3.appendChild(div3);
    // let p2 = document.createElement("p");
    // p2.className = "type m-0";
    // p2.textContent = user.phone_number;
    // div3.appendChild(p2);
    //
    // // birth date
    // var td4 = document.createElement("td")
    // row.appendChild(td4);
    // let div4 = document.createElement("div");
    // div4.className = "widget-26-job-title";
    // td4.appendChild(div4);
    // let p3 = document.createElement("p");
    // p3.className = "type m-0";
    // p3.textContent = user.birth_date.slice(0, 10);
    // div4.appendChild(p3);


    // rank
    var ranks = ["middles", "juniors", "seniors", "no rank"];
    var td_rank = document.createElement("td")
    row.appendChild(td_rank);
    let div_rank = document.createElement("div");
    div_rank.className = "widget-26-job-title";
    let span_rank = document.createElement("span");
    let select_rank = document.createElement("select");
    select_rank.id = `sel_rank + ${user.id}`;
    select_rank.className = "form-control category-select";
    select_rank.onchange = function () {
        let url = "http://localhost:8080/archery/admin/setRankAndApprove";
        var data = {
            id: user.id,
            rank: document.getElementById(`sel_rank + ${user.id}`).value
        };
        fetchWithAuth(url,
            {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
            }).then((res) => {
            if (res.status === 200) {
            } else {
                window.alert("fail");
            }
        });
        console.log("saving...");
    };
    for (var i = 0; i < ranks.length; i++) {
        var option_rank = document.createElement("option");
        option_rank.value = ranks[i];
        option_rank.text = ranks[i];
        select_rank.appendChild(option_rank);
    }

    let rank = user.rank_name;
    if (rank != null) {
        if (rank.rank_name == ranks[0]) {
            select_rank.className = "widget-26-job-category bg-soft-warning";
            select_rank[0].selected = true;
        } else if (rank.rank_name == ranks[1]) {
            select_rank.className = "widget-26-job-category bg-soft-success";
            select_rank[1].selected = true;
        } else if (rank.rank_name == ranks[2]) {
            select_rank.className = "widget-26-job-category bg-soft-danger";
            select_rank[2].selected = true;
        } else if (rank.rank_name == ranks[3]) {
            select_rank.className = "widget-26-job-category bg-soft-secondary";
            select_rank[3].selected = true;
        }
        span_rank.textContent = rank.rank_name;
    } else {
        select_rank.className = "widget-26-job-category bg-soft-secondary";
        span_rank.textContent = ranks[3];
        select_rank[3].selected = true;
    }

    div_rank.appendChild(select_rank);
    td_rank.appendChild(div_rank);


    // абонемент
    var passes = ["unlimited", "8_classes", "4_classes", "no pass"];
    var td_pass = document.createElement("td")
    row.appendChild(td_pass);
    let div_pass = document.createElement("div");
    div_pass.className = "widget-26-job-title";
    let span_pass = document.createElement("span");
    let select_pass = document.createElement("select");
    select_pass.id = `sel_pass + ${user.id}`;
    select_pass.className = "form-control category-select";
    select_pass.onchange = function () {
        let url = ""; // добавить url
        console.log("id", user.id);
        console.log("document", document.getElementById(`sel_pass + ${user.id}`).value);
        var data = {
            id: user.id,
            rank: document.getElementById(`sel_pass + ${user.id}`).value
        };
        fetchWithAuth(url,
            {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
            }).then((res) => {
            if (res.status === 200) {
            } else {
                window.alert("fail");
            }
        });
        console.log("saving...");
    };
    for (i = 0; i < passes.length; i++) {
        var option_pass = document.createElement("option");
        option_pass.value = passes[i];
        option_pass.text = passes[i];
        select_pass.appendChild(option_pass);
    }
    // let pass = user.pass_name; // тут по-другому (не pass_name)
    // if (pass != null) {
    //     if (pass.pass_name == passes[0]) {
    //         select_pass.className = "widget-26-job-category bg-soft-warning";
    //         select_pass[0].selected = true;
    //     } else if (pass.pass_name == passes[1]) {
    //         select_pass.className = "widget-26-job-category bg-soft-success";
    //         select_pass[1].selected = true;
    //     } else if (pass.pass_name == passes[2]) {
    //         select_pass.className = "widget-26-job-category bg-soft-danger";
    //         select_pass[2].selected = true;
    //     } else if(pass.pass_name == passes[3]){
    //         select_pass.className = "widget-26-job-category bg-soft-secondary";
    //         select_pass[3].selected = true;
    //     }
    //     span_pass.textContent = pass.pass_name;
    // } else {
    //     select_pass.className = "widget-26-job-category bg-soft-secondary";
    //     span_pass.textContent = passes[3];
    //     select_pass[3].selected = true;
    // }
    select_pass.className = "widget-26-job-category bg-soft-secondary";
    span_pass.textContent = passes[3];
    select_pass[3].selected = true;

    div_pass.appendChild(select_pass);
    td_pass.appendChild(div_pass);

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

function print(array, start, end) {
    for (let j = start; j < end; i++) {
        addRow('users', array[i]);
    }
}

function pagination(array) {
    var numOfStudentsOnPage = 15;
    var numOfPages = Math.ceil(array.length / numOfStudentsOnPage);
    console.log(numOfPages);

    var tbody = document.getElementById('users').getElementsByTagName("tbody")[1];
    var row = document.createElement("tr")
    var td_name = document.createElement("td")
    let row_div = document.createElement("div");
    row_div.className = "row";


    // tbody.appendChild(row);

    let col12 = document.createElement("div");
    col12.className = "col-12";
    row_div.appendChild(col12);

    let card = document.createElement("div");
    card.className = "card card-margin";
    col12.appendChild(card);

    let card_body = document.createElement("div");
    card_body.className = "card-body";
    card.appendChild(card_body);


    let nav = document.createElement("nav");
    nav.className = "d-flex justify-content-center";
    card_body.appendChild(nav);

    let ul = document.createElement("ul");
    ul.className = "pagination pagination-base pagination-boxed pagination-square mb-0";
    nav.appendChild(ul);

    for (var i = 0; i <= numOfPages; ++i) {

        let li = document.createElement("ul");
        li.className = "page-item";
        ul.appendChild(li);

        let a = document.createElement("a");
        a.className = "page-link no-border";
        a.href = "#";
        a.id = `a + ${i}`;
        a.textContent = i + 1;
        a.onclick = function () {
            print(array, i * numOfPages, (i + 1) * numOfPages);
        };
        li.appendChild(a);
    }

    row.appendChild(td_name);
    td_name.appendChild(row_div);

    tbody.appendChild(row);
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
        console.log("pagnation start")
        pagination(array);
        console.log("pagnation end")

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

