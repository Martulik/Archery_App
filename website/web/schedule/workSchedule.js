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
    }
});