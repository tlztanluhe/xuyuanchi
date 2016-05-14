<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/common/header.jsp" />
<title>用户登录</title>
</head>
 <body>
        <header class="ui-header ui-header-positive ui-border-b">
            <i class="ui-icon-return" onclick="history.back()"></i><h1>用户登录</h1><button class="ui-btn">回首页</button>
        </header>
       
        <section class="ui-container ui-center">
        <div class="demo-block">
            <div class="ui-form ui-border-t">
                <form action="#" id="login_form" >
                    <div class="ui-form-item ui-form-item-show  ui-border-b">
                        <label for="#">用户名</label>
                        <input name="name" style="height:100%;" type="text" >
                    </div>
                    <div class="ui-form-item ui-form-item-show ui-border-b">
                        <label for="#">密码</label>
                        <input name="password" style="height:100%;" type="text">
                    </div>
                    <div class="ui-form-item">
                        <a id="login_button" class="ui-btn-lg ui-btn-primary">
                    登录
                </a>
                    </div>
                   
                </form>
                <br>
                <label class="ui-label"><a id="reg_id">注册</a></label>
                <label class="ui-label"><a id="forget_pass">忘记密码？</a></label>
            </div>
        </div>

       </section>
    </body>
<jsp:include page="/common/footer.jsp"/>
</html>

<script type="text/javascript">

	var regUrl = ctx+"/user/toReg.action";
	var loginUrl = ctx + "/user/login.action";
	
	jQuery(function(){
		
		//注册
		jQuery("#reg_id").click(function(){
			location.href = regUrl;
		});
		//忘记密码
		jQuery("#forget_pass").click(function(){
			location.href = "";
		});
		//登录按钮
		jQuery("#login_button").click(function(){
			var formData = jQuery("#login_form").serialize();
			jQuery.ajax({
				type:"post",
				url:loginUrl,
				data:formData,
				dataType:"json",
				success:function(msg){
					if(msg && msg.code == config.status.success){
						location.href = ctx;
					}else{
						var content = msg.content || "系统错误，注册失败";
						var dia = $.dialog({
							title: '温馨提示',
							content: content,
							button: ["确认"]
						});
					}
				}
			});
		});
	});
</script>