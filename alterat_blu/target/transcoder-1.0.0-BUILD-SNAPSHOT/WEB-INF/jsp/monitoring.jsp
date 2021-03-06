<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<style>
  .ui-progressbar {
    position: relative;
    width: 120px;
  }
  .ui-datepicker {
	font-size: 12px;
	width: 160px;
	}
  .ui-datepicker select.ui-datepicker-month {
		width: 30%;
		font-size: 11px;
	}
	.ui-datepicker select.ui-datepicker-year {
		width: 40%;
		font-size: 11px;
	}
</style>
  
  
<script type="text/javascript">

//progress bar start
function getprogress(idx,j_id){
	
	var val ="";
	var job_status ="";
	var starttime ="";
	var endtime ="";
	var diff ="";
	var j_type ="";
	var target_dir ="";
	var target_nm ="";
 $(function() {

    var progressbar = $( "#progressbar"+idx ),
        progressLabel = $( "#progress-label"+idx );

         progressbar.progressbar({

	      value: false,
	      change: function() {
	      progressLabel.text( progressbar.progressbar( "value" ) + "%" );
	      },
	      complete: function() {
	        progressLabel.text( "100%" );
	      }
	    });

    function progress() {
	  	$.ajax({
			async : false,
			url : "/getprogress.do?j_id="+j_id,
			success : function(data, status, xhr) {
	// 				alert("data=="+data.params.job_progress);
				      val = data.params.job_progress;
				      job_status = data.params.job_status;
				      starttime = data.params.job_starttime;
				      endtime = data.params.job_endtime;
				      diff = data.params.diff;
				      j_type = data.params.j_type;
				      target_dir = data.params.target_directory;
				      target_nm = data.params.target_name;
				      progressbar.progressbar( "value", val);
							     
			},
			error : function(data, status, xhr) {
						console.log(data);
			}
		});
	  	
      if ( val <= 100 && (job_status == "Inprogress" || job_status == "Hold On")){
    	  $("#starttime"+idx).html(starttime);
    	  $("#job_status"+idx).html("Inprogress"); 
    	  $("#targetpath"+idx).html(target_dir+"/"+target_nm); 
    	  
    	  setTimeout( progress, 80 );
      }
      if ( val == 100 && job_status == "Completed" ){
    	  $("#starttime"+idx).html(starttime);
    	  $("#endtime"+idx).html(endtime);
    	  $("#job_status"+idx).html("Completed<br>"+diff);
    	  $("#targetpath"+idx).html(target_dir+"/"+target_nm); 
    	  $("#control"+idx).html("<a href=\"javascript:fn_restart_job('"+j_id+"', '"+j_type+"');\"><img src=\"images/btn_restart.png\"></a>");
    
      }
    }
    setTimeout( progress, 0 );
    $("#control"+idx).html("<a href=\"javascript:fn_restart_job('"+j_id+"', '"+j_type+"');\"><img src=\"images/btn_restart.png\"></a><a href=\"javascript:fn_cancel_job('"+j_id+"', '"+j_type+"');\"><img src=\"images/btn_cancel.png\" style=\"margin-left: 6px;\"></a>");
  });
}
//progress bar end



	$(function() {
		$("#datepicker1, #datepicker2").datepicker({
			dateFormat : 'yy-mm-dd',
			showMonthAfterYear : true,
			showButtonPanel : true,
			currentText : 'Today',
			changeMonth : false,
			changeYear : false,
			closeText : 'Close'
		});
	});
</script>
</head>

