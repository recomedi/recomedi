<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<%@ include file="/WEB-INF/slideBar.jsp" %>
	
 	<header class="relative center" id="hd">
		<a href="${pageContext.request.contextPath}/" class="absolute logo"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo"></a>
		<h2 class="mainTitle relative bold">약품정보</h2>
		<div class="absolute menu flex">
			<a href="#" class="relative alarm confirmation"><img src="${pageContext.request.contextPath}/resources/images/alarm.png" alt="alarm"></a>
			<button class="btnMenu flex"><p class="first"></p><p class="second"></p><p class="third"></p></button>
		</div>
	</header>
	
 	<div class="wrapper">
		<section class="shadow detailContents relative">
			<form name="frm">
				<input type="hidden" value="${requestScope.mdv.efcyQesitm}" class="efcyQesitm">
				<input type="hidden" value="${requestScope.sidx}" class="sidx" name="checkArr">
				<button type="button" id="scrapBtn" class="scrap absolute">
				<c:choose>
					<c:when test="${requestScope.result == 0}">
						<img src="${pageContext.request.contextPath}/resources/images/scrap_blank.png" class="scrapImg" alt="미스크랩">
					</c:when>
					<c:otherwise>
						<img src="${pageContext.request.contextPath}/resources/images/scrap.png" class="scrapImg" alt="스크랩">
					</c:otherwise>
				</c:choose>
				</button>
			</form>
			<h2 class="contentTitle center shadow regular">${requestScope.mdv.itemName} - ${requestScope.mdv.entpName}</h2>
			<div class="content shadow">
				<p>1. 효능</p>
				<p>${requestScope.mdv.efcyQesitm}</p><br>
				<p>2. 사용법</p>
				<p>${requestScope.mdv.useMethodQesitm}</p><br>
				<p>3. 주의사항경고</p>
				<p>${requestScope.mdv.atpnWarnQesitm}</p><br>
				<p>4. 주의사항</p>
				<p>${requestScope.mdv.atpnQesitm}</p><br>
				<p>5. 상호작용</p>
				<p>${requestScope.mdv.intrcQesitm}</p><br>
				<p>6. 부작용</p>
				<p>${requestScope.mdv.seQesitm}</p><br>
				<p>7. 보관법</p>
				<p>${requestScope.mdv.depositMethodQesitm}</p><br>
			</div>
		</section>

		<div class="btnBox center">
			<button type="button" class="btn" onclick="history.back();">목록가기</button>
		</div>
	</div>
	
	<script>	
	// 스크랩 관리
	const scrapBtn = document.querySelector("#scrapBtn");
	
	function scrap() {

		const scrapImg = document.querySelector(".scrapImg");
		
		if(scrapImg.alt == "미스크랩") {
	 		let ans = confirm("스크랩을 등록 하시겠습니까?");
	 		
	 		if (ans == true) {
				
	 			const itemSeq = "${requestScope.mdv.itemSeq}";
	 			const efcyQesitm = document.querySelector(".efcyQesitm").value;
	 			const itemName = "${requestScope.mdv.itemName}";
	 			
				$.ajax({
					type: "get",
					url: "${pageContext.request.contextPath}/scrap/scrapInsertAction.do",
					dataType: "json",
					data: {"itemSeq": itemSeq, "efcyQesitm": efcyQesitm, "itemName": itemName},
					success: function(result) {
						alert("등록이 완료되었습니다.");
						scrapImg.src = "${pageContext.request.contextPath}/resources/images/scrap.png";
						scrapImg.alt = "스크랩";
						document.querySelector(".sidx").value = result.sidx;
					},
					error: function(xhr, status, error) {
						alert("전송실패");
					}
				});
			}
	 		
		} else {
			
			let ans = confirm("스크랩을 삭제 하시겠습니까?");
	 		
			if (ans == true) {
				
				const chekedArry = [];
				chekedArry.push(document.querySelector(".sidx").value);
				
				const checkArr = chekedArry.join(",");
				
				$.ajax({
					type: "get",
					url: "${pageContext.request.contextPath}/scrap/scrapDeleteAction.do",
					dataType: "json",
					data: {"checkArr": checkArr},
					success: function(result) {
						alert("삭제가 완료되었습니다.");
						scrapImg.src = "${pageContext.request.contextPath}/resources/images/scrap_blank.png";
						scrapImg.alt = "미스크랩";
					},
					error: function(xhr, status, error) {
						alert("전송실패");
					}
				});
			}
			
	 		/* let ans = confirm("스크랩을 삭제 하시겠습니까?");
	 		
			if (ans == true) {
						
				const fm = document.frm;
				
				fm.action="${pageContext.request.contextPath}/scrap/" + "${requestScope.mdv.itemSeq}" + "/scrapDeleteAction.do";
				fm.method="get";
				fm.submit();
			} */
		}
		
		return;		
	}
	
	scrapBtn.addEventListener("click", scrap);
	</script>
        
    <%@ include file="/WEB-INF/footer.jsp" %>