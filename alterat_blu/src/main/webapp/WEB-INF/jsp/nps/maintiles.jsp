<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String user_id = (String) session.getAttribute("user_id");
	//String nps_url = "http://10.160.77.193:8090/index.php?flag=ingest_file";
	String nps_url = "http://cms.ddmc.com/index.php?flag=ingest_file"; // MAC 에서 수정해야함.

	if ((user_id == null) || (user_id == "")) {
	} else {
		nps_url = nps_url + "&direct=true&user_id=" + user_id;
	}
%>
<!-- <script type="text/ecmascript" src="/jquery-ui-1.11.4/jquery-ui.min.js"></script> -->
<script type="text/ecmascript" src="/css/redmond/jquery-ui-1.11.4.custom/jquery-ui.js"></script>
<script type="text/ecmascript" src="/js/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/ecmascript" src="/js/jqgrid/i18n/grid.locale-en.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="/css/redmond/jquery-ui-1.11.4.custom/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/css/ui.jqgrid.css" />

<script type="text/javascript">
	var isDebug = true;
	var iframe_url = "";
	var worker = "";
	var p_id = "";
	var bitrate = "";

	$(window).load(function() {
		try {
			iframe_url = $('#nps_frame').get(0).contentWindow.document.location.href;
	
		} catch (err) {
			console.log("Error=" + err.message);
		}
		console.log("load(), iframe_url=" + iframe_url);
	});

	$(document).ready(function() {

		$("#btn_register").on("click", function(e) {
			fn_register();
			e.preventDefault();
		});

		$("#btn_merge").on("click", function(e) {
			fn_open_merge_window();
			e.preventDefault();
		});

		$("#btn_register_merge_job").on("click", function(e) {
			fn_register_merge_job();
			e.preventDefault();
		});

		// Create jqxExpander
		$('#jqxExpander').jqxExpander({
			showArrow : true,
			toggleMode : 'dblclick',
			width : '100%',
			height : '300px'
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
				//source = $.parseJSON(data);
				source = data;
				//console.log(source);
				//fn_getFileList("d:\\"); // Local 테스트 할때
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

		//$('#jqxTabs').jqxTabs({ height: 460, width: 650, showCloseButtons: true, scrollPosition: 'both', animationType: 'fade'});

		//$('#jqxTabs').on('tabclick', function (event) {
		//});

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
			width : '100%',
			height : '420',
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
				width : '50%',
				editable : false
			}, {
				text : 'date',
				datafield : 'filedate',
				width : '25%',
				editable : false,
			}, {
				text : 'filesize',
				datafield : 'filesize',
				width : '20%',
				editable : false,
				cellsalign : 'right',
				align : 'right'
			}, ]
		});
	}

	// 트랜스코딩 등록.
	function fn_register() {

			var rows = $("#jqxgrid").jqxGrid('selectedrowindexes'); // 선택한 row의 배열을 리턴한다.
			var rows_length = rows.length;

// 			alert("rows=" + rows);

			if (rows_length < 1) {
				alert("파일을 선택해 주세요.");
				return;
			}
			
			var rows_detail = $("#detailGrid").jqxGrid('selectedrowindexes'); // 선택한 row의 배열을 리턴한다.
			var rows_detail_length = rows_detail.length;
// 			alert("rows_detail=" + rows_detail);
			
			if (rows_detail_length < 1) {
				alert("포맷을 선택해주세요.");
				return;
			}

			var fileArray = new Array();

			for (var iCount = 0; iCount < rows_length; iCount++) {
				var row = $("#jqxgrid").jqxGrid('getrowdata', rows[iCount]);
				for (var jCount = 0; jCount < rows_detail_length; jCount++) {
					var row_2 = $("#detailGrid").jqxGrid('getrowdata', rows_detail[jCount]);
					var fileInfo = new Object();
					fileInfo.fileid = row.fileid;
					fileInfo.filename = row.filename;
					fileInfo.worker = row_2.worker;         //선택한 프로프알 확장자
					fileInfo.bitrate = row_2.bitrate;
					fileInfo.p_id = row_2.p_id;			  // 선택한 프로파일 아이디
					fileArray.push(fileInfo);
				}
			}

			// 보낼 json 객체를 만들자.
			var sendData = new Object();
			sendData.file = fileArray;

			var json_filedata = JSON.stringify(sendData);
			console.log("json_filedata=" + json_filedata);

			if (confirm("등록하시겠습니까?") == true) {
				// 트랜스코딩작업에 등록한후 변환이 완료되면 NPS에 등록한다.
				var post_url = "/nps/register.do";
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
						}

						console.log("fn_register(), data=" + data);
						console.log("fn_register(), data.result=" + data.result);
					}
				});

			} else {
				return;
			}
