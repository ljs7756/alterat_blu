<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<link rel="stylesheet" href="css/tc.css">
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script>
	function clickBtn(id){
	$('#'+id).show();
	}

	function closeBtn(id){
		$('#'+id).hide();
	}
</script>
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
                	<col width="130">
                    <col width="250">
                    <col width="250">
                    <col width="250">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                	<tr>
                    	<th>ID</th>
                        <th>Name</th>
                        <th>Source Directory</th>
                        <th>Target Directory</th>
                        <th>Worker</th>
                        <th>Enable</th>
                        <th class="rightno">Manual</th>
                    </tr>
                    
                    <c:choose>
                        <c:when test="${fn:length(list) > 0}">
                            <c:forEach items="${list}" var="row">
                                <tr> 
                                    <td><a href="#" onclick="fn_watchfolderUpdate('${row.w_id}'); return false;">${row.w_id}</a></td>
                                    <td><a href="#" onclick="fn_watchfolderUpdate('${row.w_id}'); return false;">${row.w_name}</a></td>
                                    <td>${row.source_directory}</td>
                                    <td>${row.target_directory}</td>
                                    <td>${row.worker}</td>
                                    <td>${row.enable_yn}</td>
                                    <td>${row.manual_yn}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="7">조회된 결과가 없습니다.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </table>
                <!-- 2015 11월 27일 수정-->
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
                <!-- 2015 11월 27일 수정-->
                
                <!-- 2015 11월 27일 수정 이부분은 기존에 있던 list num 임 위에 넣은 소스를 기준으로 다시 개발 해야함
                <p class="list_num_watchfolder">
                    <!--<c:if test="${current_page > 0}">
                        <a href="#" onclick="fn_pageNavigation('${current_page - 1}'); return false;"><img src="/images/previous.png"></a>
                    </c:if>-->
                    <!--
                    <c:forEach var="i" begin="0" end="${tot_page}" step="1">
                        <a href="#" onclick="fn_pageNavigation('${i}'); return false;"><strong>${i + 1}</strong></a>
                    </c:forEach>
                    -->
                    <!--<c:if test="${current_page < tot_page}">
                        <a href="#" onclick="fn_pageNavigation('${current_page + 1}'); return false;"><img src="/images/next.png"></a>
                    </c:if>-->
                <!--/p>
                -->
                <div class="btn_add">
                	<a href="/watchfolder_add.do">Add</a> 
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
            $("#tc_watchfolderUpdate").on("click", function(e) {
                e.preventDefault();
                fn_tc_watchfolderUpdate();
            });
        });

        function fn_openBoardList() {
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/insertWatchfolder.do'/>");
            comSubmit.submit();
        }

        function fn_watchfolderUpdate(w_id) { 
            var comSubmit = new ComSubmit("frm");
            comSubmit.setUrl("<c:url value='/selectWatchfolderDetail.do' />");
            //comSubmit.addParam("w_id", "P0000000003")
            comSubmit.addParam("w_id", w_id)
            comSubmit.submit();
        }
        
        function fn_pageNavigation(page_no) { 
            var comSubmit = new ComSubmit("frm");
            comSubmit.setUrl("<c:url value='/selectWatchfolder.do' />");
            comSubmit.addParam("current_page", page_no)
            comSubmit.submit();
        }
    </script>
</body>
</html>
