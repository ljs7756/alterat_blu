<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" href="css/tc.css">
<%@ include file="/WEB-INF/include/include-header.jspf"%>
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
						<li><a href="/selectProfile.do?common=${list.common}">${list.common}</a></li>
				</c:forEach>
				</ul>
			</div>
			<div class="right_wrap">
				<div class="right_con">
					<table>
						<col width="170">
						<col width="170">
						<col width="100">
						<col width="900">
						<tr>
							<th class="tl">채널 선택</th>
							<td></td>
							<td>
								<input name="common" type="text" class="style_input">
							</td>
						</tr>
						<tr>
							<th class="tl">확장자</th>
							<td></td>
							<td>
								<input name="worker" type="text" class="style_input">
							</td>
						</tr>
						<tr>
							<th class="tl">비트레이트</th>
							<td></td>
							<td>
								<input name="bitrate" type="text" class="style_input">
							</td>
						</tr>
						<tr>
							<th class="tl">프로파일 이름</th>
							<td></td>
							<td>
								<input name="p_name" type="text" class="style_input">
							</td>
						</tr>
						<tr>
							<th rowspan="5" class="tl">공통</th>
							<td>Enable</td>
							<td>
								<input name="enable_yn" type="checkbox" class="chk_sty" <c:if test="${map.enable_yn == 'Y'}">checked</c:if>>
							</td>
						</tr>
						<tr>
							<td>비디오</td>
							<td>
								<textarea rows="5" name="video_option" class="style_input01" id="id_video_option">${map.video_option}</textarea>
								<div class="btn_add" style="margin:10px 10px 10px 0;"><a href="javascript:fn_video_default('${worker}');">Defauilt</a></div>
							</td>
						</tr>
						<tr>
							<td>오디오</td>
							<td>
								<textarea rows="3" name="audio_option" class="style_input01" id="id_audio_option"></textarea>
								<div class="btn_add" style="margin:10px 10px 10px 0;"><a href="javascript:fn_audio_default('${worker}');">Defauilt</a></div>
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
			$("#list").on("click", function(e) {
				e.preventDefault();
				fn_openBoardList();
			});

			$("#write").on("click", function(e) {
				e.preventDefault();
				fn_insertProfile();
			});
		});

		function fn_openBoardList() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/insertProfile.do'/>");
			comSubmit.submit();
		}

		function fn_insertProfile() {
			var comSubmit = new ComSubmit("frm");
			comSubmit.setUrl("<c:url value='/insertProfile.do' />");
			comSubmit.addParam("video_enable", "on") // DAO 에서 'Y'로 바꿈
			comSubmit.addParam("audio_enable", "on") // DAO 에서 'Y'로 바꿈 
			comSubmit.submit();
		}

		function fn_video_default(worker) {
			if (worker == 'mpeg2video') {
				document.getElementById('id_video_option').value = '-r 29.97 -f mxf -vcodec mpeg2video -flags +ilme+ildct -qscale:v 2 -g 15 -keyint_min 15 -s 1920x1080 -pix_fmt yuv422p -b:v 50000000 -minrate 50000000 -maxrate 50000000';
			} else if (worker == 'mp4') {
				document.getElementById('id_video_option').value = '-f mp4 -vcodec libx264 -s 640x360 -pix_fmt yuv422p -b:v 1000000';
			}
		}

		function fn_audio_default(worker) {
			if (worker == 'mpeg2video') {
				document.getElementById('id_audio_option').value = '-acodec pcm_s24le -ar 48000 -map 0:0 -map 0:1 -map 0:1 -map 0:1 -map 0:1 -map_channel 0.1.0:0.1 -map_channel 0.1.0:0.2 -map_channel 0.1.0:0.3 -map_channel 0.1.0:0.4 -map_channel 0.1.0:0.5';
			} else if (worker == 'mp4') {
				document.getElementById('id_audio_option').value = '-acodec aac -strict -2 -ar 44100 -b:a 125k';
			}
		}
	</script>
</body>
</html>
