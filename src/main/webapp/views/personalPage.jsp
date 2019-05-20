<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sshProject.entity.Workspace" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%
    if (session.getAttribute("userid") == null) {
        out.println("<script>alert('please login first');window.location.href='/applicationSecurity/login.jsp'</script>");
    }
%>
<%--<p>my userid is <%=session.getAttribute("userid") %></p>--%>
<%--<p>my workspaceid is <%=session.getAttribute("workspaceid") %></p>--%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">${workspace.name} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li style="margin-left: 10px">other workspace:</li>
                        <c:forEach items="${workspaces}" var="w">
                            <li><a href="/applicationSecurity/personalPage?toworkspaceid=${w.workspaceid}">${w.name}</a></li>
                        </c:forEach>
                        <li role="separator" class="divider"></li>
                        <li><a href="#" data-toggle="modal" data-target="#create_workspace">create a new workspace</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#" data-toggle="modal" data-target="#create_channel">create a new channel</a></li>
                    </ul>
                </li>
            </ul>

            <form class="navbar-form navbar-left">
                <!-- Single button -->
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        send invitation <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="#" data-toggle="modal" data-target="#invite_workspace">invite people to workspace</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#" data-toggle="modal" data-target="#add_admin">add admin</a></li>
                    </ul>
                </div>
            </form>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="/applicationSecurity/notification">notification<span id="notification_number" class="badge" style="color: #FF0000"></span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">
                        Hello&nbsp;${user.nickname}  <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#" data-toggle="modal" data-target="#my_information">my information</a></li>
                        <%--<li><a href="#">change my nickname</a></li>--%>
                        <%--<li><a href="#">change my password</a></li>--%>
                        <li role="separator" class="divider"></li>
                        <li><a href="/applicationSecurity/logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->


    </div>
</nav>


<div class="modal fade" id="create_workspace" tabindex="-1" role="dialog" aria-labelledby="create_workspace_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="create_workspace_modal">
                    create a new workspace
                </h4>
            </div>
            <div class="modal-body">
                <form style="padding: 50px" class="bs-example bs-example-form" role="form">
                    <label for="create_workspace_name" class="col-sm-2 control-label">name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="create_workspace_name" placeholder="please enter the name">
                    </div>
                    <br>
                    <br>
                    <label for="create_workspace_description" class="col-sm-2 control-label">description</label>
                    <div class="col-sm-10">
                        <textarea rows="3" class="form-control" id="create_workspace_description" ></textarea>
                    </div>

                    <br>
                    <br>
                    <p id="create_workspace_status" style="position: relative; left:30%; top:8%;color: #FF0000"></p>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">close
                </button>
                <button type="button" class="btn btn-primary" onclick="createWorkspace()">
                    submit
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="create_channel" tabindex="-1" role="dialog" aria-labelledby="create_channel_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="create_channel_modal">
                    create a new channel
                </h4>
            </div>
            <div class="modal-body">
                <form style="padding: 50px" class="bs-example bs-example-form" role="form">
                    <label for="create_workspace_name" class="col-sm-2 control-label">name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="create_channel_name" placeholder="please enter the name">
                    </div>
                    <br>
                    <br>
                    <label for="create_workspace_description" class="col-sm-2 control-label">type</label>
                    <div class="col-sm-10">
                        <select id="create_channel_type" class="combobox" style="width: 380px">
                            <option></option>
                            <option value="public">public</option>
                            <option value="private">private</option>
                        </select>
                    </div>

                    <br>
                    <br>
                    <p id="create_channel_status" style="position: relative; left:30%; top:8%;color: #FF0000"></p>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">close
                </button>
                <button type="button" class="btn btn-primary" onclick="createChannel()">
                    submit
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="invite_workspace" tabindex="-1" role="dialog" aria-labelledby="invite_workspace_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="invite_workspace_modal">
                    invite people to this workspace
                </h4>
            </div>
            <div class="modal-body">
                <form style="padding: 50px" class="bs-example bs-example-form" role="form">
                    <label for="create_workspace_name" class="col-sm-2 control-label">email</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="invite_workspace_email" placeholder="please enter the email">
                    </div>
                    <br>
                    <br>
                    <p id="invite_workspace_status" style="position: relative; left:30%; top:8%;color: #FF0000"></p>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">close
                </button>
                <button type="button" class="btn btn-primary" onclick="inviteWorkspace()">
                    submit
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<div class="modal fade" id="invite_direct_channel" tabindex="-1" role="dialog" aria-labelledby="invite_direct_channel_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="invite_direct_channel_modal">
                    invite people to direct channel
                </h4>
            </div>
            <div class="modal-body">
                <form style="padding: 50px" class="bs-example bs-example-form" role="form">
                    <label for="create_workspace_name" class="col-sm-2 control-label">email</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="invite_direct_channel_email" placeholder="please enter the email">
                    </div>
                    <br>
                    <br>
                    <p id="invite_direct_channel_status" style="position: relative; left:30%; top:8%;color: #FF0000"></p>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">close
                </button>
                <button type="button" class="btn btn-primary" onclick="inviteDirectChannel()">
                    submit
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="add_admin" tabindex="-1" role="dialog" aria-labelledby="add_admin_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="add_admin_modal">
                    make others to be admin too
                </h4>
            </div>
            <div class="modal-body">
                <form style="padding: 50px" class="bs-example bs-example-form" role="form">
                    <label for="create_workspace_name" class="col-sm-2 control-label">email</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="add_admin_email" placeholder="please enter the email">
                    </div>
                    <br>
                    <br>
                    <p id="add_admin_status" style="position: relative; left:30%; top:8%;color: #FF0000"></p>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">close
                </button>
                <button type="button" class="btn btn-primary" onclick="addAdmin()">
                    submit
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="my_information" tabindex="-1" role="dialog" aria-labelledby="my_information_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="my_information_modal">
                    my information
                </h4>
            </div>
            <div class="modal-body">
                <p>my name: ${user.name}</p>
                <p>my nickname: ${user.nickname}</p>
                <p>workspace description: ${workspace.description}</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">close
                </button>
                <button type="button" class="btn btn-primary" onclick="addAdmin()">
                    submit
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>