// 		}
	}

	var isMixedFileFormat = false;

	// Merger 팝업화면을 띄운다.
	function fn_open_merge_window() {
		var row_indexs = fn_getFileSelectedRowIndexs("#jqxgrid");
		if (row_indexs.length < 2) {
			alert("2개 이상의 파일을 선택해 주세요.");
			return;
		} else {
			$.jgrid.gridUnload('#jqGrid');
			var json_filedata = fn_getFileSelectedJson("#jqxgrid", row_indexs);

			if (isMixedFileFormat) {
				alert("같은 종류의 파일만 머지할 수 있습니다.");
				return;
			}

			console.log("fn_open_merge_window(), json_filedata=" + json_filedata);

			$("#jqGrid").jqGrid({
				colModel : [ {
					label : 'fileid',
					name : 'fileid',
					width : 300,
					hidden : true
				}, {
					label : 'filename',
					name : 'filename',
					width : 500
				}, {
					label : 'filesize (byte)',
					name : 'filesize',
					width : 100,
					align : 'right'
				} ],
				loadonce : true,
				width : 700,
				height : 400,
				rowNum : 300,
				shrinkToFit : true,
				datatype : "jsonstring",
				rownumbers : true,
				datastr : json_filedata,
				altRows : true,
				// altclass: '....'
				//pager: "#jqGridPager"
				loadError : function(xhr, status, error) {
					console.log("xhr.responseText=" + xhr.responseText);
					console.log("status=" + status);
					console.log("error=" + error);
				},
				loadComplete : function(data) {
					console.log("data=" + data);
				}
			});
			$("#jqGrid").jqGrid('sortableRows');
			$("#pop4").show();
		}
	}

	function fn_getFileSelectedRowIndexs(gridname) {
		var rows = $(gridname).jqxGrid('selectedrowindexes'); // 선택한 row의 배열을 리턴한다.
		return rows;
	}

	// 선택된 파일리스트를 json string으로 변환하고, 다른 파일포멧이 있는지 체크한다.
	function fn_getFileSelectedJson(gridname, row_indexs) {
		var fileArray = new Array();

		var idx = -1;
		var pre_file_ext = "";
		var file_ext = "";
		isMixedFileFormat = false;

		for (var iCount = 0; iCount < row_indexs.length; iCount++) {
			var row = $(gridname).jqxGrid('getrowdata', row_indexs[iCount]);
			var fileInfo = new Object();
			fileInfo.fileid = row.fileid;
			fileInfo.filename = row.filename;
			fileInfo.filesize = row.filesize;
			fileArray.push(fileInfo);

			// 파일 확장자 비교
			idx = row.filename.lastIndexOf(".");
			if (idx != -1) {
				file_ext = row.filename.substring(row.filename.lastIndexOf(".") + 1);
				file_ext = file_ext.toLocaleLowerCase();

				if (pre_file_ext == "") {
					pre_file_ext = file_ext;
				} else {
					if (pre_file_ext != file_ext) {
						isMixedFileFormat = true;
					}
				}
			}
		}

		var json_filedata = JSON.stringify(fileArray);

		return json_filedata;
	}

	// Merge 팝업화면에서 'Add' 버튼 클릭시에 Merger 작업을 등록한다.
	function fn_register_merge_job() {
		
		if (confirm("머지작업을 등록하시겠습니까?") == false) {
			return;
		}

		// jqgrid의 row 정보를 가져오자 
		var row_count = $("#jqGrid").getGridParam("records");
		//jQgrid의 전체 row 데이터 가져오기
		var rowData = jQuery("#jqGrid").jqGrid("getRowData");
		//jQgrid의 전체 row의 ID 가져오기
		var rowID = jQuery("#jqGrid").jqGrid("getDataIDs");

		console.log("rowData=" + rowData);

		var fileArray = new Array();
		
			
		for (i = 0; i < row_count; i++) {
			var fileInfo = new Object();
			var row = rowData[i];
			fileInfo.ordernum = i;
			fileInfo.fileid = row.fileid;
			fileArray.push(fileInfo);
		}
		console.log(fileArray);

		// pooq 자동전송여부 
		var ftpYn = "N";
		if($("#ftpYn").is(":checked") == true){
			ftpYn = "Y";
		}

		var json_filedata = JSON.stringify(fileArray);
		var post_url = "/nps/muxingRegist.do";

		console.log("fn_register_merge_job(), json_filedata=" + json_filedata);

		var request = $.ajax({
			url : post_url,
			type : "POST",
			data : {
				json_filedata : json_filedata,
				ftpYn : ftpYn
			},
			dataType : "JSON",
			success : function(data, status, xhr) {
				if (data.result == "-1") {
					alert("등록 실패.");
				} else {
					alert("등록 성공.");
				}

				console.log("data=" + data);
				console.log("data.result=" + data.result);

				$("#pop4").hide();
			}
		});
	}

	function fn_regist_nps() {
		var settings = 'toolbar=0, status=0, menubar=0, scrollbars=yes, height=400, width=700';
		var target = '/nps/registNps.do';

		var popup = window.open(target, 'register nps', settings);
	}

    $(document).ready(function () {
    	
		var profileChannel;
		
    	$.ajax({
			async : false,
			url : "/profileChannel.do",
			success : function(data, status, xhr) {
				profileChannel = data.list;
			},
			error : function(data, status, xhr) {
				console.log(data);
			}
		});
    	
    	
        // prepare the data
        var source =
        {
            datafields: [{ name: 'common' }],
            localdata: profileChannel

        };
        var dataAdapter = new $.jqx.dataAdapter(source);
        $("#formatGrid").jqxGrid(
        {

            width: '100%',
            height: 250,
            source: dataAdapter,

            keyboardnavigation: false,

            columns: [
                { text: '채널', datafield: 'common', width: 850 }

            ]

        });
    	
        
 		var profileList;

    	$.ajax({
			async : false,
			url : "/profileList.do",
			success : function(data, status, xhr) {

				profileList = data.list;
				
			},
			error : function(data, status, xhr) {
				console.log(data);
			}
		});
        
    	
        var dataFields = [
                    { name: 'common' },
                    { name: 'worker' },
                    { name: 'p_name' },
                    { name: 'p_id' },
                    { name: 'bitrate' },
                    { name: 'video_option' }
                ];
        var source =
        {
            datafields: dataFields,
            localdata: profileList
        };
       
        
        var dataAdapter = new $.jqx.dataAdapter(source);

        dataAdapter.dataBind();

        $("#formatGrid").on('rowselect', function (event) {

            var common = event.args.row.common;

            var records = new Array();

            var length = dataAdapter.records.length;

            for (var i = 0; i < length; i++) {

                var record = dataAdapter.records[i];

                if (record.common == common) {
                    records[records.length] = record;
                }

            }

            var dataSource = {

                datafields: dataFields,
                localdata: records

            }

            var adapter = new $.jqx.dataAdapter(dataSource);

            // update data source.

            $("#detailGrid").jqxGrid({ source: adapter });

        });
        
      
        $("#detailGrid").jqxGrid(

        {

            width: '100%',
            height: 250,
            keyboardnavigation: false,
			columnsresize : true,
			sortable: true,
			selectionmode : 'checkbox',
            columns: [
                { text: '아이디', datafield: 'p_id', width: 150 },
                { text: '이름', datafield: 'p_name', width: 150 },
                { text: '비트레이트', datafield: 'bitrate', width: 150 },
                { text: '확장자', datafield: 'worker', width: 100 },
                { text: '포맷', datafield: 'video_option', width: 350 }

            ]
            

        });

        $("#formatGrid").jqxGrid('selectrow', 0);
        
        
        $("#detailGrid").on('rowselect', function (event) {

             worker    = event.args.row.worker;
             p_id      = event.args.row.p_id; 
             bitrate   = event.args.row.bitrate; 

        });


    });	
	
