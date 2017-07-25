<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles"   uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge"/> -->
        
        <link rel="stylesheet" type="text/css" href="/css/tc.css">
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/ui.css'/>" />
        <link rel="stylesheet" href="/js/jqwidgets/styles/jqx.base.css" type="text/css" />
        
        <!-- jQuery -->
        <script type="text/javascript" src="/js/scripts/jquery-1.11.1.min.js"></script>
        <!--script type="text/javascript" src="/js/scripts/jquery-2.1.4.min.js"></script-->
        <script type="text/javascript" src="/js/scripts/demos.js"></script>
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
        
        <script src="<c:url value='/js/common.js'/>" charset="utf-8"></script>
    </head>
	
	<body class="bg_color">
        <div class="section_wrap" >
    		<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="left"/>
            <tiles:insertAttribute name="body"/>
        </div>
        <!-- section wrap End -->
        <tiles:insertAttribute name="footer" />
    </body>
</html>






