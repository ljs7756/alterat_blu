<%@ page language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<%@ page import="java.net.InetAddress"%>

<%
	String locale = response.getLocale().toString();
	String dir    = "ltr";

	if ("ar".equals(locale)) {
		dir = "rtl";
	}

	InetAddress Address = InetAddress.getLocalHost();
	String IP = Address.getHostAddress();

	IP = IP.substring(IP.length()-1);
    
    response.setHeader("Content-Disposition", "attachment; filename=election.xls"); 
    response.setHeader("Content-Description", "JSP Generated Data"); 
    response.setContentType("application/vnd.ms-excel");    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="<%=locale%>" dir="<%=dir%>" xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> -->
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge" /> -->
		
		<title><tiles:getAsString name="title" /></title>
	</head>
	
	<body>
		<div id="wrapper">
			<hr />
			<!-- S T A R T :: bodyArea -->
			<!-- container -->
		    <div id="content">
		        <div id="contents_area">
					<tiles:insertAttribute name="body"/>
			    </div>
			</div>
			<!--// E N D :: bodyArea -->
			<hr />
			
		</div>
	
	</body>
</html>