</script>

<!-- Muxing 버튼을 눌렀을때 뜨는 팝업화면  -->
<div id="pop4" class="pop_wrap" style="display: none">
	<p style="position: relative;">
		<strong style="font-size: 16px; color: #f90;">File Muxing</strong> <img id="close1" onclick="javascript:closeBtn('pop4');"
			src="/images/pop_close.png" alt="close" style="position: absolute; right: 0; top: 0px;"
		>
	</p>

	<div class="file_info_txt">
		<table id="jqGrid"></table>
		<!-- <div id="jqGridPager"></div> -->
		<div style="text-align: center; padding: 5px 0 0 0;">
			<p style="text-align: center; padding: 5px 0 0 0;">
				<img id="btn_register_merge_job" src="/images/btn_add.png">
			</p>
			<p style="text-align: right; padding: -30px 0 0 0;">
			 pooq으로 자동 전송 <input id="ftpYn" name="ftpYn" type="checkbox" value="Y" class="chk_sty">
			 </p>
		</div>
	</div>
</div>

<form id="regist_form">
	<div class="nps_wrap">
		<div class="nps_left">
			<div style="text-align: center;">
				<div id='jqxWidget'>
					<div id='jqxExpander'>
						<div>Folders</div>
						<div style="overflow: hidden;">
							<div style="border: none;" id='jqxTree'></div>
						</div>
					</div>
				</div>
			</div>
			<!--
                <div style="text-align:right; padding:3px 0 2px 0;">
                    <p style="text-align:right; padding:0px 20px 0 0;"><img src="/images/btn_retry.png"></p>
                </div>
                -->

			<div style="text-align: center; padding: 3px 0 0 0;">
				<div id="jqxgrid"></div>
			</div>
			<div style="text-align: center; padding: 5px 0 0 0;">
				<p style="text-align: center; padding: 5px 0 0 0;">
					<img id="btn_register" src="/images/btn_trans.png"><img id="btn_merge" src="/images/btn_merge.png">
				</p>
			</div>
		</div>
<!-- 		<div class="nps_right"> -->
<%-- 			<iframe id="nps_frame" src="<%=nps_url%>" frameborder="0" width="700px" height="807px" scrolling="yes"></iframe> --%>
<!-- 		</div> -->
    <div class="nps_right" id='jqxWidget1' style="font-size: 15px; font-family: Verdana; float: left;margin-left:10px;">
<!--         <h3>Customers</h3> -->
        <div id="formatGrid"></div>
<!--         <h3>Orders by Customer</h3> -->
        <div id="detailGrid"></div>
    </div>
	</div>
</form>

