<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TM-Monitor</title>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>
<body class="">
		<div class="">
			<!-- 상단 메뉴 영역 -->
			<script type="text/javascript" src="js/top.js"></script>
			<!--// 상단 메뉴 영역 -->

		</div>
		<div style="border:3px solid">
			<iframe src="http://10.1.7.85:8080/tmagent/mornitoring/list.do?tc_iframe=Y" frameborder="0" width="100%" height="750"></iframe>	
		</div>
	<div class="footer_wrap bg_color" style="height:100%">
		<img src="images/footer_text.png">
	</div>
</body>
</html>