// JavaScript Document

function clickBtn(id){
	$('#'+id).show();
}

function closeBtn(id){
	$('#'+id).hide();
}


	
document.write('<div class="left_no_top">');
document.write('	<div class="top_leftno">');
document.write('		<div class="logo_area"><img src="/images/logo.png"></div>');
document.write('		<div class="right_top">');
document.write('			<ul>');

var current_url = window.location.href;
if (current_url.indexOf("/nps/main.do") != -1) {
	document.write('				<li class="menu_on"><a href="/nps/main.do">파일변환</a></li>');
} else {
	document.write('				<li><a href="/nps/main.do">파일변환</a></li>');
}
if (current_url.indexOf("/selectProfile.do") != -1) {
	document.write('				<li class="menu_on"><a href="/selectProfile.do">프로파일 관리</a></li>');
} else {
	document.write('				<li><a href="/selectProfile.do">프로파일 관리</a></li>');
}
if (current_url.indexOf("/selectWatchfolder.do") != -1) {
	document.write('				<li class="menu_on"><a href="/selectWatchfolder.do">와치 폴더</a></li>');
} else {
	document.write('				<li><a href="/selectWatchfolder.do">와치 폴더</a></li>');
}
if (current_url.indexOf("/selectMonitoring.do") != -1) {
	document.write('				<li class="menu_on"><a href="/selectMonitoring.do?chk_all=Y&chk_status_all=Y&check_yn=Y">모니터링</a></li>');
} else {
	document.write('				<li><a href="/selectMonitoring.do?chk_all=Y&chk_status_all=Y&check_yn=Y">모니터링</a></li>');
}
document.write('			</ul>');
               
document.write('			<div class="top_btns">');
//document.write('				<p id="target" onclick="javascript:clickBtn(\'pop1\');"><a href="#">설정</a></p>');
//document.write('				<p id="target" onclick="javascript:clickBtn(\'pop2\');"><a href="#">정보</a></p>');
document.write('				<p id="target" onclick="javascript:clickBtn(\'pop3\');"><a href="#">소개</a></p>');
document.write('			</div>');
               
document.write('		</div>');
document.write('	</div>');
document.write('</div>');

document.write('<div id="pop1" class="pop_system" style="display:none;">');
document.write('	<div class="pop_wrap01">');
document.write('		<p class="pop_tit">시스템 설정</p>');
document.write('        <div class="pop_con">');
document.write('        	<p><strong>1.NPS 소재 등록 후 바로 소스폴더 / 타겟 폴더의 소재 삭제</strong></p>');
document.write('        	<ul style="margin-top:20px; font-weight:bold;">');
document.write('            	<li style="padding:0 0 10px 0;"><input name="" type="checkbox" value=""> 등록 후 바로 삭제</li>');
document.write('                <li style="padding:0 0 10px 0;"><input name="" type="checkbox" value=""> 등록 후 <input type="text" size="3" style="padding:3px; text-align:center;"> 일 이후 삭제</li>');
document.write('            </ul>');
document.write('        </div>');
document.write('        <div class="pop_btns">');
document.write('        	<span style="background:#01b1f0; width:95px; margin-right:10px;">기본값</span>');
document.write('            <span style="background:#01b1f0; width:95px; margin-right:10px;">수정</span>');
document.write('            <span style="background:#01b1f0; width:95px; background:#666;" id="close1" onclick="javascript:closeBtn(\'pop1\');">닫기</span>');
document.write('        </div>');
document.write('	</div>');
document.write('</div>');

document.write('<div id="pop2" class="pop_info" style="display:none;">');
document.write('	<div class="pop_wrap01">');
document.write('		<p class="pop_tit">시스템 정보</p>');
document.write('        <div class="pop_con">');
document.write('        	<div style="padding:20px;">');
document.write('            	<strong style="font-size:16px;">1. 버전정보</strong> <br>');
document.write('            	<b style="font-size:14px; margin:0 0 10px 20px;">현재 버전정보 : 1.0.1</b><br>');
document.write('				<p style="padding:10px 0 5px 20px;"><b style="margin-left:20px;">Ver 1.0.1 수정사항</b><br>');
document.write('				<span style="margin-left:40px;"> - XXX  수정</span><br><span style="margin-left:40px;"> - XXXX 기능개선 수정</span></p>');
document.write('            	<p style="padding:10px 0 5px 20px;"><b style="margin-left:20px;">Ver 1.0.0 수정사항</b><br>');
document.write('            	<span style="margin-left:40px;">- XXX  수정</span><br><span style="margin-left:40px;">- XXXX 기능개선 수정</span><br>');
document.write('            </div>');
document.write('        	<div style="padding:20px;">');
document.write('            	<strong style="font-size:16px;">2. 시스템 정보</strong> <br>');
document.write('				<p style="padding-left:40px;"> OS : MAC OS X 10.7<br>CPU : xxxx <br>Memory : xxxx<br>HDD : xxx GB</p>');
document.write('            </div>');
document.write('        </div>');
document.write('        <div class="pop_btns">');
document.write('            <span style="background:#01b1f0; width:95px;" id="close2" onclick="javascript:closeBtn(\'pop2\');">닫기</span>');
document.write('        </div>');
document.write('	</div>');
document.write('</div>');

document.write('<div id="pop3" class="pop_intro" style="display:none;">');
document.write('	<div class="pop_wrap01">');
document.write('        <img id="close3" onclick="javascript:closeBtn(\'pop3\');" style="cursor:pointer;" src="/images/intro_pop.jpg">');
document.write('	</div>');
document.write('</div>');
