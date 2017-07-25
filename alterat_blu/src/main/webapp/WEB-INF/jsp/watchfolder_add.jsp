<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<link rel="stylesheet" href="css/tc.css">
</head>

<body class="bg_color">
    <form id="frm">
	<div class="section_wrap">
    	<!-- 상단 메뉴 영역 -->
    	<script type="text/javascript" src="js/top.js"></script>
    	<!--// 상단 메뉴 영역 -->
       
        <div class="section_wrap">
            <div class="nps_wrap">
            	<table>
                	<col width="300"><col width="1070">
                	<tr>
                    	<th>Name</th>
                        <td class="tl"><input id="id_w_name" name="w_name" type="text" class="style_input01"></td>
                    </tr>
                    <tr>
                    	<th>Source Directory</th>
                        <td class="tl"><input id="id_source_directory" name="source_directory" type="text" class="style_input01"></td>
                    </tr>
                     <tr>
                    	<th>Target Directory</th>
                        <td class="tl"><input id="id_target_directory" name="target_directory" type="text" class="style_input01"></td>
                    </tr>
                    <tr>
                    	<th>Enable</th>
                        <td class="tl"><input id="id_enable_yn" name="enable_yn" type="checkbox" class="chk_sty"></td>
                    </tr>
                    <tr>
                        <th>Manual</th>
                            <td><input id="id_manual_yn" name="manual_yn" type="checkbox" class="chk_sty"></td>
                        </tr>
                    <tr>
                        <th>Worker</th>
                        <td> 
                            <select id="id_worker" name="worker" onchange="fn_change_item();">
                                <option value="mpeg2video" selected>mpeg2video</option>
                                <option value="mp4">mp4</option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="btn_add">
                	<a href="#this" id="write">Add</a>
                </div>
            </div>
        </div>
    </div>
    </form>
    <%@ include file="/WEB-INF/include/include-form.jspf"%>
    <div class="footer_wrap">
        <img src="images/footer_text.png">
    </div>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#write").on("click", function(e) { 
                e.preventDefault();
                fn_watchfodlerInsert();
            });
        });

        function fn_watchfodlerInsert() {
            if (document.getElementById('id_w_name').value == "") {
                alert("제목을 입력하세요");
                return false;
            }
            if (document.getElementById('id_source_directory').value == "") {
                alert("소스 디렉토리를 입력하세요");
                return false;
            }
            if (document.getElementById('id_target_directory').value == "") {
                alert("타겟 디렉토리를 입력하세요");
                return false;
            } 

            var comSubmit = new ComSubmit("frm");
            comSubmit.setUrl("<c:url value='/insertWatchfolder.do' />");
            comSubmit.addParam("method", "copy");
            comSubmit.submit();
        }
    </script>
</body>
</html>
