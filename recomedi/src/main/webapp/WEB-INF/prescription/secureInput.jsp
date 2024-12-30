<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보안 문자 입력</title>
<style>
/* 스타일 추가 */
.container {
    max-width: 600px;
    margin: 50px auto;
}
</style>
</head>
<body>
<div class="container">
    <h1>보안 문자 입력</h1>
    
    <!-- 보안문자 이미지 표시 -->
    <img src="${sessionScope.secureNoImage}" alt="보안 문자">

    <!-- 보안 문자 입력 폼 -->
    <form action="${pageContext.request.contextPath}/prescription/processSecureInput.do" method="POST">
        <input type="hidden" name="jobIndex" value="${sessionScope.jobIndex}">
        <input type="hidden" name="threadIndex" value="${sessionScope.threadIndex}">
        <input type="hidden" name="jti" value="${sessionScope.jti}">
        <input type="hidden" name="twoWayTimestamp" value="${sessionScope.twoWayTimestamp}">
        
        <label for="secureNo">보안 문자</label>
        <input type="text" id="secureNo" name="secureNo" required>
        
        <button type="submit">확인</button>
    </form>
</div>
</body>
</html>
