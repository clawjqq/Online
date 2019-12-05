<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <!--表示使用IE最新的渲染模式进行解析-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--兼容一些移动设备，会根据屏幕的大小缩放
    	width=device-width  表示宽度是设备的宽度（很多手机的宽度都是980px）
    	initial-scale=1  初始化缩放级别   1-5
    	minimum-scale=1  maximum-scale=5
    	user-scalable=no  禁止缩放   -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>添加或修改视频</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>

    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>

    <style type="text/css">
        th{
            text-align: center;
        }
    </style>
    <script type="text/javascript">
        // js函数         这个 id 随便写， 这是你自己定以的变量   展示出来下拉框的内容
        function selectType(obj,tId) {
            var typeName = obj.innerHTML;
            document.getElementById("typeName").value = typeName;
            document.getElementById("typeId").value = tId;
        }
    </script>

</head>
<body>

<nav class="navbar navbar-inverse" style="margin-bottom: 0px;">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">简单增删</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="goodsList.html">商品添加页面<span class="sr-only">(current)</span></a></li>
                <li><a href="${pageContext.request.contextPath}/goods/list">商品展示页</a></li>
                <li><a href="${pageContext.request.contextPath}/goods/addGoods">商品添加页</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">用户</a>
                </li>
                <li> <a href="#"><span class="glyphicon glyphicon-log-in" style="padding-left:0px"></span></a> </li>
                <li> <a href="#" style="padding-left:0px">退出</a> </li>
            </ul>
        </div>
    </div>
</nav>

<div class="jumbotron" style="padding-top: 15px;padding-bottom: 15px;">
    <div class="container">
        <%--此处与edit方法中setAttribute定以的名称对应--%>
        <c:if test="${empty gds.id}">
            <h2>添加信息</h2>
        </c:if>
        <c:if test="${not empty gds.id}">
            <h2>修改信息</h2>
        </c:if>
    </div>
</div>
<div class="container" style="margin-top: 20px;">

    <form class="form-horizontal" enctype="multipart/form-data" action="${pageContext.request.contextPath}/goods/save" method="post">
             <%--隐藏框，判断商品是否携带ID（新增还是修改）--%>
        <input type="hidden" name="id" value="${gds.id}" />
        <div class="form-group">
            <label class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="goodsName" value="${gds.goodsName}"  placeholder="起个靓名" >
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">商品价格</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="price" value="${gds.price}"  placeholder="价格">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">商品类别</label>
            <div class="col-sm-10">
                <div class="input-group">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">下拉菜单<span class="caret"></span></button>
                        <ul id="selectSpeaker" class="dropdown-menu">
                            <c:forEach items="${goodsTypeList}" var="goodsType">

                                <%--前面是给JS函数放入id，传给selectType方法--%>
                               <li><a onclick="selectType(this,${goodsType.id})">${goodsType.typename}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                    <input type="hidden" class="form-control" id="typeId" name="typeId" value="${gds.typeId}">
                    <input type="text" class="form-control"  disabled id="typeName" value="${gds.typename}" aria-label="...">
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">商品图片</label>
            <div class="col-sm-10" >
                <input type="file" name="imagePath" ><br/>  <%--此处引入了bootstrap文件选择框--%>
                <c:if test="${empty gds.imagePath}">
                    <image src="${pageContext.request.contextPath}/imag/default.jpg" alt="..." class="img-thumbnail" height="130px" width="130px"></image>
                </c:if>
                <c:if test="${not empty gds.imagePath}">
                    <image src="${gds.imagePath}" alt="..." class="img-thumbnail" height="130px" width="130px"></image>
                </c:if>
           </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">商品描述</label>
            <div class="col-sm-10">
                <textarea class="form-control" name="comment" rows="3">${gds.comment}</textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">保存</button>
            </div>
        </div>

    </form>

</div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

</body>
</html>