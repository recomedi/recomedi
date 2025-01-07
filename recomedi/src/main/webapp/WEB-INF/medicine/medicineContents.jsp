<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.recomedi.myapp.domain.*"%>
<%
MedicineVo mdv = (MedicineVo) request.getAttribute("mdv");

int medidx = 0;

if (session.getAttribute("medidx") != null) {
	medidx = Integer.parseInt(session.getAttribute("medidx").toString());
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>의약품 상세보기</title>

<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

</head>
<body>
	<div class="fixed sideBar">
		<nav class="flex relative">
			<ul class="sideMenu">
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/login.png" alt="로그인">로그인</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/join.png" alt="회원가입"></a>회원가입</li>
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
		<a href="#" class="absolute logo"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo"></a>
		<h2 class="mainTitle relative bold">약품정보</h2>
		<div class="absolute menu flex">
			<a href="#" class="relative alarm confirmation"><img src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="alarm"></a>
			<button class="btnMenu flex"><p class="first"></p><p class="second"></p><p class="third"></p></button>
		</div>
	</header>

	<div class="wrapper">
		<section class="shadow detailContents">
			<h2 class="contentTitle center shadow regular">${mdv.itemName} - ${mdv.entpName}</h2>
			<p class="hashtag bold">#감기약</p>
			<div class="content shadow">
				${mdv.efcyQesitm}
			</div>
		</section>

		<div class="page contentPage">
			<ul class="flex">
				<li><a href="${pageContext.request.contextPath}/medicine/medicineContents.do?medidx=${mdv.medidx-1}">◀</a></li>
				<li><a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">목록가기</a></li>
				<li><a href="${pageContext.request.contextPath}/medicine/medicineContents.do?medidx=${mdv.medidx+1}">▶</a></li>
			</ul>
		</div>
	</div>

	<footer id="ft">
		<ul class="madeBy flex">
			<li>Recomedi <a href="https://github.com/recomedi/recomedi.git" target="_blank">Github</a></li>
			<li>민들레 <a href="https://velog.io/@ktiun9630" target="_blank">velog</a> | <a href="https://github.com/Chan01116" target="_blank">Github</a></li>
			<li>데프콘 <a href="https://velog.io/@rivae_108" target="_blank">velog</a> | <a href="https://github.com/Rivea108" target="_blank">Github</a></li>
			<li>지렁이 <a href="https://velog.io/@biso15" target="_blank">velog</a> | <a href="https://github.com/biso15" target="_blank">Github</a></li>
			<li>구리 <a href="https://velog.io/@guri670" target="_blank">velog</a> | <a href="https://github.com/guri670" target="_blank">Github</a></li>
		</ul>
		<p class="copyright">Copyright 2024. Recomedi. All Rights reserved.</p>
	</footer>
</body>
</html>