<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta name="keywords" content="jQuery Progress, Progress Bar, jqxProgressBar, Loading Bar, Progress Widget" />
<meta name="description" content="The jqxProgressBar widget visually indicates the progress of a lengthy operation" />
<title id='Description'>The jqxProgressBar widget visually indicates the progress of a lengthy operation.</title>
<link rel="stylesheet" href="/js/jqwidgets/styles/jqx.base.css" type="text/css" />
<link rel="stylesheet" href="/js/jqwidgets/styles/jqx.bootstrap.css" type="text/css" />
<script type="text/javascript" src="/js/scripts/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/scripts/demos.js"></script>
<script type="text/javascript" src="/js/jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="/js/jqwidgets/jqxprogressbar.js"></script>
<script type="text/javascript" src="/js/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="/js/jqwidgets/jqxcheckbox.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// Create jqxProgressBar.
		$("#jqxProgressBar").jqxProgressBar({
			width : 150,
			height : 30,
			value : 50,
			theme : 'Bootstrap',
			showText : true
		});
		$("#jqxVerticalProgressBar").jqxProgressBar({
			width : 30,
			height : 250,
			value : 50,
			orientation : 'vertical'
		});
		// Create jqxButton.
		$('#button').jqxButton({});
		// Update ProgressBars.
		$('#button').click(function() {
			var value = $('#ValueInput')[0].value;
			$("#jqxProgressBar").jqxProgressBar({
				value : value
			});
			$("#jqxVerticalProgressBar").jqxProgressBar({
				value : value
			});
		});

		$("#checkbox").jqxCheckBox({});
		$("#customtextcheckbox").jqxCheckBox({});

		$("#checkbox").on('change', function(event) {
			$("#jqxProgressBar").jqxProgressBar({
				showText : event.args.checked
			});
			$("#jqxVerticalProgressBar").jqxProgressBar({
				showText : event.args.checked
			});
		});

		var renderText = function(text) {
			return "<span class='jqx-rc-all' style='background: #ffe8a6; color: #e53d37; font-style: italic;'>" + text + "</span>";
		}

		$("#customtextcheckbox").on('change', function(event) {
			if (event.args.checked) {
				$("#jqxProgressBar").jqxProgressBar({
					renderText : renderText
				});
				$("#jqxVerticalProgressBar").jqxProgressBar({
					renderText : renderText
				});
			} else {
				$("#jqxProgressBar").jqxProgressBar({
					renderText : null
				});
				$("#jqxVerticalProgressBar").jqxProgressBar({
					renderText : null
				});
			}
		});
	});
</script>

</head>
<body class='default'>
	<div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
		<div style='margin-top: 10px;'>Horizontal</div>
		<div style='margin-top: 10px; overflow: hidden;' id='jqxProgressBar'></div>
		<div style='margin-top: 10px;'>Vertical</div>
		<div style='margin-top: 10px; overflow: hidden;' id='jqxVerticalProgressBar'></div>
		<br />
		<div>Enter a value between 0 and 100</div>
		<input id='ValueInput' type='text' style='margin-top: 5px;' />
		<input id='button' type='button' value='Update' style='padding: 3px; margin-top: 5px;' />

		<div id="checkbox" style="margin-top: 20px;">Show Progress Text</div>
		<div id="customtextcheckbox" style="margin-top: 20px;">Custom Progress Text</div>
	</div>
</body>
</html>
