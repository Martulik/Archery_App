document.querySelector('.days').addEventListener('click', function () {
    if (!event.target.classList.contains("next-date") && !event.target.classList.contains("prev-date")) {
        if (document.querySelector('#todayid') !== null) {
            const t = document.querySelector('#todayid').classList;
            t.toggle("today");
            document.querySelector('#todayid').removeAttribute("id");
        }
        event.target.className = "today";
        event.target.setAttribute("id","todayid");
        var m = document.querySelector(".date h1").innerHTML.valueOf();
        var d = document.querySelector(".today").innerHTML.valueOf();
        document.querySelector(".date p").innerHTML = d + " " + m;
    }
});


document.querySelector('.schedule .days').addEventListener('click', function () {
    let elem = document.getElementsByClassName("timeSchedule");
    /*if (elem.style.display === "none"){*/
    /*
    }*/
    event.style.display ='flex';
});
