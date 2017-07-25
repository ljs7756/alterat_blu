<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="/css/jquery-ui.css">
<script src="/js/jquery.min.js"></script>
<script src="/js/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
	$(function() {
		$("#datepicker1, #datepicker2").datepicker(
				{
					dateFormat : 'yy-mm-dd',
					prevText : '이전 달',
					nextText : '다음 달',
					monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월',
							'8월', '9월', '10월', '11월', '12월' ],
					monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월',
							'7월', '8월', '9월', '10월', '11월', '12월' ],
					dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
					dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
					dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
					showMonthAfterYear : true,
					yearSuffix : '년',
					showButtonPanel : true,
					currentText : '오늘',
					changeMonth : false,
					changeYear : false,
					closeText : '닫기'
				});
	});
</script>
<style>
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

<p>
	생년월일: <input type="text" id="datepicker1"> ~ <input type="text" id="datepicker2">
</p>