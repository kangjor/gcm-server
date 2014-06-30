<%@page import="db.RegDao"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.android.gcm.server.*" %>
<%
 request.setCharacterEncoding("euc-kr");

 String ab = request.getParameter("data");
 String gcmdata = URLEncoder.encode(ab, "EUC-KR");
 ArrayList<String> regid = new ArrayList<String>(); //메시지를 보낼 대상들
 String MESSAGE_ID = String.valueOf(Math.random() % 100 + 1); //메시지 고유 ID
 boolean SHOW_ON_IDLE = true; //기기가 활성화 상태일때 보여줄것인지
 int LIVE_TIME = 1; //기기가 비활성화 상태일때 GCM가 메시지를 유효화하는 시간
 int RETRY = 2; //메시지 전송실패시 재시도 횟수
 
 //String simpleApiKey = "AIzaSyBKnh9tZT5tUpOxmpVbpcNx9aufEBaVxEg"; 
 String simpleApiKey = "AIzaSyCZqDbGmdTo4s2YZGerNBTaGwMDrkC7HWo";//가이드 1때 받은 키
 String gcmURL = "https://android.googleapis.com/gcm/send";  //푸쉬를 요청할 구글 주소
 try {
  //데이터베이스 연결 해서 모든 regid 값을 얻어오는 메소드 호출
  RegDao dao = new RegDao();
  regid = dao.getAllRegid();
  
  Sender sender = new Sender(simpleApiKey);
  Message message = new Message.Builder()
  .collapseKey(MESSAGE_ID)
  .delayWhileIdle(SHOW_ON_IDLE)
  .timeToLive(LIVE_TIME)
  .addData("test",gcmdata)
  .build();
  MulticastResult result = sender.send(message,regid,RETRY);
 }catch (Exception e) {
	 e.printStackTrace();
 }
 response.sendRedirect("Insert.jsp");
%>