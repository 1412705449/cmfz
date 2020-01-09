<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<html>
<head>
    <script type="text/javascript">
        $(function () {
            $("#ftable").jqGrid(
                {
                    url: "${pageContext.request.contextPath}/album/selectByPage",
                    datatype: "json",
                    colNames: ['id', '专辑名', '分数', '上师', '播音', '数量', '介绍', '封面', '创建时间', 'filename'],
                    colModel: [
                        {name: 'id', index: 'id', align: "center", editoptions: {readonly: true}},
                        {name: 'title', align: "center", editable: true},
                        {name: 'score', align: "center", editable: true},
                        {name: 'author', align: "center", editable: true},
                        {name: "broadcast", align: "center", editable: true},
                        {name: 'counts', align: "center"},
                        {name: 'descs', align: "center", editable: true},
                        {
                            name: 'status',
                            formatter: function (cellvalue, options, rowObject) {
                                return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                            },
                            align: "center",
                            editable: true,
                            edittype: "file",
                            editoptions: {enctype: "multipart/form-data"}
                        },
                        {name: "createDate", edittype: "date", width: 90, editable: true, sorttype: "date"},
                        {name: 'filename', hidden: true, editable: true, align: "center"},
                    ],
                    rowNum: 2,
                    rowList: [2, 4, 6],
                    pager: '#fpage',
                    sortname: 'id',
                    autowidth: true,
                    multiselect: true,
                    viewrecords: true,
                    sortorder: "desc",
                    height: '50%',
                    subGrid: true,
                    caption: "吹牛逼",
                    editurl: "${pageContext.request.contextPath}/album/album",
                    subGridOptions: {
                        "plusicon": "fa fa-plus",
                        "minusicon": "fa fa-minus",
                        "openicon": "fa fa-share",
                        "reloadOnExpand": false,
                        "selectOnExpand": true
                    },
                    // subgrid_id:父级行的Id  row_id:当前的数据Id
                    subGridRowExpanded: function (subgrid_id, row_id) {
                        // 调用生产子表格的方法
                        // 生成表格 | 生产子表格工具栏
                        addSubgrid(subgrid_id, row_id);
                    },
                    // 删除表格的方法
                    subGridRowColapsed: function (subgrid_id, row_id) {
                    }
                });
            $("#ftable").jqGrid('navGrid', '#fpage', {
                edit: true,
                add: true,
                del: true,
                edittext: "编辑",
                addtext: "添加",
                deltext: "删除"
            }, {
                closeAfterEdit: true,
                afterSubmit: function (response, postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url: "${pageContext.request.contextPath}/album/uploadAlbum",
                        type: "post",
                        datatype: "json",
                        data: {albumId: albumId},
                        fileElementId: "status",//设置传输文件的名字，相当于from中的name
                        success: function () {
                            //后台执行成功，则刷新当前表格
                            $("#ftable").trigger("reloadGrid")
                        }
                    });
                    //防止页面报错
                    return postData;
                }
            }, {
                closeAfterAdd: true,
                afterSubmit: function (response, postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url: "${pageContext.request.contextPath}/album/uploadAlbum",
                        type: "post",
                        datatype: "json",
                        data: {albumId: albumId},
                        fileElementId: "status",//设置传输文件的名字，相当于from中的name
                        success: function () {
                            //后台执行成功，则刷新当前表格
                            $("#ftable").trigger("reloadGrid")
                        }
                    });
                    //防止页面报错
                    return postData;
                }
            }, {
                closeAfterDel: true,
            });
        });

        // subgrid_id 父行级id
        function addSubgrid(subgrid_id, row_id) {
            // 声明子表格Id
            var sid = subgrid_id + "table";
            // 声明子表格工具栏id
            var spage = subgrid_id + "page";
            $("#" + subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='" + spage + "' style='height: 50px'></div>")
            $("#" + sid).jqGrid(
                {
                    // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                    url: "${pageContext.request.contextPath}/chapter/selectByPage?albumId=" + row_id,
                    datatype: "json",
                    colNames: ['ID', '章节名称', '章节大小', '播放时长', '在线试听', '创建时间', 'filename'],
                    colModel: [
                        {name: "id", align: 'center'},
                        {name: "title", align: 'center', editable: true},
                        {name: "sizes", align: 'center'},
                        {name: "time", align: 'center'},
                        {
                            name: "url",
                            align: 'center',
                            width: 300,
                            editable: true,
                            edittype: 'file',
                            formatter: function (value, option, rows) {
                                return "<audio controls loop preload='auto'>\n" +
                                    "<source src='" + value + "' type='audio/mpeg'>\n" +
                                    "<source src='" + value + "' type='audio/ogg'>\n" +
                                    "</audio>";
                            }
                        },
                        {name: "createTime", align: 'center'},
                        {name: 'filename', hidden: true, editable: true, align: "center"},
                    ],
                    rowNum: 2,
                    rowList: [2, 4, 6],
                    pager: spage,
                    viewrecords: true,
                    mtype: "post",
                    sortname: 'num',
                    sortorder: "asc",
                    height: '100%',
                    multiselect: true,
                    autowidth: true,
                    editurl: "${pageContext.request.contextPath}/chapter/chapter?albumId=" + row_id,

                });
            $("#" + sid).jqGrid('navGrid',
                "#" + spage, {
                    edit: true,
                    add: true,
                    del: true,
                    edittext: "修改",
                    addtext: "添加",
                    deltext: "删除"
                }, {
                    //控制修改操作
                    closeAfterEdit: true,
                    afterSubmit: function (response, postData) {
                        //alert("上传");
                        var chapterId = response.responseJSON.chapterId;
                        console.log(chapterId);
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/uploadChapter",//用于文件上传的服务器端请求地址
                            fileElementId: 'url',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                            // dataType : 'json',
                            data: {chapterId: chapterId},
                            success: function () {
                                $("#" + sid).trigger("reloadGrid");
                            }
                        });
                        return postData;
                    }
                }, {
                    //控制添加操作
                    closeAfterAdd: true,
                    afterSubmit: function (response, postData) {
                        //alert("上传");
                        var chapterId = response.responseJSON.chapterId;
                        console.log(chapterId);
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/uploadChapter",//用于文件上传的服务器端请求地址
                            fileElementId: 'url',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                            // dataType : 'json',
                            data: {chapterId: chapterId},
                            success: function () {
                                $("#" + sid).trigger("reloadGrid");
                            }
                        });
                        return postData;
                    }
                }, {
                    closeAfterDel: true,
                });
        };
    </script>
</head>
<div class="page-header">
    <h4>专辑管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>专辑信息</a></li>
</ul>
<div class="panel">
    <table id="ftable"></table>
    <div id="fpage" style="height:50px"></div>
</div>
</body>
</html>
