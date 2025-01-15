<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.recomedi.myapp.domain.*"%>
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
		<section class="medicineAll flex">
			<div class="medicineItem section shadow">
				<h3 class="regular title">#감기</h3>
				<ul>
				<c:forEach items="${mlist['감기']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do?keyword=${hmlist['감기']}" class="btn">더보기</a>
				</div>
			</div>
			
			
			<div class="medicineItem section shadow">
				<h3 class="regular title">#소화제</h3>
				<ul>
				<c:forEach items="${mlist['소화']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#해열제</h3>
				<ul>
				<c:forEach items="${mlist['해열']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#진통제</h3>
				<ul>
				<c:forEach items="${mlist['진통']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#피부약</h3>
				<ul>
				<c:forEach items="${mlist['피부']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#제산제</h3>
				<ul>
				<c:forEach items="${mlist['속쓰림']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#피로회복제</h3>
				<ul>
				<c:forEach items="${mlist['육체피로']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#구충제</h3>
				<ul>
				<c:forEach items="${mlist['구충']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
			<div class="medicineItem section shadow">
				<h3 class="regular title">#수면유도제</h3>
				<ul>
				<c:forEach items="${mlist['불면']}" var="mdv" varStatus="status" begin="0" end="5">
				<li><a href="${pageContext.request.contextPath}/medicine/${mdv.itemSeq}/medicineContents.do?medidx=${mdv.medidx}">${mdv.itemName} - ${mdv.entpName}</a></li>	
				</c:forEach>
				</ul>
				<div class="btnBox right">
					<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn">더보기</a>
				</div>
			</div>
		</section>
			
	</div>

    <%@ include file="/WEB-INF/footer.jsp" %>