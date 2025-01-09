<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>스프링 학습하기</title>
</head>



<body>
	<c:if test="${!empty sessionScope.midx}">
${memberName} &nbsp; 
<a href="${pageContext.request.contextPath}/member/memberLogout.do">로그아웃</a>
	</c:if>
	<!-- 로그아웃 -->

	<br>
	<a href="${pageContext.request.contextPath}/member/memberJoin.do">member
		회원가입 페이지</a>
	<br>
	<a href="${pageContext.request.contextPath}/member/memberLogin.do">member
		로그인 페이지</a>
	<br>
	<a href="${pageContext.request.contextPath}/member/memberMypage.do">member
		마이페이지 페이지</a>
	<br>
	<a href="${pageContext.request.contextPath}/notice/noticeList.do">notice
		목록 페이지</a>
	<br>
	<a href="${pageContext.request.contextPath}/notice/noticeWrite.do">notice
		작성 페이지</a>
	<br>
	<a href="${pageContext.request.contextPath}/notice/noticeDetail.do">notice
		상세 페이지</a>
	<br>
	<a href="${pageContext.request.contextPath}/notice/noticeModify.do">notice
		수정 페이지</a>
	<br>
	<a href="${pageContext.request.contextPath}/notice/noticeDelete.do">notice
		삭제 페이지</a>
	<br>
</body>

<!-- <script>
	location.href = "${pageContext.request.contextPath}/main.do";
</script> -->

</html>
