<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>许愿池</title>
   <jsp:include page="/common/header.jsp" />
</head>
 <body ontouchstart="">
<header class="ui-header ui-header-positive ui-border-b">
    <h1>许愿池</h1>
    <button class="ui-btn">
    
  	<c:if test="${loginUser != null }">
  		${loginUser.userName }
  	</c:if>
  	<c:if test="${loginUser == null }">
  		请登
  	</c:if>
    </button>
</header>
<div class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
    <button class="ui-btn-lg" id="woyaoxuyuan_id">
      我要许愿
    </button>
    <button class="ui-btn-lg" id="">
    XXXX
    </button>
    
</div>
<section class="ui-container ">
<div class="ui-whitespace">
    <p class="ui-txt-justify"><a>nexus</a>:<a>我想吃顿大餐。。。</a></p>
	<p class="ui-txt-justify"><a>nexus</a>:<a>我想吃顿大餐。。。</a></p>
	<p class="ui-txt-justify"><a>nexus</a>:<a>我想吃顿大餐。。。</a></p>
	<p class="ui-txt-justify"><a>nexus</a>:<a>我想吃顿大餐。。。</a></p>
</div>
</section> 
 </body>
<jsp:include page="/common/footer.jsp"></jsp:include>
</html>

<script type="text/javascript">

	var loginUrl = ctx+"/user/toLogin.action";
	var publishWishUrl = ctx + "/wish/publish.action"
	var currentUser = "";
	jQuery(function(){
		
		//用户已登录情况下
		if(currentUser){
			jQuery("#woyaoxuyuan_id").click(function(){
				window.location=publishWishUrl;
			});
		}
		//用户未登录
		if(!currentUser){
			jQuery("#woyaoxuyuan_id").click(function(){
				window.location=loginUrl;
			});
		}
		
	});
</script>