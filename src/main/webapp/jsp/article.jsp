<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="../kindeditor-4.1.11-zh-CN/kindeditor/kindeditor-all-min.js"></script>
    <script src="../kindeditor-4.1.11-zh-CN/kindeditor/lang/zh-CN.js"></script>
    <script src="../kindeditor-4.1.11-zh-CN/kindeditor/kindeditor-all.js"></script>
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
        $(function () {
                $("#articleTable").jqGrid(
                    {
                        url: "${pageContext.request.contextPath}/article/selectByPage",
                        datatype: "json",
                        colNames: ['id', 'title', 'img', 'content', 'createDate', 'publishdate', 'status', 'filename', 'guruId'],
                        colModel: [
                            {name: 'id', index: 'id', align: "center"},
                            {name: 'title', align: "center"},
                            {
                                name: 'imgfile', formatter: function (cellvalue, options, rowObject) {
                                    return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                                },
                                align: "center",
                                editable: true
                            },
                            {name: 'content', align: "center"},
                            {name: "createDate", align: "center"},
                            {name: 'publishDate', align: "center"},
                            {name: 'status', align: "center"},
                            {name: 'filename', hidden: true, align: "center"},
                            {name: 'guruId', hidden: true, align: "center"},
                        ],
                        rowNum: 4,
                        rowList: [2, 4, 6],
                        pager: '#articlePage',
                        autowidth: true,
                        multiselect: true,
                        sortname: 'id',
                        mtype: "post",
                        viewrecords: true,
                        sortorder: "desc",
                        caption: "吹牛逼",
                        editurl: "${pageContext.request.contextPath}/article/delete",
                    });
                $("#articleTable").jqGrid('navGrid', '#articlePage', {edit: false, add: false, del: true});
            },
            {
                closeAfterDel: true,
            }
        );
        //展示文章信息
        $("#btnUpdate").click(function () {
            //只读属性，最后选择行的id
            var rowId = $("#articleTable").jqGrid("getGridParam", "selrow");
            //判断是否选中一行
            if (rowId != null) {
                //根据行id获取整个行的数据
                var row = $("#articleTable").jqGrid("getRowData", rowId);

                //给input框设添加数据
                $("#id").val(row.id);
                $("#filename").val(row.filename);
                $("#createDate").val(row.createDate);
                $("#publishDate").val(row.publishDate);
                //$("#imgfile").val(row.imgfile);
                $("#title").val(row.title);
                // 更替KindEditor 中的数据使用KindEditor.html("#editor_id",data.content) 做数据替换
                KindEditor.html("#content", row.content)
                // 处理状态信息
                $("#status").val(row.status);
                var option = "";
                if (row.status == "1") {
                    option += "<option selected value='1'>展示</option>";
                    option += "<option value='2'>冻结</option>";
                } else {
                    option += "<option value='1'>展示</option>";
                    option += "<option selected value='2'>冻结</option>";
                }
                $("#status").html(option);
                /*处理上师信息*/
                $.ajax({
                    url: "${pageContext.request.contextPath}/guru/selectAll",
                    type: "post",
                    datatype: "json",
                    success: function (data) {
                        var option = "<option value='0'>请选择所属上师</option>";
                        data.forEach(function (guru) {
                            if (guru.id == row.guruId) {
                                option += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                            } else {
                                option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                            }
                        });
                        $("#guruId").html(option);
                        $("#myModal").modal("show");
                    }
                });
            } else {
                alert("请选择一行");
            }
        });


        $("#btnId").click(function () {
            $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/article/article",
                type: "post",
                datatype: "JSON",
                data: {
                    "id": $("#id").val(),
                    "title": $("#title").val(),
                    "content": $("#content").val(),
                    "status": $("#status").val(),
                    "guruId": $("#guruId").val(),
                },
                fileElementId: "fileImg",
                success: function () {
                    $("#closeId").click();
                    $("#articleTable").trigger("reloadGrid");
                }
            })
        });

        $("#btnAdd").click(function () {
            $("#imgForm")[0].reset();
            $("#id").val("");
            /*状态*/
            var option = "";
            option += "<option value='1'>展示</option>";
            option += "<option value='2'>冻结</option>";
            $("#status").html(option);
            /*查询所有上师*/
            $.ajax({
                url: "${pageContext.request.contextPath}/guru/selectAll",
                type: "post",
                datatype: "json",
                success: function (data) {
                    var option = "<option value='0'>请选择所属上师</option>";
                    data.forEach(function (guru) {
                        option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                    });
                    $("#guruId").html(option);
                }
            });

            KindEditor.html("#content", "");
            $("#myModal").modal("show");
        });
    </script>
</head>
<div class="page-header">
    <h4>文章管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>所有文章</a></li>
    <li>
        <button class="btn btn btn-default" id="btnAdd" data-toggle="modal">
            添加文章
        </button>
    </li>
    <li>
        <button class="btn btn btn-default" id="btnUpdate" data-toggle="modal">
            修改文章
        </button>
    </li>
</ul>
<div class="panel">
    <table id="articleTable"></table>
    <div id="articlePage" style="height:50px"></div>
</div>

<!-- 添加修改文章模态框（Modal） -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 730px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    添加文章信息
                </h4>
            </div>
            <%--身体--%>
            <form role="form" id="imgForm" class="form-horizontal">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <div class="form-group">
                                <label for="title">标题</label>
                                <input type="text" class="form-control" name="title" id="title" placeholder="请输入名称">
                            </div>
                            <div class="form-group">
                                <label for="fileImg">封面</label>
                                <!-- name不能起名和实体类一致 会出现使用String类型接受二进制文件的情况 -->
                                <input type="file" id="fileImg" name="fileImg">
                            </div>
                            <div class="form-group">
                                <label for="content">内容</label>
                                <textarea id="content" name="content" style="width:700px;height:300px;"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="status">状态</label>
                                <select class="form-control" id="status" name="status">
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="guruId">上师列表</label>
                                <select class="form-control" id="guruId" name="guruId">
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="hidden" neme="id" class="form-control" id="id">
                            </div>
                            <div class="form-group">
                                <input type="hidden" name="imgfile" class="form-control" id="imgfile">
                            </div>
                            <div class="form-group">
                                <input type="hidden" name="filename" class="form-control" id="filename">
                            </div>
                            <div class="form-group">
                                <input type="hidden" name="publishDate" class="form-control" id="publishDate">
                            </div>
                            <div class="form-group">
                                <input type="hidden" name="createDate" class="form-control" id="createDate">
                            </div>
                        </div>
                    </div>
                </div>
                <%--脚--%>
                <div class="modal-footer">
                    <button id="closeId" type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" id="btnId" class="btn btn-primary">
                        提交
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>
