<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>NPS 등록 테스트 :: 완료전달</title>

<%@ include file="/WEB-INF/include/include-header.jspf"%>

</head>
<body>
	<div id="demo">
		<h2>Functional Testing</h2>
	</div>

	<form>
		Task ID <input type="text" id="txtTaskID"  name="txtTaskID" value="566548" style="width: 400" /> <br>
		Type Code <input type="text" id="txtTypeCode" name="txtTypeCode" value="69" style="width: 400"/> <br>
		
		<input type="button" id="execute" value="완료처리" />
	</form>

	<%@ include file="/WEB-INF/include/include-form.jspf"%>

	<script>
		$(document).ready(function() {
			$("#execute").on("click", function(e) {
				e.preventDefault();
				fn_updateComplete();
			});
		});

		function fn_updateComplete() {
			var response = confirm("선택하신 파일들을 완료처리 하시겠습니까?");
			if (response == true) {
				if (window.XMLHttpRequest)
				{ 
					xmlRequest = new XMLHttpRequest();
				}
				else
				{// code for IE6, IE5
					xmlRequest = new ActiveXObject("Microsoft.XMLHTTP");
				}

				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						alert("성공적으로 처리되었습니다.");
					}
				}
				xhttp.open("POST", "/testRegisterComplete.do", true);

				xhttp.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				xhttp.setRequestHeader("Cache-Control",
						"no-cache, must-revalidate");
				xhttp.setRequestHeader("Pragma", "no-cache");

				var params = "txtTaskID=" + document.getElementById('txtTaskID').value;
				params += "&txtTypeCode=" + document.getElementById('txtTypeCode').value;

				xhttp.send(params); 
			}
		}
	</script>
</body>
</html>
