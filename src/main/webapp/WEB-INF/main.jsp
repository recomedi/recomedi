<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="mainHeader.jsp" %>
<script>
function check() {
	let fm = document.frm;

	if (keyword === "") {
		alert();
		fm.action="${pageContext.request.contextPath}/medicine/medicineList.do";
		fm.method="post";
		fm.submit();
	}
	  
	return;
}
</script>
   <div class="wrapper flex">      
      <form class="mainSarch flex" name="frm">
         <select class="select2" name="searchType" id="keyword">
            <option value="itemName">제품명</option>
            <option value="efcyQesitm">효능</option>
         </select>
         <div class="relative">
            <div class="flex">
               <input type="text" name = "keyword" placeholder="제품명을 입력해주세요.">
               <button type="button" class="btn btnWhite" onclick="check();">검색</button>
            </div>
            <div class="flex">
               <a href="${pageContext.request.contextPath}/medicine/medicineList.do?keyword=${mlist['감기']}" class="btn btnWhite">#감기약</a>
               <a href="#" class="btn btnWhite">#감기약</a>
               <a href="#" class="btn btnWhite">#감기약</a>
               <a href="#" class="btn btnWhite">#감기약</a>
               <a href="#" class="btn btnWhite">#감기약</a>
            </div>
<!--             <div class="searchResult absolute right none">검색 후 none class를 없애세요
               <ul class="left">
                  <li><a href="#">1. 아세트아미노펜</a></li>
                  <li><a href="#">1. 아세트아미노펜</a></li>
                  <li><a href="#">1. 아세트아미노펜</a></li>
                  <li><a href="#">1. 아세트아미노펜</a></li>
                  <li><a href="#">1. 아세트아미노펜</a></li>
               </ul>
               <a href="#" class="btn">더보기</a>
            </div> -->
         </div>
      </form>
   </div>
   
   <a href="${pageContext.request.contextPath}/prescription/certification.do" class="btn">본인인증페이지</a>
   <script>
   // select
   $(document).ready(function() {
     $('.select2').select2 ({ 
        width : "120px"
     })
   });
   </script>
   <%@ include file="footer.jsp" %>
