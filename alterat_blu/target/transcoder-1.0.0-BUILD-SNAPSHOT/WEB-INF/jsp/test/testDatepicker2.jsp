<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>jQuery UI Datepicker - Default functionality</title>
<!-- 
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
 -->
<link rel="stylesheet" href="jquery-ui-1.11.4/jquery-ui.css">
<script src="jquery-ui-1.11.4/external/jquery/jquery.js"></script>
<script src="jquery-ui-1.11.4/jquery-ui.min.js"></script>
<script src="jquery-ui-1.11.4/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker(
				{
                    dateFormat : 'yy-mm-dd',
                    showMonthAfterYear : true,
                    showButtonPanel : true,
                    currentText : 'Today',
                    changeMonth : false,
                    changeYear : false,
                    closeText : 'Close'
                }
		);
	});
</script>
</head>
<body>
	<p>
		Date: <input type="text" id="datepicker">
	</p>
</body>
</html>