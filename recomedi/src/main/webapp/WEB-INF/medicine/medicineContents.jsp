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
			<div class="scrap absolute">
				<img src="${pageContext.request.contextPath}/resources/images/scrap_blank.png" alt="미스크랩">
				<%-- <img src="${pageContext.request.contextPath}/resources/images/scrap.png" alt="스크랩"> --%>
			</div>
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
        
    <%@ include file="/WEB-INF/footer.jsp" %>