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
	<div class="section_wrap_l">
    	<!-- 상단 메뉴 영역 -->
    	<script type="text/javascript" src="js/top.js"></script>
    	<!--// 상단 메뉴 영역 -->
    	<div class="left_list">
            <img src="images/profile_list_tit.jpg">
            <ul>
            	<c:forEach var="list" items="${channelList}" varStatus ="status">
	            	<c:choose>
					<c:when test="${common == list.common}">
						<li><a href="/selectProfile.do?common=${list.common}"><font color="white">${list.common}</font></a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/selectProfile.do?common=${list.common}">${list.common}</a></li>
					</c:otherwise>
				</c:choose>
				</c:forEach>
            </ul>
        </div>
        <div class="right_wrap">
            <div class="right_con">
            	<table>
                	<col width="120">
                    <col width="200">
                    <col width="50">
                    <col width="250">
                    <col width="250">
                	<tr>
                    	<th>ID</th>
                        <th>Name</th>
                        <th>Enable</th>
                        <th>Video</th>
                        <th class="rightno">Audio</th>
                    </tr>
                    <c:choose>
                       <c:when test="${fn:length(list) > 0}">
                           <c:forEach items="${list}" var="row">
                               <tr>
                                   <td class="tl"><a href="#" onclick="fn_tc_profileUpdate('${row.p_id}', '${common}'); return false;">${row.p_id}</a></td>
                                   <td><a href="#" onclick="fn_tc_profileUpdate('${row.p_id}', '${common}'); return false;">${row.p_name}</a></td> 
                                   <td>${row.enable_yn}</td>
                                   <td><strong style="color:#FC0;">${row.video_option}</strong></td>
                                   <td class="rightno"><strong style="color:#FC0;">${row.audio_option}</strong></td>
                               </tr>
                           </c:forEach>
                       </c:when>
                       <c:otherwise>
                           <tr>
                               <td colspan="6">조회된 결과가 없습니다.</td>
                           </tr>
                       </c:otherwise>
                   </c:choose>
                </table>
                <!-- 2015년 11월 27일 수정 한걸로 다시 개발-->
                <p class="list_num_1">
					<c:if test="${current_page > 9}">
						<a href="#" onclick="fn_pageNavigation('${start_page - 1}'); return false;">
							<img src="/images/previous.png">
						</a>
					</c:if>

					<c:forEach var="i" begin="${start_page}" end="${pages}" step="1">
						<a href="#" onclick="fn_pageNavigation('${i}'); return false;">
							<strong>${i + 1}</strong>
						</a>
					</c:forEach>
					<c:if test="${current_page < tot_page - 10}">
						<a href="#" onclick="fn_pageNavigation('${pages + 1}'); return false;">
							<img src="/images/next.png">
						</a>
					</c:if>
				</p>
                <!-- 2015년 11월 27일 수정 한걸로 다시 개발-->
                
                    
                <div class="btn_add">
                	<a href="/tc_profile_add.do?worker=${worker}">ADD</a>
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
            $("#tc_profileUpdate").on("click", function(e) { 
                e.preventDefault();
                fn_tc_profileUpdate();
            });
        });

        function fn_openBoardList() {
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/insertProfile.do'/>");
            comSubmit.submit();
        }

        function fn_tc_profileUpdate(p_id, common) { 
            var comSubmit = new ComSubmit("frm");
            comSubmit.setUrl("<c:url value='/selectTheProfile.do' />");
            //comSubmit.addParam("p_id", "P0000000003")
            comSubmit.addParam("p_id", p_id)
            comSubmit.addParam("common", common)
            comSubmit.submit();
        } 
        
        function fn_pageNavigation(page_no) { 
            var comSubmit = new ComSubmit("frm");
            comSubmit.setUrl("<c:url value='/selectProfile.do' />");
            comSubmit.addParam("current_page", page_no)
            comSubmit.submit();
        }
    </script>
</body>
</html>
