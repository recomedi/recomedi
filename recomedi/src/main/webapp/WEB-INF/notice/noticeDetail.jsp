<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<!-- 공지사항을 누르면 공지사항 목록페이지로 -->
<link href="${pageContext.request.contextPath}/resources/css/style2.css"
	rel="stylesheet">
</head>
<body>
	<header>
		<div onclick="location.href='noticeList.do'" style="cursor: pointer;">
			<h2 class="mainTitle">공지사항</h2>
		</div>
	</header>
	<section>
		<h1>${bv.title}</h1>
		<p>${bv.contents}</p>
		
	</section>
<c:if test="${not empty sessionScope.sessionMember and sessionScope.sessionMember.admin eq 'Y'}">
    <div class="btnBox">
        <a class="btn aBtn"
           href="${pageContext.request.contextPath}/notice/noticeModify.do?bidx=${bidx}">수정</a>
        
        <button type="button" class="btn aBtn" onclick="confirmDelete(${bidx})">삭제</button>
    </div>
</c:if>
<script>
function confirmDelete(bidx) {
    if (bidx === undefined || bidx === null) {
        alert('삭제할 게시글의 ID가 유효하지 않습니다.');
        return;  // 함수 종료
    }
    if (confirm('정말 삭제하시겠습니까?')) {
        window.location.href = '${pageContext.request.contextPath}/notice/noticeDelete.do?bidx=' + bidx;  // 삭제 액션으로 리다이렉트
    } else {
        alert('삭제가 취소되었습니다.');
    }
}
model.addAttribute("bidx", board.getBidx());
</script>
	<%-- 디버깅 코드 --%>
	<%-- <c:if test="${not empty sessionScope.sessionMember}"> 
    <p>Session Member ID: ${sessionScope.sessionMember.id}</p>
    <p>Session Member Admin: ${sessionScope.sessionMember.admin}</p>
    <p>세션에서 midx: ${sessionScope.midx}</p>
    <p>Debug: bidx = ${bidx}</p>
	</c:if>  --%>
	
	<!-- 사용자 버튼 -->
	<div class="btnBox">
	    <a class="btn aBtn"
	       href="${pageContext.request.contextPath}/notice/noticeList.do">목록</a>
	</div>
	<hr>
</body>
</html>