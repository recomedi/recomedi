<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ include file="/WEB-INF/header.jsp" %>
<title>글목록</title>
<link href="./style.css" rel="stylesheet">
<div>
		<button class="close absolute"><img src="./image/menuClose.svg" alt="menuClose"></button>
	</div>
	
	<header class="relative center" id="hd">
		<a href="#" class="absolute logo"><img src="./image/logo.png" alt="logo"></a>
				<h2>처방 상세 정보</h2>
		
		<!-- 사용자 정보 -->
		<table class="table">
			<tr><th>조제일자</th><td>${prescription.resMenufactureDate}</td></tr>
			<tr><th>처방기관</th><td>${prescription.resPrescribeOrg}</td></tr>
			<tr><th>조제기관</th><td>${prescription.commBrandName}</td></tr>
		</table>
		
		<!-- 약물 정보 -->
		<h3>약물 정보</h3>
		
		<c:forEach var="drug" items="${prescription.drugs}">
			<table class="table">
				<tr><th>약물 이름</th><td>${drug.resDrugName}</td></tr>
				<tr><th>성분</th><td>${drug.resIngredients}</td></tr>
			</table>
		
			<hr style="margin-top:20px; margin-bottom:20px;">
		</c:forEach>
		
		<c:if test="${empty prescription.drugs}">
			<p>조회된 약물 정보가 없습니다.</p>
		</c:if>
		
		<!-- 버튼 -->
		<div class="button-group">
			<button onclick="window.location.href='${pageContext.request.contextPath}/prescription/prescriptionList.do'">목록으로 돌아가기</button>
		</div>
			
					<p class="infoText">※제품명을 클릭하시면 약에 대한 상세정보를 확인하실 수 있습니다.</p>
		
					<div class="page">
						<ul class="flex">
							<li class="center"><a href="#">◀</a></li>
							<li class="center on"><a href="#">1</a></li>
							<li class="center"><a href="#">2</a></li>
							<li class="center"><a href="#">3</a></li>
							<li class="center"><a href="#">4</a></li>
							<li class="center"><a href="#">5</a></li>
							<li class="center"><a href="#">6</a></li>
							<li class="center"><a href="#">7</a></li>
							<li class="center"><a href="#">8</a></li>
							<li class="center"><a href="#">9</a></li>
							<li class="center"><a href="#">10</a></li>
							<li class="center"><a href="#">▶</a></li>
						</ul>
					</div>
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
<%@ include file="/WEB-INF/footer.jsp" %>