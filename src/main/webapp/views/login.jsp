

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<div class="jumbotron text-center" style="margin-bottom:0">
    <h1>Snickr</h1>
</div>


<%
    if(session.getAttribute("userid") != null)
    {
        out.println("<script>window.location.href='/applicationSecurity/personalPage'</script>");
        return;
    }
%>

<div >
    <form style="padding: 50px" class="bs-example bs-example-form" role="form">
        <div class="input-group" style="position: relative; left:40%; top:10%">
            <span class="input-group-addon">username</span>
            <input id="login_username" type="email" class="form-control" placeholder="your email" style="margin:0 auto;width: 200px;">
        </div>
        <br>
        <div class="input-group" style="position: relative; left:40%; top:10%">
            <span class="input-group-addon">password</span>
            <input id="login_password" type="password" class="form-control" style="width: 200px;">
        </div>
        <br>
        <button type="button" class="btn btn-success" style="position: relative; left:40%;width: 100px" onclick="submitLogin()">sign in</button>
        <button type="button" class="btn btn-primary" style="position: relative; left:46%;width: 100px" data-toggle="modal" data-target="#register">sign up</button>
        <br><br>
        <p id="login_status" style="position: relative; left:45%; top:8%;color: #FF0000"></p>
        <div class="modal fade" id="register" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">
                            create a new account
                        </h4>
                    </div>
                    <div class="modal-body">
                        <form style="padding: 50px" class="bs-example bs-example-form" role="form">
                            <div class="input-group" style="position: relative; left:30%; top:8%">
                                <span class="input-group-addon">email</span>
                                <input id="register_email" type="text" class="form-control" style="width: 200px;">
                            </div>
                            <br>
                            <div class="input-group" style="position: relative; left:30%; top:8%">
                                <span class="input-group-addon">name</span>
                                <input id="register_name" type="text" class="form-control" style="width: 200px;">
                            </div>
                            <br>
                            <div class="input-group" style="position: relative; left:30%; top:8%">
                                <span class="input-group-addon">nickname</span>
                                <input id="register_nickname" type="text" class="form-control" style="width: 200px;">
                            </div>
                            <br>
                            <div class="input-group" style="position: relative; left:30%; top:8%">
                                <span class="input-group-addon">password</span>
                                <input id="register_password" type="password" class="form-control" style="width: 200px;">
                            </div>
                            <br>
                            <br>
                            <p id="register_status" style="position: relative; left:30%; top:8%;color: #FF0000"></p>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">close
                        </button>
                        <button type="button" class="btn btn-primary" onclick="submitRegister()">
                            submit
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
    </form>
</div>

<script>
function submitRegister() {
    var patt=/^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;

    if ( $("#register_email").val()==""||$("#register_name").val()==""||$("#register_nickname").val()==""
        ||$("#register_password").val()==""){
        $("#register_status").text("all information must be input");

    }else if (patt.test($("#register_email").val())==false){
        $("#register_status").text("invalid email format");
    }else {
        $.ajax({
            type: "post",
            async: false, // 同步执行
            url: "/applicationSecurity/register",
            data: {
                "email": $("#register_email").val(),
                "name": $("#register_name").val(),
                "nickname": $("#register_nickname").val(),
                "password" : $("#register_password").val()
            },
            success: function (result) {
                if (result == "SUCCESS") {
                    $("#register_status").text("log up succeed");
                } else if (result == "FAILURE") {
                    $("#register_status").text("this email has been used!");
                } else {
                }
            },
            error: function () {
            }
        })


    }

    setTimeout(function () {
        $("#register_status").text("");
    },3000)
}

function submitLogin() {
    if ( $("#login_username").val()==""||$("#login_password").val()==""){
        $("#login_status").text("all information must be input");

    } else {
        $.ajax({
            type: "post",
            async: false, // 同步执行
            url: "/applicationSecurity/login",
            data: {
                "username": $("#login_username").val(),
                "password": $("#login_password").val()
            },
            success: function (result) {
                if (result == "FAILURE") {
                    $("#login_status").text("invalid username or password");
                } else {
                    $("#login_status").text("log in succeed");
                    setTimeout("window.location.href = '/applicationSecurity/personalPage'", 0);
                }
            },
            error: function () {
            }
        })


    }

    setTimeout(function () {
        $("#login_status").text("");
    },3000)
}
</script>
</body>
</html>
