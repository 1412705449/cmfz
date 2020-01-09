<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript">
        function login() {
            $.ajax({
                url: "${app}/AdminController/login",
                type: "POST",
                datatype: "JSON",
                data: $("#loginForm").serialize(),
                success: function (mis) {
                    if (mis != null & mis != "") {
                        alert(mis)
                        // $("#msg").html("<p style='color: red'>"+mis+"</p >")
                    } else {
                        location.href = "${app}/jsp/main.jsp";
                    }
                }
            })
        }
    </script>
</head>
<body style=" background: url(${app}/img/login1.jpg); background-size: 100%;">
<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="${app}/UserController/login">
            <div class="modal-body" id="model-body">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户名" autocomplete="off" name="username">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
                </div>
                <div class="form-group">
                    <div class="col-sm-5">
                        <input type="text" placeholder="验证码" name="code" id="code" class="form-control">
                    </div>
                    <div class="col-sm-4">
                        <img id="num" src="${app}/AdminController/code" alt="" style="width: 100%"
                             onclick="document.getElementById('num').src = '${app}/AdminController/code?'+(new Date()).getTime()">
                    </div>
                </div>
                <span id="msg"></span>
            </div>
            <div class="modal-footer">
                <div class="form-group">
                    <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-default form-control">注册</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
