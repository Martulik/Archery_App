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

