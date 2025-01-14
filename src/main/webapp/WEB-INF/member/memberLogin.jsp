<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<%@ include file="/WEB-INF/header.jsp" %>
         
  <script>

	function check() {	  

	  // 유효성 검사하기
	  let fm = document.frm;
	  
	  if (fm.id.value == "") {
		  alert("이메일을 입력해주세요");
		  fm.id.focus();
		  return;
	  } else if (fm.pwd.value == "") {
		  alert("비밀번호를 입력해주세요");
		  fm.pwd.focus();
		  return;
	  }
	  
	  fm.action = "${pageContext.request.contextPath}/member/memberLoginAction.do";
	  fm.method = "post";
	  fm.submit();
	  
	  return;
    }
  </script>
  
	<div class="wrapper flex">
		<section class="member shadow">
			<img src="${pageContext.request.contextPath}/resources/images/bg_login.png" alt="로그인" class="bg block">
			<h2 class="regular title center">로그인</h2>
			<form name="frm">
				<label for="email" class="block">이메일</label>
				<input type="text" id="email" placeholder="Email" name="id">
				<label for="password" class="block">비밀번호</label>
				<input type="password" id="password" placeholder="Password" name="pwd">
				<div class="btnBox center">
					<button class="btn" type="button" onclick="check()">로그인</button>
					<a class="btn" href="${pageContext.request.contextPath}/member/memberJoin.do">회원가입</a>
				</div>
			</form>
		</section>
	</div>
        
    <%@ include file="/WEB-INF/footer.jsp" %>