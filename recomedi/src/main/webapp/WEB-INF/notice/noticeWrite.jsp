<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link href="${pageContext.request.contextPath}/resources/css/style2.css" rel="stylesheet">
<script>
    function check() {
        // 유효성 검사하기
        let fm = document.frm;
        if (fm.title.value == "") {
            alert("제목을 입력해주세요");
            fm.title.focus();
            return;
        } else if (fm.contents.value == "") { 
            alert("내용을 입력해주세요");
            fm.contents.focus();
            return;
        }
        let ans = confirm("저장하시겠습니까?");
        if (ans == true) {
            fm.action = "./noticeWriteAction.do";
            fm.method = "post";
            fm.submit();
        }
        return;
    }
</script>
</head>
<body>
    <header>
        <h2 class="mainTitle">글쓰기</h2>
    </header>
    <!-- 저장 성공/실패 메시지 처리 -->
    <c:if test="${not empty msg}">
        <script>
            alert("${msg}");  // 컨트롤러에서 전달된 메시지
            window.location.href = '/notice/noticeList.do';
        </script>
    </c:if>
    <form name="frm">
        <table class="writeTable">
            <tr>
                <th>제목</th>
                <td><input type="text" name="title"></td>
            </tr>
            <tr>
                <th>내용</th>
                <td><textarea name="contents" rows="6"></textarea></td> 
            </tr>
        </table>
        <div class="btnBox">
            <button type="button" class="btn" onclick="check();">등록</button>
            <a class="btn aBtn" href="noticeList.do">목록</a>
        </div>
    </form>
</body>
</html>