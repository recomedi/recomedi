<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
<link href="${pageContext.request.contextPath}/resources/css/style2.css"
	rel="stylesheet">
</head>

<script>
	
</script>

<body>
	<header>
		<!-- 헤더의 공지사항 클릭시 목록으로 이동 -->
		<div onclick="location.href='noticeList.do'" style="cursor: pointer;">
			<h2 class="mainTitle">공지사항</h2>
		</div>
		<!-- AXAJ를 사용해서 페이지에 띄울 것을 만들어야함 -->
		<form class="search" name="frm"
			action="${pageContext.request.contextPath}/notice/noticeList.do"
			method="get">
			<input type="text" name="title" value="${param.title}"
				placeholder="제목을 입력하세요"> <input type="hidden" name="page"
				value="1">
			<!-- value="${param.page != null ? param.page : 1}" -->
			<button type="submit" class="btn">검색</button>
		</form>
	</header>

	<section>
		<!-- 검색 결과가 없을 경우 메시지 표시 -->
		<c:if test="${not empty noResultMessage}">
			<div class="no-result-message">
				<p>${noResultMessage}</p>
			</div>
		</c:if>


		<table class="listTable">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>조회수</th>
				<th>등록일</th>
			</tr>

			<c:forEach items="${blist}" var="bv" varStatus="status">
				<tr>
					<!-- 게시글 번호: 최신순으로 계산 -->
					<td>${pm.totalCount - ((status.index) + (pm.scri.page - 1) * pm.scri.perPageNum)}</td>

					<!-- 게시글 제목: 클릭 가능하도록 링크 추가 -->
					<td class="title"><a
						href="${pageContext.request.contextPath}/notice/noticeDetail.do?bidx=${bv.bidx}">
							${bv.title} </a></td>


					<!-- 게시글 조회 -->
					<td>${bv.view}</td>
					<!-- 작성일 -->
					<td>${bv.date}</td>
				</tr>


			</c:forEach>
		</table>

		<div class="btnBox">
			<a class="btn aBtn"
				href="${pageContext.request.contextPath}/notice/noticeWrite.do">글쓰기</a>
		</div>

		<c:set var="queryParam"
			value="title=${pm.scri.title}&searchType=${pm.scri.searchType}"></c:set>
		<!-- 검색어 유지 -->

		<div class="page">
			<ul>
				<!-- 이전 페이지 링크 -->
				<c:if test="${pm.prev == true}">
					<li><a
						href="${pageContext.request.contextPath}/notice/noticeList.do?page=${pm.startPage-1}&title=${param.title}&searchKeyword=${param.searchKeyword}">◀</a></li>
				</c:if>

				<!-- 페이지 번호 링크 -->
				<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}"
					step="1">
					<li class="<c:if test='${i == pm.scri.page}'>on</c:if>"><a
						href="${pageContext.request.contextPath}/notice/noticeList.do?page=${i}&title=${param.title}&searchKeyword=${param.searchKeyword}">
							<span style="font-size: 20px;">${i}</span>
					</a></li>
				</c:forEach>

				<!-- 다음 페이지 링크 -->
				<c:if test="${pm.next && pm.endPage > 0}">
					<li><a
						href="${pageContext.request.contextPath}/notice/noticeList.do?page=${pm.endPage+1}&title=${param.title}&searchKeyword=${param.searchKeyword}">▶</a></li>
				</c:if>
			</ul>
		</div>
	</section>

</body>
</html>