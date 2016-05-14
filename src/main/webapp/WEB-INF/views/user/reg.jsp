<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
		<head>
			<jsp:include page="/common/header.jsp" />
			<title>
				用户登录
			</title>
		</head>
		<body>
			<header class="ui-header ui-header-positive ui-border-b">
				<i class="ui-icon-return" onclick="history.back()">
				</i>
				<h1>
					新用户注册
				</h1>
				<button class="ui-btn">
					
				</button>
			</header>
			<section class="ui-container ui-center">
				<div class="demo-block">
					<div class="ui-form ui-border-t">
						<form action="#" id="reg_form">
							<div class="ui-form-item ui-form-item-show  ui-border-b">
								<label for="#">
									用户名
								</label>
								<input name="name" style="height:100%;" type="text">
							</div>
							<div class="ui-form-item ui-form-item-show ui-border-b">
								<label for="#">
									密码
								</label>
								<input name="password" style="height:100%;" type="text">
							</div>
							<div class="ui-form-item ui-form-item-show ui-border-b">
								<label for="#">
									手机号
								</label>
								<input name="phone" style="height:100%;" type="text">
							</div>
							<div class="ui-form-item ui-form-item-show ui-border-b">
								<label for="#">
									邮箱
								</label>
								<input name="email" style="height:100%;" type="text">
							</div>
							<div class="ui-form-item">
								<a id="reg_button" class="ui-btn-lg ui-btn-primary">
									注册
								</a>
							</div>
						</form>
					</div>
				</div>
			</section>
		</body>
		<jsp:include page="/common/footer.jsp" />
	
	</html>
	<script type="text/javascript">
		//jQuery.getScript(config.frozen.dialog);	
		var regUrl = ctx + "/user/reg.action";
		var loginUrl = ctx + "/user/toLogin.action";
		jQuery(function() {
			jQuery("#reg_button").click(function() {
				jQuery.ajax({
					url: regUrl,
					type: "post",
					data: jQuery("#reg_form").serialize(),
					dataType: "json",
					success: function(msg) {
						if (msg && msg.code == config.status.success) {
							var dia = $.dialog({
								title: '温馨提示',
								content: msg.content,
								button: ["确认"]
							});
							dia.on("dialog:action",function(e){
						        location.href = loginUrl;
						    });
						} else {
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