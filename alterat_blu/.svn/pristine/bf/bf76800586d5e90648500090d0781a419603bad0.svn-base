<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>MP4 파일 머지 테스트</title>

<%@ include file="/WEB-INF/include/include-header.jspf"%>

</head>
<body>
	<div id="demo">
		<h2>MP4 파일 머지테스트</h2>
	</div>

	<form>
		<table border="1">
			<tr>
				<td>
					Source 1 :<input type="text" id="src_01" value="d:\temp3\merge2\M2014081300228_10.MP4" style="width: 400">
				</td>
			</tr>
			<tr>
				<td>
					Source 2 :<input type="text" id="src_02" value="d:\temp3\merge2\M2014102300188_382.MP4" style="width: 400">
				</td>
			</tr>
            <tr>
                <td>
                    Source 3 :<input type="text" id="src_03" value="d:\temp3\merge2\M2014102300188_396.MP4" style="width: 400">
                </td>
            </tr>
            <tr>
                <td>
                        머지된 파일이 저장될 경로를 지정한다.(Watch folder 의 In 폴더) <br><br>
                    Destination Path :<input type="text" id="dst_01" value="d:\temp3" style="width: 200">
                </td>
            </tr>
		</table>

		<input type="button" id="execute" value="MP4파일들 Merge 직업등록" />
	</form>

	<%@ include file="/WEB-INF/include/include-form.jspf"%>

	<script>
		$(document).ready(function() {
			$("#execute").on("click", function(e) {
				e.preventDefault();
				fn_insertMergeJob();
			});
		});

		function fn_insertMergeJob() {
			var response = confirm("선택하신 파일들을 머지하시겠습니까?");
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
				xhttp.open("POST", "/insertMergeJob.do", true);

				xhttp.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				xhttp.setRequestHeader("Cache-Control",
						"no-cache, must-revalidate");
				xhttp.setRequestHeader("Pragma", "no-cache");

				var params = "src_01=" + document.getElementById('src_01').value;
				params += "&src_02=" + document.getElementById('src_02').value;
                params += "&src_03=" + document.getElementById('src_03').value;
                params += "&dst_01=" + document.getElementById('dst_01').value;

				xhttp.send(params); 
			}
		}
	</script>
</body>
</html>
