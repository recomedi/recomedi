<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.recomedi.myapp.domain.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body class="flex">
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
		<h2 class="mainTitle relative bold">전체 의약품 조회</h2>
		<div class="absolute menu flex">
			<a href="#" class="relative alarm confirmation"><img src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="alarm"></a>
			<button class="btnMenu flex"><p class="first"></p><p class="second"></p><p class="third"></p></button>
		</div>
	</header>

	<div class="wrapper">
		<section class="medicineAll flex">
			<div class="medicineItem section shadow">
				<h3 class="regular title">#감기</h3>
				<ul>
				<c:forEach items="${mlist}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#소화제</h3>
				<ul>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
				</ul>
				<div class="btnBox right">
					<a href="#" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#해열제</h3>
				<ul>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
				</ul>
				<div class="btnBox right">
					<a href="#" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#진통제</h3>
				<ul>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
				</ul>
				<div class="btnBox right">
					<a href="#" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#피부약</h3>
				<ul>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
				</ul>
				<div class="btnBox right">
					<a href="#" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#제산제</h3>
				<ul>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
				</ul>
				<div class="btnBox right">
					<a href="#" class="btn">더보기</a>
				</div>
			</div>
						<div class="medicineItem section shadow">
				<h3 class="regular title">#피로회복제</h3>
				<ul>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
				</ul>
				<div class="btnBox right">
					<a href="#" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#구충제</h3>
				<ul>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
				</ul>
				<div class="btnBox right">
					<a href="#" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#수면유도제</h3>
				<ul>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
					<li><a href="#">약설명</a></li>
				</ul>
				<div class="btnBox right">
					<a href="#" class="btn">더보기</a>
				</div>
			</div>
		</section>
			
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

	<script>
		const btnMenu = document.querySelector("#hd .btnMenu");
		const sideBar = document.querySelector(".sideBar");
		const btnMenuClose = document.querySelector(".sideBar .close");

		function sideBarOpen() {
			sideBar.classList.add("open");
			btnMenu.classList.add("none");
		}
		btnMenu.addEventListener("click", sideBarOpen);
		
		function sideBarClose() {
			sideBar.classList.remove("open");
			btnMenu.classList.remove("none");
		}
		btnMenuClose.addEventListener("click", sideBarClose);

	</script>
</body>
</html>