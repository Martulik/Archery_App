document.querySelector('.days').addEventListener('click', function () {
    if (!event.target.classList.contains("next-date") && !event.target.classList.contains("prev-date")) {
        const t = document.querySelector('#todayid').classList;
        t.toggle("today");
        document.querySelector('#todayid').removeAttribute("id");

        event.target.className = "today";
        /* event.target.style.backgroundColor = '#BAD4AA';
         event.target.style.color = 'black';*/
        //event.target.classList.removeAttribute("id");
        event.target.setAttribute("id","todayid");
        var m = document.querySelector(".date h1").innerHTML.valueOf();
        var d = document.querySelector(".today").innerHTML.valueOf();
        document.querySelector(".date p").innerHTML = d + " " + m;
    }
});

function chpok(){
    let elem = document.getElementById('timeWork');
     //смотрим, включен ли сейчас элемент
    if (elem.style.display === "none"){
        elem.style.display="";
    }
}