// document.addEventListener('DOMContentLoaded', () => {
//     const loginForm = document.querySelector('#login');
//     const signUpForm = document.querySelector('#sign-up');
//
//     document.querySelector('#linkLogin')
//         .addEventListener('click', e => {
//             e.preventDefault();
//             signUpForm.classList.add("form-hidden");
//             loginForm.classList.remove("form-hidden");
//         });
//
//     document.querySelector('#linkSignUp')
//         .addEventListener('click', e => {
//             e.preventDefault();
//             loginForm.classList.add("form-hidden");
//             signUpForm.classList.remove("form-hidden");
//         });
// })


function login() {

    var user = document.getElementsByName('email').values();
    var pass = document.getElementsByName('pass').values();

    var req = new XMLHttpRequest();
    req.open("POST", "http://localhost:8080/archery/auth/signIn", true);
    req.setRequestHeader('Content-Type', 'application/json');
    req.withCredentials = true;
    req.onreadystatechange = function() {
        if(req.readyState === XMLHttpRequest.DONE){
            if (req.status === 200) {
               // window.location = "http://localhost:63342/Archery_App/website/web/lk.html?_ijt=finm44415ind7a9ordd7imhmq4&_ij_reload=RELOAD_ON_SAVE";
            window.location = "../../lk.html";
            }
        }
      /*  if (req.readyState === XMLHttpRequest.DONE) {
            if (req.status === 200) {
                document.getElementById("log_form").style.display = 'none';
                document.getElementById("logged_user").style.display = 'block';
                document.getElementById("logged_user").textContent = document.getElementById("user_id").value;
                document.getElementById("logout_button").style.display = 'block';
                hide_error();
            }
            else if (req.status == 401) {
                document.getElementById('error_text').textContent = "User/password is incorrect";
                document.getElementById('error').style.display="";
            }
       }*/
    }
    req.send(JSON.stringify({login: user, password: pass}));
}