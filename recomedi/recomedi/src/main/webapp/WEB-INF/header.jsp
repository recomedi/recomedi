<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가먹는약 한큐에!</title>
<script src="https://code.jquery.com/jquery-3.7.1.slim.min.js" integrity="sha256-kmHvs0B+OpCW5GVHUNjv9rOmY0IvSIRcf7zGUDTDQM8=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body class="main flex">	
	<div class="fixed sideBar">
		<nav class="flex relative">
			<ul class="sideMenu">
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/login.png" alt="로그인">로그인</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/join.png" alt="회원가입">회원가입</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/editProfile.png" alt="정보수정">정보수정</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/prescription.png" alt="처방받은약">처방받은약</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/scrap_blank.png" alt="스크랩">스크랩</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="내가문의한글">내가문의한글</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/medicine.png" alt="전체의약품">전체의약품조회</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/notice.png" alt="공지사항">공지사항</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/inquiry.png" alt="문의게시판">문의게시판</a></li>
				<li class="logout"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/logout.png" alt="로그아웃">로그아웃</a></li>
			</ul>
		</nav>
		<button class="close absolute"><img src="${pageContext.request.contextPath}/resources/images/menuClose.svg" alt="menuClose"></button>
	</div>
	
	<header class="relative center" id="hd">
		<a href="#" class="logo"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo"></a>
		<div class="absolute menu flex">
			<a href="#" class="relative alarm confirmation"><img src="${pageContext.request.contextPath}/resources/images/alarm_w.png" alt="alarm"></a>
			<button class="btnMenu flex"><p class="first"></p><p class="second"></p><p class="third"></p></button>
		</div>
	</header>