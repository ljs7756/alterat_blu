<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>

<body>
	<form id="frm">
		<div class="section_wrap">
			<div class="left_list">
				<img src="/images/left_top.jpg">
			</div>
			<div class="right_wrap">
				<div class="right_top">
					<ul>
						<li><a href="/selectProfile.do"><img src="/images/gnb01_off.png"></a></li>
						<li><a href="/selectWatchfolder.do"><img src="/images/gnb02_off.png"></a></li>
						<li><a href="/selectMonitoring.do?chk_all=Y&chk_status_all=Y&check_yn=Y"><img src="/images/gnb03_on.png"></a></li>
					</ul>
				</div>
			</div>
			<div style="margin-left: 75px; width: 1200px;">
			    <p style="padding: 30px 110px 0 0; text-align: right; font-weight: bold;">&nbsp;</p>
				<table class="board_list">
				    <tr>
				        <td>Errordfdfdf</td>
				    </tr>
				    <tr>
				        <td><input type="button" name="btnback" value="Back"  onclick="fn_back('${return_url}');"></td>
				    </tr>
				</table>
			</div>
		</div>
	</form>

	<%@ include file="/WEB-INF/include/include-form.jspf"%>

	<script type="text/javascript">
	
        function fn_back(return_url) {
        	var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='" + return_url + "'/>");
            comSubmit.submit(); 
        } 
    </script>
</body>
</html>