<div class="modal fade" id="invite_public_channel" tabindex="-1" role="dialog" aria-labelledby="invite_public_channel_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="invite_public_channel_modal">
                    invite people to private channel
                </h4>
            </div>
            <div class="modal-body">
                <form style="padding: 50px" class="bs-example bs-example-form" role="form">
                    <label for="create_workspace_name" class="col-sm-2 control-label">email</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="invite_public_channel_email" placeholder="please enter the email">
                    </div>
                    <br>
                    <br>
                    <label for="create_workspace_description" class="col-sm-2 control-label">name</label>
                    <div class="col-sm-10">
                        <select id="invite_public_channel_channelid" class="combobox" style="width: 380px">
                            <option></option>
                            <c:forEach items="${privateChannel}" var="p">
                                <option value=${p.channelid}>${p.name}</option>
                            </c:forEach>

                        </select>
                    </div>

                    <br>
                    <br>
                    <p id="invite_public_channel_status" style="position: relative; left:30%; top:8%;color: #FF0000"></p>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">close
                </button>
                <button type="button" class="btn btn-primary" onclick="invitePublicChannel()">
                    submit
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<br>
<br>
<br>
<br>
<div class="container" style="padding-left:50px;margin-left:0px">
    <div class="row">
        <div class="col-sm-3" style="padding-left:0px;margin-left:0px">
            <input type="hidden" id="show_channel" >
            <div class="panel-group" id="accordion2">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseTwo">
                                public channels
                            </a>

                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse in">
                        <div class="panel-body" style="height: 120px;overflow: auto">
                            <c:forEach items="${publicChannel}" var="p">
                                <li><a href="#" name="${p.channelid}" onclick="changeChannel(this)">${p.name}</a></li>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel-group" id="accordion3">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion3"
                               href="#collapseThree">
                                direct channels
                            </a>
                            <a style="float: right" href="#" data-toggle="modal" data-target="#invite_direct_channel">+</a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse in">
                        <div class="panel-body" style="height: 120px;overflow: auto">
                            <c:forEach items="${directChannel}" var="d">
                                <li><a href="#" name="${d.channelid}" onclick="changeChannel(this)">${d.name}</a></li>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel-group" id="accordion4">


                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion4"
                               href="#collapseFour">
                                private channels
                            </a>
                            <a style="float: right" href="#" data-toggle="modal" data-target="#invite_public_channel">+</a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse in">
                        <div class="panel-body" style="height: 120px; overflow: auto">
                            <c:forEach items="${privateChannel}" var="p">
                                <li><a href="#" name="${p.channelid}" onclick="changeChannel(this)">${p.name}</a></li>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-9">
            <div id="channel_content" class="well well-lg" style="height: 450px;overflow: auto">
            </div>
            <div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="sendMessage()">+</button>
                      </span>
                <input type="text" class="form-control" placeholder="Message..." id="message_content">
            </div><!-- /input-group -->
        </div>
    </div>