<body class="bg_color">
	<form id="frm">
		<div class="section_wrap">
			<!-- 상단 메뉴 영역 -->
			<script type="text/javascript" src="js/top.js"></script>
			<!--// 상단 메뉴 영역 -->
			<div id="pop4" class="pop_wrap" style="display: none;">
				<p style="position: relative;">
					<strong style="font-size: 16px; color: #f90;">파일정보</strong> <img id="close1" onclick="javascript:closeBtn('pop4');"
						src="images/pop_close.png" alt="close" style="position: absolute; right: 0; top: 0px;"
					>
				</p>
				<div id="pop4content" class="file_info_txt">Intentionally Blank</div>
			</div>

			<div class="nps_wrap">
				<p style="padding: 0 0 30px 0; font-weight: bold;">
					<span>
						<input id="chk_all" name="chk_all" type="checkbox" value="Y" class="chk_sty" <c:if test="${chk_all == 'Y'}">checked</c:if>>
						전체
						<input id="chk_transcoding" name="chk_transcoding" type="checkbox" value="Y" class="chk_sty"
							<c:if test="${chk_transcoding == 'Y'}">checked</c:if>
						>
						Transcoding
						<input id="chk_transferring" name="chk_transferring" type="checkbox" value="Y" class="chk_sty"
							<c:if test="${chk_transferring == 'Y'}">checked</c:if>
						>
						Transferring
						<input id="chk_merge" name="chk_merge" type="checkbox" value="Y" class="chk_sty" <c:if test="${chk_merge == 'Y'}">checked</c:if>>
						Merge
					</span>
					<span style="margin-left: 30px;">
						기간검색
						<input name="begin_date" id="datepicker1" type="text" size="11" maxlength="10" value=<c:out value="${begin_date}"/>>
						~
						<input name="end_date" id="datepicker2" type="text" size="11" maxlength="10" value=<c:out value="${end_date}"/>>
						<a href="javascript:fn_search();">
							<img src="images/btn_search.png" style="vertical-align: middle;">
						</a>
					</span>
					<span style="margin-left: 30px;">
						<!--status
						<select id="id_job_status" name="job_status" onchange="fn_change_item();" class="input_text">
							<option value="Hold On" <c:if test="${job_status =='Hold On'}">selected</c:if>>Hold On</option>
							<option value="Progress" <c:if test="${job_status =='Progress'}">selected</c:if>>Progress</option>
							<option value="Failed" <c:if test="${job_status =='Failed'}">selected</c:if>>Failed</option>
							<option value="Completed" <c:if test="${job_status =='Completed'}">selected</c:if>>Completed</option>
						</select>-->
						<input id="chk_status_all" name="chk_status_all" type="checkbox" value="Y" class="chk_sty"
							<c:if test="${chk_status_all == 'Y'}">checked</c:if>
						>
						전체상태
						<input id="chk_holdon" name="chk_holdon" type="checkbox" value="Hold On" class="chk_sty"
							<c:if test="${chk_holdon == 'Hold On'}">checked</c:if>
						>
						Hold On
						<input id="chk_progress" name="chk_progress" type="checkbox" value="Inprogress" class="chk_sty"
							<c:if test="${chk_progress == 'Inprogress'}">checked</c:if>
						>
						Inprogress
						<input id="chk_canceled" name="chk_canceled" type="checkbox" value="Canceled" class="chk_sty"
							<c:if test="${chk_canceled == 'Canceled'}">checked</c:if>
						>
						Canceled
						<input id="chk_failed" name="chk_failed" type="checkbox" value="Failed" class="chk_sty"
							<c:if test="${chk_failed == 'Failed'}">checked</c:if>
						>
						Failed
						<input id="chk_completed" name="chk_completed" type="checkbox" value="Completed" class="chk_sty"
							<c:if test="${chk_completed == 'Completed'}">checked</c:if>
						>
						Completed
					</span>
					<span style="margin-left: 30px;">
						<input id="chk_auto_refresh" name="chk_auto_refresh" type="checkbox" value="" class="chk_sty"
							<c:if test="${check_yn == 'Y'}">checked</c:if>
						>
						자동갱신
					</span>
				</p>
				<table>
					<col width="100">
					<col width="90">
					<col width="400">
					<col width="100">
					<col width="130">
					<col width="300">
					<col width="120">
					<col width="120">
					<col width="100">
					<col width="200">
					<tr>
						<th>ID</th>
						<th>Type</th>
						<th>Source Path</th>
						<th>Size</th>
						<th>Progress</th>
						<th>Target Path</th>
						<th>start</th>
						<th>End</th>
						<th>Status</th>
						<th>Control</th>
					</tr>

					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach items="${list}" var="row" varStatus="status">
								<tr>
									<td>${row.j_id}</td>
									<td>${row.j_type}</td>
									<td>
										<!--a href="#" onclick="javascript:clickBtn('pop4');">${row.file_path}</a-->
										<a href="#" onclick="javascript:fn_requestMeta('${row.j_id}');">${row.file_path}</a>
									</td>
									<td>
										<c:if test="${row.file_size ne null}">${row.file_size}</c:if>
									</td>
									<td class="tl">
										<script>getprogress('${status.count}','${row.j_id}')</script>
										<div id="progressbar${status.count}">
											<div id="progress-label${status.count}" style="position: absolute;left: 40%; top: 4px;font-weight: bold;text-shadow: 1px 1px 0 #fff;">Loading...</div>
										</div>
									</td>
									<td class="tc"><div id="targetpath${status.count}"></div></td>
									<td class="tc"><div id="starttime${status.count}"></div></td>
									<td class="tc"><div id="endtime${status.count}"></div></td>
									<td class="tc"><div id="job_status${status.count}"></div></td>
									<!-- td class="rightno"><a href="#"><img src="/images/btn_restart.png"></a><a href="#"><img src="/images/btn_cancel.png" style="margin-left: 6px;"></a></td-->
									<td class="t1">
									  <div id="control${status.count}"></div>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="9" class="tc">조회된 결과가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
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

					<input id="current_page" type="hidden" value="${current_page}">
				</p> 
				<div class="btn_add" style="clear: both; margin: 30px 0;">
					<a href="javascript:fn_manual();">수동처리</a>
					<a href="javascript:fn_initialize();" style="margin: 0 10px;">이력삭제</a>
					<a href="javascript:fn_del_input();" style="margin: 0 10px 0 0;">소스 소재삭제</a>
					<a href="javascript:fn_del_output();">타겟 소재삭제</a>
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
			$("#list").on("click", function(e) {
				e.preventDefault();
				fn_openBoardList();
			});

			$("#chk_auto_refresh").on("click", function(e) {
				fn_auto_refresh();
			});

			$("#chk_all").on("click", function(e) {
				fn_chk_all();
			});

			$("#chk_status_all").on("click", function(e) {
				fn_chk_status_all();
			});
			
			
			// 오늘날짜를 셋팅한다.
			//var d = new Date(); 
			//var today = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
			//document.getElementById('datepicker1').value = today;
			//document.getElementById('datepicker2').value = today;

