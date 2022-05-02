
function getData(url, field_id) {
    fetch(url).then(response => {
        return response.json();
    }).then(data => {
        document.getElementById(field_id).value = data;
        // input = document.getElementById(id);
        console.log(data);
        // id.value = data;
    }).catch(err => {
        alert("Fail");
        console.log(err);
    });
}

getData("http://localhost:8080/archery/test/studentList",'inputFirstname');
getData("http://localhost:8080/archery/test/studentList",'inputLastName');
getData("http://localhost:8080/archery/test/studentList",'inputEmailAddress');
getData("http://localhost:8080/archery/test/studentList",'inputPhone');
getData("http://localhost:8080/archery/test/studentList",'inputBirthday');

