<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link href="${pageContext.request.contextPath}/resources/css/style2.css"
	rel="stylesheet">
<script>
	function check() {
		// 유효성 검사하기
		let fm = document.frm;

		if (fm.title.value == "") {
			alert("제목을 입력해주세요");
			fm.title.focus();
			return;
		} else if (fm.content.value == "") {
			alert("내용을 입력해주세요");
			fm.content.focus();
			return;
		}

		let ans = confirm("저장하시겠습니까?");

		if (ans == true) {
			fm.action = "/notice/noticeModifyAction.do";
			fm.method = "post";
			fm.submit();
		}

		return;
	}
</script>
</head>
<body>
	<header>
		<h2 class="mainTitle">글수정</h2>
	</header>

	<!-- bidx 값이 존재하면 int로 변환 -->
	<c:if test="${not empty bidx}">
		<c:set var="bidx" value="${bidx * 1}" />
	</c:if>
	<c:if test="${empty bidx}">
		<c:set var="bidx" value="0" />
	</c:if>

	<!-- bidx와 msg를 flashScope에서 출력 -->
	<c:if test="${not empty flashScope.bidx}">
		<p>BIDX: ${flashScope.bidx}</p>
	</c:if>

	<c:if test="${not empty flashScope.msg}">
		<p>메시지: ${flashScope.msg}</p>
	</c:if>

	<form name="frm" method="post" action="/notice/noticeModifyAction.do">
		<table class="writeTable">
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" value="${bv.title}"></td>
				<!-- 수정된 부분 -->
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="contents" rows="6">${bv.contents}</textarea></td>
				<!-- 수정된 부분 -->
			</tr>
		</table>

		<!-- bidx 값 출력 -->
		<%-- <c:out value="${bv.bidx}" default="bidx 값 없음" /> --%>
		<!-- 수정된 부분 -->

		<!-- bidx 값을 hidden 필드에 넣기 -->
		<input type="hidden" name="bidx" value="${bv.bidx}">
		<!-- 수정된 부분 -->

		<div class="btnBox">
			<button type="submit" class="btn" onclick="check();">저장</button>
			<a class="btn aBtn" href="/notice/noticeDetail.do?bidx=${bv.bidx}">취소</a>
		</div>
	</form>



</body>
</html>