</div>

<script>
    function  createWorkspace() {
        if ( $("#create_workspace_name").val()==""){
            $("#create_workspace_status").text("workspace name cannot be blank");

        }else {
            $.ajax({
                type: "post",
                async: false, // 同步执行
                url: "/applicationSecurity/createworkspace",
                data: {
                    "name": $("#create_workspace_name").val(),
                    "description": $("#create_workspace_description").val()
                },
                success: function (result) {
                    if (result == "FAILURE") {
                        $("#create_workspace_status").text("creation fail");
                    } else {
                        $("#create_workspace_status").text("creation succeed");
                        setTimeout("window.location.href = '/applicationSecurity/personalPage'", 0);
                        console.log(${workspace.workspaceid})
                    }


                },
                error: function () {
                }
            })


        }

        setTimeout(function () {
            $("#create_workspace_status").text("");
        },3000)
    }

    function createChannel() {
        if ( $("#create_channel_name").val()==""){
            $("#create_channel_status").text("channel name cannot be blank");

        }else {
            $.ajax({
                type: "post",
                async: false, // 同步执行
                url: "/applicationSecurity/createchannel",
                data: {
                    "name": $("#create_channel_name").val(),
                    "creatorid": ${user.userid},
                    "workspaceid": ${workspace.workspaceid},
                    "type": $("#create_channel_type").val()
                },
                success: function (result) {
                    if (result == "FAILURE") {
                        $("#create_channel_status").text("creation fail");
                    } else {
                        $("#create_channel_status").text("creation succeed");
                        setTimeout("window.location.href = '/applicationSecurity/personalPage'", 0);
                    }


                },
                error: function () {
                }
            })
        }
        setTimeout(function () {
            $("#create_channel_status").text("");
        },3000)
    }

    function invitePublicChannel() {
        if ( $("#invite_public_channel_email").val()==""){
            $("#invite_public_channel_status").text("email cannot be blank");

        }else {
            $.ajax({
                type: "post",
                async: false, // 同步执行
                url: "/applicationSecurity/invitepublicchannel",
                data: {
                    "email": $("#invite_public_channel_email").val(),
                    "invitorid": ${user.userid},
                    "channelid": $("#invite_public_channel_channelid").val(),
                    "workspaceid": ${workspace.workspaceid}
                },
                success: function (result) {

                    $("#invite_public_channel_status").text(result);

                },
                error: function () {
                }
            })
        }
        setTimeout(function () {
            $("#invite_public_channel_status").text("");
        },3000)
    }


    function inviteDirectChannel() {
        if ( $("#invite_direct_channel_email").val()==""){
            $("#invite_direct_channel_status").text("email cannot be blank");

        }else {
            $.ajax({
                type: "post",
                async: false, // 同步执行
                url: "/applicationSecurity/invitedirectchannel",
                data: {
                    "email": $("#invite_direct_channel_email").val(),
                    "invitorid": ${user.userid},
                    "workspaceid": ${workspace.workspaceid}
                },
                success: function (result) {

                    $("#invite_direct_channel_status").text(result);

                },
                error: function () {
                }
            })
        }
        setTimeout(function () {
            $("#invite_direct_channel_status").text("");
        },3000)
    }


    function inviteWorkspace() {
        if ( $("#invite_workspace_email").val()==""){
            $("#invite_workspace_status").text("email cannot be blank");

        }else {
            $.ajax({
                type: "post",
                async: false, // 同步执行
                url: "/applicationSecurity/inviteworkspace",
                data: {
                    "email": $("#invite_workspace_email").val(),
                    "inviterid": ${user.userid},
                    "workspaceid": ${workspace.workspaceid}
                },
                success: function (result) {
                        $("#invite_workspace_status").text(result);

                },
                error: function () {
                }
            })
        }
        setTimeout(function () {
            $("#invite_workspace_status").text("");
        },3000)
    }

    function addAdmin() {
        if ( $("#add_admin_email").val()==""){
            $("#add_admin_status").text("email cannot be blank");

        }else {
            $.ajax({
                type: "post",
                async: false, // 同步执行
                url: "/applicationSecurity/addadmin",
                data: {
                    "email": $("#add_admin_email").val(),
                    "inviterid": ${user.userid},
                    "workspaceid": ${workspace.workspaceid}
                },
                success: function (result) {
                    $("#add_admin_status").text(result);

                },
                error: function () {
                }
            })
        }
        setTimeout(function () {
            $("#add_admin_status").text("");
        },3000)
    }

   function changeChannel(e){
        console.log("yes")
       $("#show_channel").val($(e).attr("name")) ;
       $.ajax({
           type: "post",
           async: true, // 异步执行
           url: "/applicationSecurity/showchannel",
           data: {
               "channelid": $("#show_channel").val()
           },
           success: function (result) {
               var data = JSON.parse(result);
                   leng = data.length;
                   $("#channel_content").text("");
                   for (var ele in data){
                       $("#channel_content").append("" +
                           "            <div class='panel panel-default'>\n" +
                           "                            <div class='panel-heading'>\n" +
                           "\n" +
                           "                            <h3 class='panel-title'>\n" +
                           "                            "+data[ele]["nickname"]+"\n" +
                           "                        </h3>\n" +
                           "\n" +
                           "                            </div>\n" +
                           "                            <div class='panel-body' style='overflow: hidden'>\n" +
                           "                            <p>"+ data[ele]["senddate"] +"</p>\n" +
                           "                        <p>"+data[ele]["content"]+"</p>\n" +
                           "                        </div>\n" +
                           "                        </div>"
                       )

               }
           },
           error: function () {
           }
       })

       // alert($("#show_channel").val()==15)
   }

    function sendMessage(){
        if ($("#show_channel").val()!=''){
            $.ajax({
                type: "post",
                async: false, // 同步执行
                url: "/applicationSecurity/sendmessage",
                data: {
                    "content": $("#message_content").val(),
                    "channelid": $("#show_channel").val(),
                    "userid": ${user.userid}
                },
                success: function (result) {
                    $("#add_admin_status").text(result);

                },
                error: function () {
                }
            })
        $("#message_content").val("")
        }
    }

    var leng=0;
    setInterval(function () {
        if ($("#show_channel").val()!=''){
            $.ajax({
                type: "post",
                async: true, // 异步执行
                url: "/applicationSecurity/showchannel",
                data: {
                    "channelid": $("#show_channel").val()
                },
                success: function (result) {
                    var data = JSON.parse(result);
                    if (data.length!=leng){
                        leng = data.length;
                        $("#channel_content").text("");
                        for (var ele in data){
                            $("#channel_content").append("" +
                                "            <div class='panel panel-default'>\n" +
                                "                            <div class='panel-heading'>\n" +
                                "\n" +
                                "                            <h3 class='panel-title'>\n" +
                                "                            "+data[ele]["nickname"]+"\n" +
                                "                        </h3>\n" +
                                "\n" +
                                "                            </div>\n" +
                                "                            <div class='panel-body' style='overflow: hidden'>\n" +
                                "                            <p>"+ data[ele]["senddate"] +"</p>\n" +
                                "                        <p>"+data[ele]["content"]+"</p>\n" +
                                "                        </div>\n" +
                                "                        </div>"
                            )

                        }
                    }
                },
                error: function () {
                }
            })
        }

    },2000)


    setInterval(function () {
        $.ajax({
            type: "post",
            async: true, // 异步执行
            url: "/applicationSecurity/shownotificationnumber",
            data: {
                "userid": ${user.userid},
                "workspaceid": ${workspace.workspaceid}
            },
            success: function (result) {
                if(result==0){
                    $("#notification_number").text("");
                }else {
                    $("#notification_number").text(result);
                }

            },
            error: function () {
            }
        })
    },2000)


</script>



</body>
</html>