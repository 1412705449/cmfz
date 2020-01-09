<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript">
        $(function () {
            $("#guruTable").jqGrid(
                {
                    url: "${pageContext.request.contextPath}/guru/selectByPage",
                    datatype: "json",
                    colNames: ['id', 'name', 'photo', 'status', 'nick_name', 'filename'],
                    colModel: [
                        {name: 'id', index: 'id', align: "center", editoptions: {readonly: true}},
                        {name: 'name', align: "center", editable: true},
                        {
                            name: 'photo',
                            formatter: function (cellvalue, options, rowObject) {
                                return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                            },
                            align: "center",
                            editable: true,
                            edittype: "file",
                            editoptions: {enctype: "multipart/form-data"}
                        },
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
                        {name: 'nick_name', align: "center", editable: true},
                        {name: 'filename', hidden: true, editable: true, align: "center"},
                    ],
                    rowNum: 4,
                    rowList: [2, 4, 6],
                    pager: '#guruPage',
                    autowidth: true,
                    multiselect: true,
                    sortname: 'id',
                    mtype: "post",
                    viewrecords: true,
                    sortorder: "desc",
                    caption: "吹牛逼"
                });
            $("#guruTable").jqGrid('navGrid', '#guruPage', {edit: true, add: true, del: true});
        });
    </script>
</head>
<div class="page-header">
    <h4>上师管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>上师信息</a></li>
</ul>
<div class="panel">
    <table id="guruTable"></table>
    <div id=guruPage" style="height:50px"></div>
</div>
</body>
</html>
