<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<%@ include file="/WEB-INF/slideBar.jsp" %>
	
	<header class="relative center" id="hd">
		<a href="${pageContext.request.contextPath}/" class="absolute logo"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo"></a>
		<h2 class="mainTitle relative bold">"${requestScope.keyword}" 검색결과</h2>
		<div class="absolute menu flex">
			<a href="#" class="relative alarm confirmation"><img src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="alarm"></a>
			<button class="btnMenu flex"><p class="first"></p><p class="second"></p><p class="third"></p></button>
		</div>
	</header>
	
 	<div class="wrapper board">
		<form class="search flex">		
			<select class="select2 none" name="searchType">
				<option value="itemName" selected>제품명</option>
			</select>
			<input type="text" name="keyword" placeholder="제품명을 입력해주세요.">
			<button class="btn">검색</button>
		</form>

		<section class="shadow searchResultArea">
			<table class="listTable">
				<colgroup>
					<col width="10%">
					<col width="70%">
					<col width="20%">
				</colgroup>
				<thead class="relative">
					<tr>
						<th>번호</th>
						<th>제품명</th>
						<th>제조사</th>
					</tr>
				</thead>
				<tbody class="bold">
					<c:forEach items="${requestScope.mlist}" var="mdv" varStatus="status">
					<tr>
						<td>${requestScope.pm.totalCount - (requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum - status.index}</td>
						<td class="tbTitle"><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do">${mdv.itemName}</a></td>
						<td>${mdv.entpName}</td>
					</tr>
					</c:forEach>
					
					<c:if test="${requestScope.pm.totalCount == 0}">
					<tr>
						<td colspan="3">제품이 없습니다.</td>
					</tr>
					</c:if>
				</tbody>
			</table>
		</section>
			
		<div class="page">
			<ul class="flex">
			<c:set var="queryParam" value="keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}"></c:set>
				<c:if test="${requestScope.pm.prev == true}">
				<li class="center"><a href="${pageContext.request.contextPath}/medicine/medicineList.do?page=${requestScope.pm.startPage - 1}&${queryParam}">◀</a></li>
				</c:if>
				
				<c:forEach var="i" begin="${requestScope.pm.startPage}" end="${requestScope.pm.endPage}" step="1">
				<li class="center <c:if test="${i == requestScope.pm.scri.page}"> on</c:if>"><a href="${pageContext.request.contextPath}/medicine/medicineList.do?page=${i}&${queryParam}">${i}</a></li>
				</c:forEach>
				
				<c:if test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
				<li class="center"><a href="${pageContext.request.contextPath}/medicine/medicineList.do?page=${requestScope.pm.endPage + 1}&${queryParam}">▶</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	
    <script>
	// select
	$(document).ready(function() {
	  $('.select2').select2 ({ 
	  	width : "120px"
	  })
	});
	</script>
	
    <%@ include file="/WEB-INF/footer.jsp" %>