// 			fn_auto_refresh();
		});

		function fn_search() {
			var begin = document.getElementById('datepicker1').value;
			var end = document.getElementById('datepicker2').value;

			if (!isValidDate(begin)) {
				alert("검색기간(시작일)을 확인해주세요.");
				return;
			}
			if (!isValidDate(end)) {
				alert("검색기간(종료일)을 확인해주세요.");
				return;
			}

			fn_pageNavigation(0);
		}

		function fn_pageNavigation(page_no) {
			var comSubmit = new ComSubmit("frm");
			comSubmit.setUrl("<c:url value='/selectMonitoring.do'/>");
			comSubmit.addParam("current_page", page_no);

			var check_yn = document.getElementById('chk_auto_refresh').checked;
			if (check_yn) {
				comSubmit.addParam("check_yn", 'Y');
			} else {
				comSubmit.addParam("check_yn", 'N');
			}

			comSubmit.submit();
		}

		var t_id = null;

		function fn_auto_refresh() {
			if (document.getElementById('chk_auto_refresh').checked) {
				t_id = setTimeout(function() {
					fn_reload()
				}, 3 * 1000);
			} else {
				if (t_id != null) {
					clearTImeout(t_id);
				}
			}
		}

		function fn_chk_all() {
			if (document.getElementById('chk_all').checked) {
				document.getElementById('chk_transcoding').checked = true;
				document.getElementById('chk_transferring').checked = true;
				document.getElementById('chk_merge').checked = true;
			} else {
				document.getElementById('chk_transcoding').checked = false;
				document.getElementById('chk_transferring').checked = false;
				document.getElementById('chk_merge').checked = false;
			}
		}

		function fn_chk_status_all() {
			if (document.getElementById('chk_status_all').checked) {
				document.getElementById('chk_holdon').checked = true;
				document.getElementById('chk_progress').checked = true;
				document.getElementById('chk_canceled').checked = true;
				document.getElementById('chk_failed').checked = true;
				document.getElementById('chk_completed').checked = true;
			} else {
				document.getElementById('chk_holdon').checked = false;
				document.getElementById('chk_progress').checked = false;
				document.getElementById('chk_canceled').checked = false;
				document.getElementById('chk_failed').checked = false;
				document.getElementById('chk_completed').checked = false;
			}
		}

		function fn_reload() {
			var current_page = document.getElementById('current_page').value;
			console.log('current_page=' + current_page);
			if (current_page == null) {
				fn_pageNavigation(0);
			} else {
				fn_pageNavigation(current_page);
			}
		}

		function fn_initialize() {
			var response = confirm("초기화 하시겠습니까? (모든 데이터가 지워집니다.)");
			if (response == true) {
				//var comSubmit = new ComSubmit("frm");
				//comSubmit.setUrl("<c:url value='/deleteMonitor.do' />");
				//comSubmit.submit();
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					// readState 
					// 0: request not initialized
					// 1: Server connection established 
					// 2: request received
					// 3: processing request
					// 4: request finished and response is ready
					// status
					// 200: "OK"
					// 404: Page not found
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						// xhttp.responseText or xhttp.responseXML
						alert("성공적으로 처리되었습니다.");
					}
				}
				xhttp.open("GET", "/deleteAll.do", true);
				xhttp.send();
			}
		}

		function fn_manual() {
			var response = confirm("수동처리 하시겠습니까?");
			if (response == true) {

				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						alert("성공적으로 처리되었습니다.");
					}
				}
				xhttp.open("GET", "/manualJobs.do", true);
				xhttp.send();
			}
		}

		function fn_change_item() {
			fn_reload();
		}

		function fn_del_input() {
			var response = confirm("Input 디렉토리내의 소재를 삭제 모두 하시겠습니까?");
			if (response == true) {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						alert("성공적으로 처리되었습니다.");
					}
				}
				xhttp.open("GET", "/deleteInputFiles.do", true);
				xhttp.send();
			}
		}

		function fn_del_output() {
			var response = confirm("Out 디렉토리내의 소재를 삭제 모두 하시겠습니까?");
			if (response == true) {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						alert("성공적으로 처리되었습니다.");
					}
				}
				xhttp.open("GET", "/deleteOutputFiles.do", true);
				xhttp.send();
			}
		}

		// 배치처리 테스트
		function fn_insert_test() {
			var response = confirm("대량 데이터를 인서트 하시겠습니까?");
			if (response == true) {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						alert("성공적으로 처리되었습니다.");
					}
				}
				xhttp.open("GET", "/testInsertBatch.do", true);
				xhttp.send();
			}
		}

		// 메타데이터를 불러온다.
		function fn_requestMeta(j_id) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					document.getElementById("pop4content").innerHTML = xhttp.responseText;
					document.getElementById("pop4").style.display = "block";
				}
			};
			xhttp.open("GET", "/requestMetadata.do?j_id=" + j_id, true);
			xhttp.send();
		}

		// 'Hold On' 작업외의 다른 작업들을 'Hold On' 상태로 만든다.
		function fn_restart_job(j_id, j_type) {
			var response = confirm("선택하신 작업을 재시작 하시게습니까?");
			if (response == true) {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						alert("성공적으로 처리되었습니다.");
					}
				}
				xhttp.open("GET", "/restartJob.do?j_id=" + j_id + "&j_type=" + j_type, true);
				xhttp.send();
			}
		}

		// 'Inprogress' 작업을 'Hold on' 상태로 변경한다.
		function fn_cancel_job(j_id, j_type) {
			var response = confirm("선택하신 작업을 취소 하시게습니까?");
			if (response == true) {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						alert("성공적으로 처리되었습니다.");
					}
				}
				xhttp.open("GET", "/cancelJob.do?j_id=" + j_id + "&j_type=" + j_type, true);
				xhttp.send();
			}
		}
	</script>
</body>
</html>