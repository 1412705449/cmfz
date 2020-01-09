<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${app}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${app}/boot/css/back.css">
    <link rel="stylesheet" href="${app}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${app}/jqgrid/css/jquery-ui.css">
    <script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${app}/boot/js/bootstrap.min.js"></script>
    <script src="${app}/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${app}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${app}/boot/js/ajaxfileupload.js"></script>
    <script src="../kindeditor-4.1.11-zh-CN/kindeditor/kindeditor-all-min.js"></script>
    <script src="../kindeditor-4.1.11-zh-CN/kindeditor/lang/zh-CN.js"></script>
    <script src="${app}/echarts/echarts.min.js"></script>
    <!-- 将https协议改为http协议 -->
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        KindEditor.ready(function (K) {
            window.editor = K.create('#content', {
                uploadJson: '${pageContext.request.contextPath}/article/uploadImg',
                allowFileManager: true,
                fileManagerJson: '${pageContext.request.contextPath}/article/showAllImg',
                afterBlur: function () {
                    // 同步数据方法
                    this.sync();
                }
            });
        });
    </script>
</head>
<body>
<%--导航栏--%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">吹牛逼大会</a>
        </div>
        <div>
            <!--向右对齐-->
            <ul class="nav navbar-nav navbar-right">
                <li><a>来了!! <font style="color:red;">${sessionScope.admin.username}</font>老弟</a></li>
                <li><a href="${pageContext.request.contextPath}/AdminController/outLogin">退出登陆</a></li>
            </ul>
        </div>
    </div>
</nav>
<%--栅格--%>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-2">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                用户模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#jumbotronId').load('${app}/jsp/user.jsp')">用户管理</a></li>
                                <li><a href="javascript:$('#jumbotronId').load('${app}/jsp/echarts.jsp')">用户活跃度分析</a>
                                </li>
                                <li><a href="javascript:$('#jumbotronId').load('${app}/jsp/map.jsp')">用户地区分布</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                专辑模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#jumbotronId').load('${app}/jsp/album.jsp')">专辑管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                文章模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#jumbotronId').load('${app}/jsp/article.jsp')">文章管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                上师模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#jumbotronId').load('${app}/jsp/Guru.jsp')">上师管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                轮播图模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#jumbotronId').load('${app}/jsp/banner.jsp')">轮播图管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-10" id="jumbotronId">
            <div class="jumbotron">
                <div class="container">
                    <h1 style="text-align: center">欢迎加入！</h1>
                    <div id="myCarousel" class="carousel slide">
                        <!-- 轮播（Carousel）指标 -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                        </ol>
                        <!-- 轮播（Carousel）项目 -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <img src="${app}/img/11.jpg" alt="First slide">
                                <div class="carousel-caption">张卫</div>
                            </div>
                            <div class="item">
                                <img src="${app}/img/12.jpg" alt="Second slide">
                                <div class="carousel-caption">吴淑克</div>
                            </div>
                            <div class="item">
                                <img src="${app}/img/13.jpg" alt="Third slide">
                                <div class="carousel-caption">牛三</div>
                            </div>
                        </div>
                        <!-- 轮播（Carousel）导航 -->
                        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hid*8
                                  den="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>