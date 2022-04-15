function login() {

    var user = document.getElementsByName('email').values();
    var pass = document.getElementsByName('pass').values();

    var req = new XMLHttpRequest();
    console.log("first attempt");
    req.open("POST", "http://localhost:63342/archery/auth/signIn", true);
    console.log("2 attempt");
    req.setRequestHeader('Content-Type', 'application/json');
    //req.withCredentials = true;
    req.send(JSON.stringify({login: user, password: pass}));
    console.log("3 attempt");

    // if(req.status === 200) {
    location.href = "../../lk.html";
    //window.location = "../../lk.html";
    // } else {
    //     alert("Some fail");
    // }

    // req.onreadystatechange = function() {
    //     if(req.readyState === XMLHttpRequest.DONE) {
    //         if (req.status === 200) {
    //            // window.location = "http://localhost:63342/Archery_App/website/web/lk.html?_ijt=finm44415ind7a9ordd7imhmq4&_ij_reload=RELOAD_ON_SAVE";
    //         window.location = "../../lk.html";
    //         }
    //     }
    //   /*  if (req.readyState === XMLHttpRequest.DONE) {
    //         if (req.status === 200) {
    //             document.getElementById("log_form").style.display = 'none';
    //             document.getElementById("logged_user").style.display = 'block';
    //             document.getElementById("logged_user").textContent = document.getElementById("user_id").value;
    //             document.getElementById("logout_button").style.display = 'block';
    //             hide_error();
    //         }
    //         else if (req.status == 401) {
    //             document.getElementById('error_text').textContent = "User/password is incorrect";
    //             document.getElementById('error').style.display="";
    //         }
    //    }*/
    // }
}

// (function ($) {
//     "use strict";
//
//     /*==================================================================
//     [ Validate ]*/
//     var input = $('.validate-input .input100');
//
//     $('.validate-form').on('submit',function(){
//         var check = true;
//
//         for(var i=0; i<input.length; i++) {
//             if(validate(input[i]) === false){
//                 showValidate(input[i]);
//                 check=false;
//             }
//         }
//
//         return check;
//     });
//
//
//     $('.validate-form .input100').each(function(){
//         $(this).focus(function(){
//            hideValidate(this);
//         });
//     });
//
//     function validate (input) {
//         if($(input).attr('type') === 'email' || $(input).attr('name') === 'email') {
//             if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
//                 return false;
//             }
//         }
//         else {
//             if($(input).val().trim() === ''){
//                 return false;
//             }
//         }
//     }
//
//     function showValidate(input) {
//         var thisAlert = $(input).parent();
//
//         $(thisAlert).addClass('alert-validate');
//     }
//
//     function hideValidate(input) {
//         var thisAlert = $(input).parent();
//
//         $(thisAlert).removeClass('alert-validate');
//     }
//
//
//
// })(jQuery);