<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!doctype html>
<html>
<head>
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        function publishMessage() {
            var name = $("#OutId").val();
            var goEasy = new GoEasy({
                host: 'hangzhou.goeasy.io',
                appkey: 'BC-12a269729da540b9aae5c52b7a206f55',
            });
            goEasy.publish({
                channel: "cmfz", //替换为您自己的channel
                message: name//替换为您想要发送的消息内容
            });
            var span = "<span style='color: blue'>" + name + "</span>";
            $("#d1").html(span);
        }

        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io',
            appkey: 'BC-12a269729da540b9aae5c52b7a206f55'
        });
        goEasy.subscribe({
            channel: "cmfz",//替换为您自己的channel
            onMessage: function (message) {
                var span = "<span style='color: red'>" + message.content + "</span>";
                $("#d2").html(span);
                //console.log("Channel:" + message.channel + " content:" + message.content);
            }
        });
    </script>
</head>
<body>
<center>
    <%--<textarea cols="105" rows="25" name="show_textarea" id="textareaId" readonly></textarea>--%>
    <div style="border: 5px black solid" id="textareaId">
        <div id="d1" class="navbar-right"></div>
        <br>
        <div id="d2" class="navbar-left"></div>
    </div>
    <br>
    <!--demos-conts-->
    <div class="demos_con">
        <div class="demos_condsend clearfix">
            <input id="OutId" type="text"/>
            <button class="demos_condsend_2 fr" onclick="publishMessage();">Publish</button>
        </div>
    </div>
</center>
</body>
</html>
