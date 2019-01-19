$(document).ready(function() {
    $("#loginForm").show("medium");

    $("#loginToRegister").click(function() {
        $("#loginForm").hide("medium");
        $("#registerForm").show("slow");
    });

    $("#registerToLogin").click(function() {
        $("#registerForm").hide("medium");
        $("#loginForm").show("slow");
    });


    $("#loginSubmit").click(function() {
        var myemail = $("#lemail").val();
        var mypassword = $("#lpassword").val();

        if (myemail == "" && mypassword == "") {
            $("#lreport").text("Πρέπει να εισάγεται το eimail και Password");
        } else if (myemail == "") {
            $("#lreport").text("Πρέπει να εισάγεται το eimail");
        } else if (mypassword == "") {
            $("#lreport").text("Πρέπει να εισάγεται το password");
        }  else {
            // alert(email);
            // alert(password);
            
            $.post("../login.php",
    {
        email: myemail,
                    password: mypassword,
                    type: 'admin'
    },
    function(data, status){
        console.log(data);
        var obj = jQuery.parseJSON(data);
        if(obj.success)
        {
            window.location.replace("reportList.html");
        }
        else
        {
            $("#lreport").text(obj.message);
        }
    });
        }
    });

    $("#registerSubmit").click(function() {
        var memail = $("#remail").val();
        var mpassword = $("#rpassword").val();
        var mname = $("#rname").val();
        var msurname = $("#rsurname").val();

        if (memail == "" || mpassword == "" || mname == "" || msurname == "") {
            $("#rreport").text("Πρέπει να εισάγεται όλα τα στοιχεία");
        } else {
            $.post("../register.php",
    {
        email: memail,
                    password: mpassword,
                    name:mname,
                    surname:msurname,
                    type: 'admin'
    },
    function(data, status){
          var obj = jQuery.parseJSON(data);
        if(obj.success)
        {
              $("#registerForm").hide("medium");
        $("#loginForm").show("slow");
            
        }else
        {
            console.log(obj.message);
            $("#rreport").text(obj.message);
        }
    });
        }


    });

});