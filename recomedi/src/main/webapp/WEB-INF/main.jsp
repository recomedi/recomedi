<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="header.jsp" %>

메인화면입니다.



<a href="${pageContext.request.contextPath}/prescription/certification.do" class="btn btn-primary">본인인증페이지</a>
<a href="${pageContext.request.contextPath}/medicine/medicineList.do" class="btn btn-primary">의약품검색페이지</a>
<a href="${pageContext.request.contextPath}/medicine/medicineContents.do" class="btn btn-primary">의약품상세페이지</a>
<a href="${pageContext.request.contextPath}/medicine/medicineHashTag.do" class="btn bth-primary">전체의약품조회페이지</a>
<%@ include file="footer.jsp" %>