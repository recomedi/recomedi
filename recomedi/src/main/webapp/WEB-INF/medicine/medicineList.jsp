<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.recomedi.myapp.domain.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>의약품 리스트</title>

<script
  src="https://code.jquery.com/jquery-3.7.1.slim.min.js"
  integrity="sha256-kmHvs0B+OpCW5GVHUNjv9rOmY0IvSIRcf7zGUDTDQM8="
  crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

</head>
<body>
	<div class="fixed sideBar">
		<nav class="flex relative">
			<ul class="sideMenu">
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/login.png" alt="로그인">로그인</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/join.png" alt="회원가입"></a>회원가입</li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/editProfile.png"
						alt="정보수정">정보수정</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/prescription.png"
						alt="처방받은약">처방받은약</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/scrap_blank.png"
						alt="스크랩">스크랩</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="내가문의한글">내가문의한글</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/medicine.png" alt="전체의약품">전체의약품조회</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/notice.png" alt="공지사항">공지사항</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/inquiry.png" alt="문의게시판">문의게시판</a></li>
				<li class="logout"><a href="#"><img
						src="${pageContext.request.contextPath}/resources/images/logout.png" alt="로그아웃">로그아웃</a></li>
			</ul>
		</nav>
		<button class="close absolute">
			<img src="${pageContext.request.contextPath}/resources/images/menuClose.svg" alt="menuClose">
		</button>
	</div>

	<header class="relative center" id="hd">
		<a href="#" class="absolute logo"><img src="${pageContext.request.contextPath}/resources/images/logo.png"
			alt="logo"></a>
		<h2 class="mainTitle relative bold">"부로펜" 검색결과</h2>
		<div class="absolute menu flex">
			<a href="#" class="relative alarm confirmation"><img
				src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="alarm"></a>
			<button class="btnMenu flex">
				<p class="first"></p>
				<p class="second"></p>
				<p class="third"></p>
			</button>
		</div>
	</header>

	<div class="wrapper">
		<form class="search flex">
			<input type="text" placeholder="제품명을 입력해주세요.">
			<button class="btn">검색</button>
		</form>

		<section class="shadow searchResultArea">
			<table class="listTable">
				<thead class="relative">
					<tr>
						<th>번호</th>
						<th>제품명</th>
						<th>제조사</th>
					</tr>
				</thead>
				<tbody class="bold">
					<c:forEach items="${mlist}" var="mdv" varStatus="status">
						<tr>
							<td>${mdv.medidx}</td>
							<td class="tbTitle"><a
								href="${pageContext.request.contextPath}/medicine/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName}</a></td>
							<td>${mdv.entpName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</section>

		<div class="page">
			<ul class="flex">
				<c:if test="${pm.prev == true}">
					<li class="center"><a
						href="${pageContext.request.contextPath}/medicine/medicineList.do?page=${pm.startPage-1}&${queryParam}">◀</a></li>
				</c:if>

				<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}" step="1">
					<li <c:if test="${i==pm.scri.page}"> class='on' </c:if>><a
						href="${pageContext.request.contextPath}/medicine/medicineList.do?page=${i}&${queryParam}">
							<span style="font-size: 20px;">${i}</span>
					</a></li>
				</c:forEach>

				<c:if test="${pm.next == true}">
					<li class="center"><a href="${pageContext.request.contextPath}/medicine/medicineList.do?page=${pm.endPage+1}&${queryParam}">▶</a></li>
				</c:if>
				
			</ul>
		</div>
	</div>

	<footer id="ft">
		<ul class="madeBy flex">
			<li>Recomedi <a href="https://github.com/recomedi/recomedi.git"
				target="_blank">Github</a></li>
			<li>민들레 <a href="https://velog.io/@ktiun9630" target="_blank">velog</a>
				| <a href="https://github.com/Chan01116" target="_blank">Github</a></li>
			<li>데프콘 <a href="https://velog.io/@rivae_108" target="_blank">velog</a>
				| <a href="https://github.com/Rivea108" target="_blank">Github</a></li>
			<li>지렁이 <a href="https://velog.io/@biso15" target="_blank">velog</a>
				| <a href="https://github.com/biso15" target="_blank">Github</a></li>
			<li>구리 <a href="https://velog.io/@guri670" target="_blank">velog</a>
				| <a href="https://github.com/guri670" target="_blank">Github</a></li>
		</ul>
		<p class="copyright">Copyright 2024. Recomedi. All Rights
			reserved.</p>
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