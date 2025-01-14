<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<%@ include file="/WEB-INF/slideBar.jsp" %>
	
	<header class="relative center" id="hd">
		<a href="${pageContext.request.contextPath}/" class="absolute logo"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo"></a>
		<h2 class="mainTitle relative bold">스크랩</h2>
		<div class="absolute menu flex">
			<a href="#" class="relative alarm confirmation"><img src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="alarm"></a>
			<button class="btnMenu flex"><p class="first"></p><p class="second"></p><p class="third"></p></button>
		</div>
	</header>	
 
 	<div class="wrapper board">
		<form class="search flex">
			<select name="searchType" class="select2">
				<option value="itemName" selected>제품명</option>
				<option value="efcyQesitm">효능·효과</option>
			</select>
			<input type="text" placeholder="제품명을 입력해주세요." name="keyword">
			<button class="btn">검색</button>
		</form>

		<section class="shadow searchResultArea relative">
			<form class="search flex" name="frm">
				<input type="hidden" class="checkArrInput" name="checkArr">
				<table class="listTable">
					<colgroup>
						<col width="5%">
						<col width="10%">
						<col width="35%">
						<col width="35%">
						<col width="15%">
					</colgroup>
					<thead class="relative">
						<tr>
							<th><input type="checkbox" id="checkAll"></th>
							<th>번호</th>
							<th>의약품명</th>
							<th>효능·효과</th>
							<th>등록일</th>
						</tr>
					</thead>
					<tbody class="bold">
						<c:forEach items="${requestScope.slist}" var="sd" varStatus="status">
						<tr>
							<td><input type="checkbox" value="${sd.sidx}" class="check"></td>
							<td>${requestScope.pm.totalCount - (requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum - status.index}</td>
							<td class="tbTitle"><a href="${pageContext.request.contextPath}/medicine/${sd.itemSeq}/medicineContents.do">${sd.itemName}</a></td>
							<td>${sd.efcyQesitm}</td>
							<td>
							<c:choose> 
								<c:when test="${!empty sd.modify}">
									${sd.modify}
								</c:when>
								<c:otherwise>
									${fn:substringBefore(sd.date, ' ')}
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="btnBox absoluteBtnBox absolute">
					<button class="btn" type="button" id="scrapDelete">삭제</button>
				</div>
			</form>
		</section>
			
		<div class="page">
			<ul class="flex">
			<c:set var="queryParam" value="keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}"></c:set>
				<c:if test="${requestScope.pm.prev == true}">
				<li class="center"><a href="${pageContext.request.contextPath}/scrap/scrapList.do?page=${requestScope.pm.startPage - 1}&${queryParam}">◀</a></li>
				</c:if>
				
				<c:forEach var="i" begin="${requestScope.pm.startPage}" end="${requestScope.pm.endPage}" step="1">
				<li class="center <c:if test="${i == requestScope.pm.scri.page}"> on</c:if>"><a href="${pageContext.request.contextPath}/scrap/scrapList.do?page=${i}&${queryParam}">${i}</a></li>
				</c:forEach>
				
				<c:if test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
				<li class="center"><a href="${pageContext.request.contextPath}/scrap/scrapList.do?page=${requestScope.pm.endPage + 1}&${queryParam}">▶</a></li>
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
	
	// 스크랩 삭제
	const delBtn = document.querySelector("#scrapDelete");
	
	function scrapDelete() {

		const chekedScrap = document.querySelectorAll(".check:checked");
		const allScrap = document.querySelectorAll(".check");
		
		if(chekedScrap.length > 0) {
			
	 		let ans = confirm("삭제하시겠습니까?");
	 		
	 		if (ans == true) {
	 			
				const checkArrInput = document.querySelector(".checkArrInput");
				const chekedArry = [];
				
				for(let i = 0; i < chekedScrap.length; i++) {
					chekedArry.push(chekedScrap[i].value);
				}
				
				const checkArr = chekedArry.join(",");
				
				let page = "";
				if(chekedScrap.length == allScrap.length) {
					page = "${requestScope.pm.scri.page - 1}";
				} else {
					page = "${requestScope.pm.scri.page}";
				}
				
				$.ajax({
					type: "get",
					url: "${pageContext.request.contextPath}/scrap/scrapDeleteAction.do",
					dataType: "json",
					data: {"checkArr": checkArr},
					success: function(result) {
						alert("삭제가 완료되었습니다.");
						location.href = path = "${pageContext.request.contextPath}/scrap/scrapList.do?page=" + page + "&keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}";
					},
					error: function(xhr, status, error) {
						alert("전송실패");
					}
				});
	 		
			/* 
				
				const fm = document.frm;				
				const checkArrInput = document.querySelector(".checkArrInput");
				const chekedArry = [];
				
				for(let i = 0; i < chekedScrap.length; i++) {
					chekedArry.push(chekedScrap[i].value);
				}
				
				checkArrInput.value = chekedArry.join(",");  // 배열을 input에 바로 담을 수 없음
				
				fm.action="${pageContext.request.contextPath}/scrap/scrapDeleteAction.do";
				fm.method="get";
				fm.submit();
		 	*/
		 	
			}
			
			return;
		}
		
		alert("삭제할 스크랩을 선택해주세요.");
		
	}
	
	delBtn.addEventListener("click", scrapDelete);
	
	
	// 전체선택
	const checkAllBtn = document.querySelector("#checkAll");
	
	function checkAll() {
		const chekedScrap = document.querySelectorAll(".check");
		
		for(let i = 0; i < chekedScrap.length; i++) {
			if(checkAllBtn.checked) {
				chekedScrap[i].checked = true;
			} else {
				chekedScrap[i].checked = false;
			}
		}
		
		return;
		
	}
	
	checkAllBtn.addEventListener("click", checkAll);
		
	</script>
        
    <%@ include file="/WEB-INF/footer.jsp" %>