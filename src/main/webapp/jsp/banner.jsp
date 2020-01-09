<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript">
        $(function () {
            $("#bannerTable").jqGrid(
                {
                    url: "${pageContext.request.contextPath}/banner/selectByPage",
                    datatype: "json",
                    colNames: ['id', 'title', 'url', 'href', 'createDate', 'descs', 'status', 'filename'],
                    colModel: [
                        {name: 'id', index: 'id', align: "center", editoptions: {readonly: true}},
                        {name: 'title', align: "center", editable: true},
                        {
                            name: 'url',
                            formatter: function (cellvalue, options, rowObject) {
                                return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                            },
                            align: "center",
                            editable: true,
                            edittype: "file",
                            editoptions: {enctype: "multipart/form-data"}
                        },
                        {name: 'href', align: "center", editable: true},
                        {name: "createDate"},
                        {name: 'descs', align: "center", editable: true},
                        {
                            name: 'status',
                            formatter: function (value, options, row) {
                                if (value == "1") {
                                    return "显示";
                                } else if (value == "0") {
                                    return "隐藏";
                                }
                            },
                            sortable: false,
                            align: "center",
                            editable: true,
                            edittype: "select",
                            editoptions: {value: "1:显示;0:隐藏"}
                        },
                        {name: 'filename', hidden: true, editable: true, align: "center"},
                    ],
                    rowNum: 4,
                    rowList: [2, 4, 6],
                    pager: '#bannerPage',
                    autowidth: true,
                    multiselect: true,
                    sortname: 'id',
                    mtype: "post",
                    viewrecords: true,
                    sortorder: "desc",
                    caption: "吹牛逼",
                    editurl: "${pageContext.request.contextPath}/banner/banner",
                });
            $("#bannerTable").jqGrid('navGrid', '#bannerPage', {edit: true, add: true, del: true},
                {
                    closeAfterEdit: true,
                    afterSubmit: function (response, postData) {
                        var bannerId = response.responseJSON.bannerId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url: "${pageContext.request.contextPath}/banner/uploadBanner",
                            type: "post",
                            datatype: "json",
                            data: {bannerId: bannerId},
                            fileElementId: "url",//设置传输文件的名字，相当于from中的name
                            success: function () {
                                //后台执行成功，则刷新当前表格
                                $("#bannerTable").trigger("reloadGrid")
                            }
                        });
                        //防止页面报错
                        return postData;
                    }
                }, {
                    closeAfterAdd: true,
                    afterSubmit: function (response, postData) {
                        var bannerId = response.responseJSON.bannerId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url: "${pageContext.request.contextPath}/banner/uploadBanner",
                            type: "post",
                            datatype: "json",
                            data: {bannerId: bannerId},
                            fileElementId: "url",//设置传输文件的名字，相当于from中的name
                            success: function () {
                                //后台执行成功，则刷新当前表格
                                $("#bannerTable").trigger("reloadGrid")
                            }
                        });
                        //防止页面报错
                        return postData;
                    }
                }, {
                    closeAfterDel: true,
                });
        });

        /*轮播图导出*/
        function EasyExport() {
            $.ajax({
                url: "${pageContext.request.contextPath}/banner/easyExcel",
                type: "post",
                datatype: "json",
                success: function () {
                    $("#bannerTable").trigger("reloadGrid");
                }
            });
        }

        /*轮播图导入*/
        function EasyImport() {
            $.ajax({
                url: "${pageContext.request.contextPath}/banner/easyImport",
                type: "post",
                datatype: "json",
                success: function () {
                    $("#bannerTable").trigger("reloadGrid");
                }
            });
        }
    </script>
</head>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a onclick="EasyExport()">轮播图导出</a></li>
    <li><a onclick="EasyImport()">轮播图导入</a></li>
</ul>
<div class="panel">
    <table id="bannerTable"></table>
    <div id="bannerPage" style="height:50px"></div>
</div>
</body>
</html>
