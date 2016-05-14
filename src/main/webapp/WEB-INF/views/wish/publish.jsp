<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/common/header.jsp" />
<title>许下我的愿望</title>
</head>
 <body>
        <header class="ui-header ui-header-positive ui-border-b">
            <i class="ui-icon-return" onclick="history.back()"></i><h1></h1>

			<a class="ui-btn" style="color: #00aeef">
    
  	发布愿望
</a>
        </header>
       
        <section class="ui-container ui-center">
        <div class="demo-block">
            <div class="ui-form ui-border-t">
                <form action="#" id="login_form" >
                	愿望描述<br>
                        <textarea name="name" style="height:200px;width:100%;border:1;"  ></textarea>
                    <a class="ui-btn annex">添加附件</a>
                    <span class="annex_area">
                        <div class="ui-form-item ui-form-item-show ui-border-b">
                            <label for="#">上传附件</label>
                            <input name="password" style="height:100%;" type="file">
                        </div>
                    </span>

                </form>

            </div>
        </div>

       </section>
    </body>
<jsp:include page="/common/footer.jsp"/>
</html>
<div style="display: none" class="htmlTemplate">

    <div class="annex_template">

        <div class="ui-form-item ui-form-item-show ui-border-b">
            <label for="#">上传附件</label>
            <input name="password" style="height:100%;" type="file">
        </div>
    </div>
</div>
<script type="text/javascript">

    $(".annex").click(function(){
        var annex_template_html = $("");

    });
</script>