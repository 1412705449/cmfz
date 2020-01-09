<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript">
        $(function () {
            $("#userTable").jqGrid(
                {
                    url: "${pageContext.request.contextPath}/user/selectByPage",
                    datatype: "json",
                    colNames: ['id', 'phone', 'password', 'salt', 'status', 'photo', 'name', 'nick_name', 'sex', 'sign', 'location', 'rigest_date', 'last_login', 'filename', 'option'],
                    colModel: [
                        {name: 'id', index: 'id', align: "center", editoptions: {readonly: true}},
                        {name: "phone", align: "center", editable: true},
                        {name: 'password', align: "center", editable: true},
                        {name: 'salt', align: "center", editable: true},
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
                        {name: 'name', align: "center", editable: true},
                        {name: 'nick_name', align: "center", editable: true},
                        {name: 'sex', align: "center", editable: true},
                        {name: 'sign', align: "center", editable: true},
                        {name: 'location', align: "center", editable: true},
                        {name: 'rigest_date', align: "center", editable: true},
                        {name: 'last_login', align: "center", editable: true},
                        {name: 'filename', hidden: true, editable: true, align: "center"},
                        {
                            name: 'option', align: "center",
                            formatter: function (cellvalue, options, rowObject) {
                                var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('" + rowObject.id + "')\">修改</button>&nbsp;&nbsp;";
                                return button;
                            }
                        }
                    ],
                    rowNum: 4,
                    rowList: [2, 4, 6],
                    pager: '#userPage',
                    autowidth: true,
                    multiselect: false,
                    sortname: 'id',
                    mtype: "post",
                    viewrecords: true,
                    sortorder: "desc",
                    caption: "吹牛逼"
                });
            $("#userTable").jqGrid('navGrid', '#userPage', {edit: false, add: false, del: false});
        });

        function update(id) {
            $.ajax({
                url: "${pageContext.request.contextPath}/user/updateStates",
                type: "post",
                datatype: "json",
                data: {id: id},
                success: function () {
                    $("#userTable").trigger("reloadGrid");
                }
            });
        }
    </script>
</head>
<div class="page-header">
    <h4>用户管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
</ul>
<div class="panel">
    <table id="userTable"></table>
    <div id="userPage" style="height:50px"></div>
</div>
</body>
</html>
