<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c" %>
<%@ taglib uri="http://chenliang.com/" prefix="p"%>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>商品列表</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/confirm.js"></script>


<style type="text/css">
    th{
        text-align: center;
    }
</style>


<script type="text/javascript">
    var selectNum = 0; //选中的数量

    $(function () {
        $("#selectAll").click(function () {
            $(":checkbox").prop("checked", this.checked);
            if (this.checked) {
                selectNum = $(":checkbox").length - 1;
            } else {
                selectNum = 0;
            }
            $("#batches").text(selectNum);
        });

        $("#batchRemove").click(function () {
            Confirm.show('溫馨提示', '您确定要刪除这['+selectNum+']条商品吗？', {
                '确定' : {
                    'primary' : true,
                    'callback' : function() {
                        $("#batchbox").submit();
                    }
                }
            });
        })
    });


    function selectOne(obj){
        var result = obj.checked;
        if(result){
            selectNum += 1;
            if (selectNum == ($(":checkbox").length - 1)) {
                $("#selectAll").attr("checked", true);
            }
        }else{
            selectNum -= 1;
            if (selectNum != ($(":checkbox").length - 1)) {
                $("#selectAll").attr("checked", false);
            }
        }
        $("#batches").text(selectNum);
    }


    /*删除时，展示是否要删除，确认画面*/
    function showDelete(name,id){
        alert(name, id);
        Confirm.show('溫馨提示', '您确定要刪除['+name+']商品吗？', {
            '确定' : {
                'primary' : true,
                'callback' : function() {
                    location.href= "${pageContext.request.contextPath}/goods/delete?goodsID="+id;
                }
            }
        });
    }


    function showType(obj, id) {
        var type = $(obj).text();

        /*$("#btn1").text(type); 需要加上小箭头标签*/
        $("#btn1").html(type + "<span class='caret'></span>");
        $("#typeID").val(id);

    }

</script>

</head>
<body>

<nav class="navbar navbar-inverse" style="margin-bottom: 0px;">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">简单增删</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-9">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/goods/list">商品展示页</a></li>
                <li><a href="${pageContext.request.contextPath}/goods/addGoods">商品添加页</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right" style="margin-right: -25px;">
                <li><a href="#">用户</a></li>
                <li><a href="${pageContext.request.contextPath}/showLogin"><span class="glyphicon glyphicon-log-in"></span>&nbsp;&nbsp;&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="jumbotron" style="padding-top: 3px; padding-bottom: 0.2px; margin-bottom: 10px;">
    <div class="navbar container" style="padding-left:15px; ">
        <h2>商品管理</h2>
    </div>
</div>

<div class="container">
     <div class="row">
         <div class="col-md-6">
           <button type="button" class="btn btn-info" onclick="location.href='${pageContext.request.contextPath}/goods/addGoods'">添加</button>
           <button type="button" id="batchRemove" class="btn btn-primary">批量删除<span id="batches" class="badge">0</span></button>
         </div>

         <div class="col-md-6">

             <form action="${pageContext.request.contextPath}/goods/list" method="post" class="form-inline navbar-right" style="margin-right:10px">
                 <div class="form-group">
                     <input type="text" name="goodsName" value="${goodsVo.goodsName}" class="form-control" id="exampleInputEmail3" placeholder="商品名称">
                 </div>
                 <div class="form-group">
                     <input type="date" name="date" value="${goodsVo.date}" class="form-control" id="exampleInputPassword3" placeholder="">
                 </div>

                 <!-- Single button -->
                 <div class="btn-group">
                     <button type="button" id="btn1" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                         商品类型<span class="caret"></span>
                     </button>
                     <ul class="dropdown-menu">
                         <c:forEach items="${typeList}" var="type">
                             <li><a href="#" onclick="showType(this,${type.id})">${type.typename}</a></li>
                         </c:forEach>
                         <input id="typeID" type="hidden" name="typeId" />
                     </ul>
                 </div>
                 <button type="submit" class="btn btn-info">查&nbsp;询</button>
             </form>

         </div>

     </div>
</div>

<form id="batchbox" action="${pageContext.request.contextPath}/goods/batchDelete">
<div class="container">
    <table class="table table-bordered table-hover table-striped" style="text-align:center;table-layout: fixed">
       <thead>
          <tr>
              <th style="width: 5%;"><input type="checkbox" id="selectAll" name="ids" /></th>
              <th style="width: 6%">序号</th>
              <th style="width: 15%">商品名称</th>
              <th>商品价格</th>
              <th>图片</th>
              <th>上架时间</th>
              <th>类型</th>
              <th>商品描述</th>
              <th style="width: 5%">编辑</th>
              <th style="width: 5%">删除</th>
          </tr>
       </thead>
       <tbody>
           <c:forEach items="${page.rows}" var="goodsList" varStatus="var">
               <tr>
                  <td style="padding-top: 20px"><input type="checkbox" name="ids" onclick="selectOne(this)" value="${goodsList.id}"/></td>
                  <td style="padding-top: 20px">${var.count}</td>
                  <td style="padding-top: 20px; white-space: nowrap;
                  overflow: hidden; text-overflow: ellipsis;">${goodsList.goodsName}</td>
                  <td style="padding-top: 20px">${goodsList.price}</td>
                  <td>
                      <c:if test="${empty goodsList.imagePath}">
                          <image src="../img/default.jpg" width="60px" height="60px" />
                      </c:if>
                      <c:if test="${not empty goodsList.imagePath}">
                          <image src="${goodsList.imagePath}" width="60px" height="60px" />
                      </c:if>
                  </td>
                  <td style="padding-top: 20px">${goodsList.uploadTimeStr}</td>
                  <td style="padding-top: 20px">${goodsList.typename}</td>
                  <td style="padding-top: 15px">
                      <button type="button" class="btn btn-primary" data-container="body" data-toggle="popover" data-placement="top" data-content="${goodsList.comment}">
                      商品详情
                      </button>
                  </td>
                                          <%--获取选择的商品id            这个 span 标签是加入的图标--%>
                   <td style="padding-top:20px"><a href="edit?goodsId=${goodsList.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                                                                       <%--此处需要加个引号--%>
                   <td style="padding-top:20px"><a href="#" onclick="showDelete('${goodsList.goodsName}',${goodsList.id})"><span class="glyphicon glyphicon-trash"></span></a></td>

               </tr>
           </c:forEach>
        </tbody>
    </table>


    <div class="navbar-right" style="margin-left: 5px">
        <p:page url="${pageContext.request.contextPath}/goods/list"></p:page>
    </div>

</div>
</form>


<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>
