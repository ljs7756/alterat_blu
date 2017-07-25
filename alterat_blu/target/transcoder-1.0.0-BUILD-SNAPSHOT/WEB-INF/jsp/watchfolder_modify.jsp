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
                        <td class="tl"><input name="w_name" type="text" class="style_input01" value="${map.w_name}"></td>
                    </tr>
                    <tr>
                    	<th>Source Directory</th>
                        <td class="tl"><input name="source_directory" type="text" class="style_input01" value="${map.source_directory}"></td>
                    </tr>
                     <tr>
                    	<th>Target Directory</th>
                        <td class="tl"><input name="target_directory" type="text" class="style_input01" value="${map.target_directory}"></td>
                    </tr>
                    <tr>
                    	<th>Enable</th>
                        <td class="tl"><input name="enable_yn" type="checkbox" class="chk_sty" <c:if test="${map.enable_yn == 'Y'}">checked</c:if>></td>
                    </tr>
                    <tr>
                        <th>Manual</th>
                            <td><input name="manual_yn" type="checkbox" class="chk_sty" <c:if test="${map.manual_yn == 'Y'}">checked</c:if>></td>
                        </tr>
                    <tr>
                        <th>Worker</th>
                        <td> 
                            <select id="id_worker" name="worker" onchange="fn_change_item();">
                                <option value="mpeg2video" <c:if test="${map.worker == 'mpeg2video'}">selected</c:if>>mpeg2video</option>
                                <option value="mp4" <c:if test="${map.worker == 'mp4'}">selected</c:if>>mp4</option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="btn_add">
                	<a href="#this" id="modify">Modify</a> <a href="#this" id="delete">Delete</a> 
                    <a href="#this" id="list">List</a>
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
            $("#modify").on("click", function(e) {
                e.preventDefault();
                fn_modifyProfile();
            });

            $("#delete").on("click", function(e) {
                e.preventDefault();
                fn_deleteProfile();
            });

            $("#list").on("click", function(e) {
                e.preventDefault();
                fn_listProfile();
            });
        });

        function fn_modifyProfile() {
            var comSubmit = new ComSubmit("frm");
            comSubmit.setUrl("<c:url value='/updateWatchfolder.do'/>");
            comSubmit.addParam("w_id", "${map.w_id}")
            comSubmit.addParam("method", "copy")
            comSubmit.submit();
        }

        function fn_deleteProfile() {
            var response = confirm("삭제 하시겠습니까?");
            if (response == true) {
                var comSubmit = new ComSubmit("frm");
                comSubmit.setUrl("<c:url value='/deleteWatchfolder.do' />");
                comSubmit.addParam("w_id", "${map.w_id}")
                comSubmit.submit();
            }
        } 

        function fn_listProfile() {
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/selectWatchfolder.do'/>"); 
            comSubmit.submit();
        }
    </script>
</body>
</html>
