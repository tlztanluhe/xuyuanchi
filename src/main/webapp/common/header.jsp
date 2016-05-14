<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="loginUser" value="${sessionScope.login_user }" scope="session"/>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="${ctx }/plugin/frozen/css/frozen.css" />
<script type="text/javascript">
	var ctx = "${ctx}";
</script>

<script src="${ctx }/js/jquery-1.10.1.js"></script>
<script type="text/javascript">
	jQuery.noConflict();
</script>
<script src="${ctx }/plugin/frozen/lib/zepto.min.js"></script>
<script src="${ctx }/plugin/frozen/js/frozen.js"></script>
<script src="${ctx }/js/config.js"></script>
