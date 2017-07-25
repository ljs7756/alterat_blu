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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/magnific-popup.min.css">
 <link rel="stylesheet" href="/js/jqwidgets/styles/jqx.base.css" type="text/css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/jquery.magnific-popup.min.js"></script>

        <script type="text/javascript" src="/js/jqwidgets/jqxcore.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxtabs.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxbuttons.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxscrollbar.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxpanel.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxtree.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxexpander.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxdata.js"></script> 
        <script type="text/javascript" src="/js/jqwidgets/jqxmenu.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxcheckbox.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxlistbox.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxdropdownlist.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxgrid.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxgrid.sort.js"></script> 
        <script type="text/javascript" src="/js/jqwidgets/jqxgrid.pager.js"></script> 
        <script type="text/javascript" src="/js/jqwidgets/jqxgrid.selection.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxgrid.columnsresize.js"></script>  
        <script type="text/javascript" src="/js/jqwidgets/jqxgrid.edit.js"></script>
        <script type="text/javascript" src="/js/jqwidgets/jqxgrid.filter.js"></script>
<script type="text/javascript">

//progress bar start
function getprogress(idx,no){
	
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
			url : "/getprogressFtp.do?no="+no,
			success : function(data, status, xhr) {
	// 				alert("data=="+data.params.job_progress);
				      val = data.params.progress;
				      job_status = data.params.job_status;
				      starttime = data.params.start_date;
				      endtime = data.params.end_date;
				      diff = data.params.diff;
				      progressbar.progressbar( "value", val);
							     
			},
			error : function(data, status, xhr) {
						console.log(data);
			}
		});
	  	
      if ( (val <= 100 || val == null) && (job_status == "Inprogress" || job_status == "Standby")){
    	  $("#starttime"+idx).html(starttime);
    	  $("#job_status"+idx).html(job_status); 
    	  $("#control"+idx).html("<a href=\"javascript:fn_cancel_job('"+no+"','Transferring');\"><img src=\"images/btn_cancel.png\" style=\"margin-left: 6px;\"></a>");
    	  setTimeout( progress, 400 );
      }else if ( val == 100 && job_status == "Completed" ){
    	  $("#starttime"+idx).html(starttime);
    	  $("#endtime"+idx).html(endtime);
    	  $("#job_status"+idx).html("Completed<br>"+diff);
    	  $("#control"+idx).html("");
      }else{
    	  $("#job_status"+idx).html(job_status);
      }
    }
    setTimeout( progress, 0 );
   
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
		
		$("#btn_sending").on("click", function(e) {
			e.preventDefault();
			var rows = $("#jqxgrid").jqxGrid('selectedrowindexes'); // 선택한 row의 배열을 리턴한다.
			var rows_length = rows.length;
// 			alert("rows=" + rows);
			if (rows_length < 1) {
				alert("파일을 선택해 주세요.");
				return;
			}
		
			var rows_ftp = $("#ftplist").jqxGrid('selectedrowindexes'); // 선택한 row의 배열을 리턴한다.
			var rows_ftp_length = rows_ftp.length;
		
// 			alert("rows_detail=" + rows_detail);
			if (rows_ftp_length < 1) {
				alert("ftp를 선택해주세요.");
				return;
			}

			var fileArray = new Array();

			for (var iCount = 0; iCount < rows_length; iCount++) {
				var row = $("#jqxgrid").jqxGrid('getrowdata', rows[iCount]);
				for (var jCount = 0; jCount < rows_ftp_length; jCount++) {
					var row_2 = $("#ftplist").jqxGrid('getrowdata', rows_ftp[jCount]);
					var fileInfo = new Object();
					fileInfo.fileid = row.fileid;
					fileInfo.filename = row.filename;
					fileInfo.no = row_2.no;
					fileInfo.channel = row_2.channel;        
					fileArray.push(fileInfo);
				}
			}
			
			// 보낼 json 객체를 만들자.
			var sendData = new Object();
			sendData.file = fileArray;

			var json_filedata = JSON.stringify(sendData);

			if (confirm("등록하시겠습니까?") == true) {

				var post_url = "/filesending.do";
				var request = $.ajax({
					url : post_url,
					type : "POST",
					data : {
						json_filedata : json_filedata
					},
					dataType : "JSON",
					success : function(data, status, xhr) {
						if (data.result == "-1") {
							alert("등록 실패.");
						} else {
							alert("등록 성공.");
							$.magnificPopup.close();
							window.location.reload();
						}

					}
				});

			} else {
				return;
			}
		
			
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
					<a href="#sendingPop" class="btn btn-info schedule-popup">전송 추가</a>
				</p>
				<table>
					<col width="80">
					<col width="400">
					<col width="130">
					<col width="130">
					<col width="100">
					<col width="300">
					<col width="120">
					<col width="120">
					<col width="100">
					<col width="200">
					<tr>
						<th>NO</th>
						<th>Source Path</th>
						<th>File Size</th>
						<th>Progress</th>
						<th>CHANNEL</th>
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
									<td>${row.no}</td>
									<td>${row.source_path}</td>
									<td>${row.file_size}</td>
									<td class="tl">
										<script>getprogress('${status.count}','${row.no}')</script>
										<div id="progressbar${status.count}">
											<div id="progress-label${status.count}" style="position: absolute;left: 40%; top: 4px;font-weight: bold;text-shadow: 1px 1px 0 #fff;">Loading...</div>
										</div>
									</td>
									<td>${row.channel}</td>
									<td>${row.target_path}</td>
									<td class="tc"><div id="starttime${status.count}"></div></td>
									<td class="tc"><div id="endtime${status.count}"></div></td>
									<td class="tc"><div id="job_status${status.count}"></div></td>
									<td class="t1">
									  <div id="control${status.count}"></div>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="10" class="tc">조회된 결과가 없습니다.</td>
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
							<c:choose>
								<c:when test="${current_page == i}">
									<strong class="now">${i + 1}</strong>
								</c:when>
								<c:otherwise>
									<strong>${i + 1}</strong>
								</c:otherwise>
							</c:choose>
						</a>
					</c:forEach>
					<c:if test="${current_page < tot_page - 10}">
						<a href="#" onclick="fn_pageNavigation('${pages + 1}'); return false;">
							<img src="/images/next.png">
						</a>
					</c:if>

					<input id="current_page" type="hidden" value="${current_page}">
				</p> 
			</div>
		</div>
	</form>

	 <div id="sendingPop" class="mfp-hide" style="margin-left:230px;width:900px;height:710px;background-color:#313131;">
					<div id='jqxExpander' style="float:left;margin-right:3px;">
						<div>Folders</div>
						<div style="overflow: hidden;">
							<div style="border: none;" id='jqxTree'></div>
						</div>
					</div>
					<div id="jqxgrid"></div>
					<div id="ftplist" style="margin-top:5px;"></div>
					<p style="text-align: center; padding: 5px 0 0 0;">
					<img id="btn_sending" src="/images/btn_add.png">
					</p>
	</div>

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


			$("#chk_status_all").on("click", function(e) {
				fn_chk_status_all();
			});
			
			
        	$('.schedule-popup').magnificPopup({
        		type: 'inline',
        		mainClass: 'mfp-inline-mobile',
        		preloader: false,
        		open: function(){
                     $("#test-modal").css("display", "block");
                 },
        		modal: false
        	});
        	
    		// Create jqxExpander
    		$('#jqxExpander').jqxExpander({
    			showArrow : true,
    			toggleMode : 'dblclick',
    			width : '30%',
    			height : '400px'
    		});
    		// Create jqxTree
    		var tree = $('#jqxTree');
    		var file_url = "/nps/directory.do";
    		var source = null;
    		$.ajax({
    			async : false,
    			url : file_url,
    			dataType : "JSON",
    			success : function(data, status, xhr) {
    				source = data;
    				fn_getFileList("/");  // MAC 에서 수정해야함.
    			},
    			error : function(data, status, xhr) {
    				console.log(data);
    			}
    		});

    		tree.jqxTree({
    			source : source,
    			width : '100%',
    			height : '100%'
    		});

    		tree.on('expand', function(event) {
    			var label = tree.jqxTree('getItem', event.args.element).label;
    			var $element = $(event.args.element);
    			var loader = false;
    			var loaderItem = null;
    			var children = $element.find('ul:first').children();
    			console.log(tree.jqxTree('getItem', event.args.element).value);
    			$.each(children, function() {
    				var item = tree.jqxTree('getItem', this);
    				if (item && item.label == 'Loading...') {
    					loaderItem = item;
    					loader = true;
    					return false
    				}
    				;
    			});

    			if (loader) {
    				$.ajax({
    					url : loaderItem.value,
    					data : {
    						'folder' : tree.jqxTree('getItem', event.args.element).value
    					},
    					success : function(data, status, xhr) {
    						//var items = jQuery.parseJSON(data);
    						var items = data;
    						console.log(items);
    						tree.jqxTree('addTo', items.result, $element[0]);
    						tree.jqxTree('removeItem', loaderItem.element);
    					}
    				});
    			}
    		});

    		$('#jqxTree').on('select', function(event) {
    			var args = event.args;
    			var item = $('#jqxTree').jqxTree('getItem', args.element);
    			fn_getFileList(item.value);
    		});

    		$("#jqxgrid").on('rowselect', function(event) {
    			console.log(event);
    			var row = event.args.row;
    			console.log(row)
    			//alert(event.args.row.fileid);
    		});

    		$("#jqxgrid").on('rowclick', function() {
    			// put the focus back to the Grid. Otherwise, the focus goes to the drag feedback element.
    			$("#jqxgrid").jqxGrid('focus');
    		});
    		
    		
    		function fn_getFileList(directory) {

    			var url = "/nps/file.do";
    			// prepare the data
    			var source = {
    				datatype : "json",
    				datafields : [ {
    					name : 'fileid',
    					type : 'string'
    				}, {
    					name : 'selected',
    					type : 'bool'
    				}, {
    					name : 'filename',
    					type : 'string'
    				}, {
    					name : 'filesize',
    					type : 'string'
    				}, {
    					name : 'filedate',
    					type : 'string'
    				},],
    				id : 'fileid',
    				url : url
    			};

    			var dataAdapter = new $.jqx.dataAdapter(source, {
    				formatData : function(data) {
    					$.extend(data, {
    						folder : directory
    					});
    					return data;
    				}
    			});

    			$("#jqxgrid").jqxGrid({
    				width : '69%',
    				height : '400',
    				source : dataAdapter,
    				editable : true,
    				columnsresize : true,
    				sortable: true,
    				selectionmode : 'checkbox',
    				columns : [ {
    					text : 'fileid',
    					datafield : 'fileid',
    					width : '0%',
    					editable : false,
    					hidden : true
    				}, {
    					text : '',
    					datafield : 'selected',
    					width : '5%',
    					editable : true,
    					hidden : true,
    					columntype : 'checkbox'
    				}, {
    					text : 'filename',
    					datafield : 'filename',
    					width : '48%',
    					editable : false
    				}, {
    					text : 'date',
    					datafield : 'filedate',
    					width : '25%',
    					editable : false,
    				}, {
    					text : 'filesize',
    					datafield : 'filesize',
    					width : '21%',
    					editable : false,
    					cellsalign : 'right',
    					align : 'right'
    				}, ]
    			});
    		}
    		
     		var profileList;

        	$.ajax({
    			async : false,
    			url : "/ftpinfo.do",
    			success : function(data, status, xhr) {
    				profileList = data.list;
    			},
    			error : function(data, status, xhr) {
    				console.log(data);
    			}
    		});
        	
            var dataFields = [
                        { name: 'no',type: 'int' },
                        { name: 'channel' },
                        { name: 'address' },
                        { name: 'id' },
                        { name: 'ftp_port' },
                        { name: 'regdate',type: 'date' }
                    ];
            var source =
            {
                datafields: dataFields,
                localdata: profileList
            };
           
            var adapter = new $.jqx.dataAdapter(source);
            $("#ftplist").jqxGrid({ source: adapter });
            $("#ftplist").jqxGrid(

                    {
                        width: '99%',
                        height: 250,
                        keyboardnavigation: false,
            			columnsresize : true,
            			sortable: true,
            			selectionmode : 'checkbox',
                        columns: [
                            { text: '번호', datafield: 'no', width: "5%" },
                            { text: '채널', datafield: 'channel', width: "15%" },
                            { text: '주소', datafield: 'address', width: "20%" },
                            { text: '아이디', datafield: 'id', width: "10%" },
                            { text: '포트', datafield: 'ftp_port', width: "10%" },
                            { text: '등록일', datafield: 'regdate', width: "40%", cellsformat: 'yyyy-MM-dd' }
                        ]

                    });
			
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
			comSubmit.setUrl("<c:url value='/selectSending.do'/>");
			comSubmit.addParam("current_page", page_no);

			comSubmit.submit();
		}

		var t_id = null;

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


		function fn_change_item() {
			fn_reload();
		}
		
		function fn_cancel_job(no,type) {
			
	    	if(confirm("선택하신 작업을 취소 하시게습니까?")==true){
				$.ajax({
					url :"cancelFtp.do?j_id=" + no + "&j_type=" + type+"",
					success : function(data, status, xhr) {
						alert("성공적으로 처리되었습니다.");
						window.location.reload();
					},
					error : function(data, status, xhr) {
						alert("error");
					}
				});
	    	}
		}

		
	</script>
</body>
</html>