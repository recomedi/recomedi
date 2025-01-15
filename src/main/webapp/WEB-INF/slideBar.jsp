<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가먹는약 한큐에!</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
<script>
	// 메세지
	const msg = "${requestScope.msg}";
	if (msg != null && msg != "") {
	 alert(msg);
	}
</script>
</head>
<body class="flex">
	<div class="fixed sideBar">
		<nav class="flex relative">
			<ul class="sideMenu">
				<c:if test="${empty sessionScope.midx}">
				<li><a href="${pageContext.request.contextPath}/member/memberLogin.do"><img src="${pageContext.request.contextPath}/resources/images/login.png" alt="로그인">로그인</a></li>
				<li><a href="${pageContext.request.contextPath}/member/memberJoin.do"><img src="${pageContext.request.contextPath}/resources/images/join.png" alt="회원가입">회원가입</a></li>
				</c:if>
				
				<li><a href="${pageContext.request.contextPath}/medicine/medicineHashTag.do"><img src="${pageContext.request.contextPath}/resources/images/medicine.png" alt="전체의약품">전체의약품조회</a></li>
				
				<c:if test="${!empty sessionScope.midx}">
				<li><a href="${pageContext.request.contextPath}/member/memberMypage.do"><img src="${pageContext.request.contextPath}/resources/images/editProfile.png" alt="정보수정">정보수정</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/prescription.png" alt="처방받은약">처방받은약</a></li>
				<li><a href="${pageContext.request.contextPath}/scrap/scrapList.do"><img src="${pageContext.request.contextPath}/resources/images/scrap_blank.png" alt="스크랩">스크랩</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="내가문의한글">내가문의한글</a></li>
				</c:if>
				
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/notice.png" alt="공지사항">공지사항</a></li>
				<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/inquiry.png" alt="문의게시판">문의게시판</a></li>
				
				<c:if test="${!empty sessionScope.midx}">
				<li class="logout"><a href="${pageContext.request.contextPath}/member/memberLogout.do"><img src="${pageContext.request.contextPath}/resources/images/logout.png" alt="로그아웃">로그아웃</a></li>
				</c:if>
			</ul>
		</nav>
		<button class="close absolute"><img src="${pageContext.request.contextPath}/resources/images/menuClose.svg" alt="menuClose"></button>
	</div>