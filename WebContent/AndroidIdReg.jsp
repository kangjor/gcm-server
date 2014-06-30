<%@page import="db.RegDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		// 안드로이드 휴대폰으로 부터 넘어오는 휴대폰 고유ID값을 받아옴
		String regid = request.getParameter("regid");
		String phoneid = request.getParameter("phoneid");
		System.out.println(regid);
		if(regid.equals(null)) {
			regid="1";
		}
		RegDao dao = new RegDao();
		// 데이터 베이스 입력
		dao.insertRegid(regid, phoneid);
	%>
</body>
</html>