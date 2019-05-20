<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" style="padding-left:50px;margin-left:0px;margin-top: 80px">
    <div class="row">
        <div class="col-sm-6 ">
            <div class="well well-lg" style="height: 450px;overflow: auto">

                <c:forEach items="${workspaceInvitations}" var="w">
                    <div class="panel panel-default">
                        <div class="panel-heading">

                            <h3 class="panel-title" style="display: inline">
                                workspace invitation
                            </h3>
                            <form action="/applicationSecurity/updatejoinworkspace" style="display: inline;float: right" method="post">
                                <input type="hidden" name="joinwsid" value=${w.joinwsid}>
                                <button type="submit" class="btn btn-primary" name="agree_action" value="agree">agree</button>

                                <button type="submit" class="btn btn-danger" name="reject_action" value="reject">reject</button>
                            </form>

                        </div>
                        <div class="panel-body" style="overflow: hidden">
                            <p>invitor: ${w.invitorname}</p>
                            <p>workspace: ${w.workspacename}</p>
                            <p>description:${w.description}</p>
                        </div>
                    </div>
                </c:forEach>


            </div>
            <form action="/applicationSecurity/personalPage">
                <button type="submit" class="btn btn-success">return to homepage</button>
            </form>
        </div>
        <div class="col-sm-6 ">
            <div class="well well-lg" style="height: 450px;overflow: auto">
                <c:forEach items="${channelInvitations}" var="c">
                    <div class="panel panel-default">
                        <div class="panel-heading">

                            <h3 class="panel-title" style="display: inline">
                                channel invitation
                            </h3>
                            <form action="/applicationSecurity/updatejoinchannel" style="display: inline;float: right" method="post">
                                <input type="hidden" name="joinchannelid" value=${c.joinchannelid}>
                                <button type="submit" class="btn btn-primary" name="agree_action" value="agree">agree</button>

                                <button type="submit" class="btn btn-danger" name="reject_action" value="reject">reject</button>
                            </form>

                        </div>
                        <div class="panel-body" style="overflow: hidden">
                            <p>invitor: ${c.invitorname}</p>
                            <p>channel: ${c.name}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
    </div>
</div>
</body>
</html>
