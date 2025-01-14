<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>최종 결과</title>
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
}

.container {
    max-width: 800px;
    margin: 50px auto;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 20px;
}

.title {
    text-align: center;
    font-size: 24px;
    font-weight: bold;
}

.section {
    margin-top: 20px;
}

.section h2 {
    font-size: 18px;
    font-weight: bold;
    border-bottom: 2px solid #003366;
    padding-bottom: 5px;
}

.table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

.table th, .table td {
    border: 1px solid #cccccc;
    padding: 10px;
    text-align: left;
}

.table th {
    background-color: #f0f0f0;
}

.button-group {
    text-align: center;
    margin-top: 20px;
}

.button-group button {
    padding: 10px 20px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
}

.button-group button:first-child {
    background-color: #cccccc; /* 취소 버튼 */
}

.button-group button:last-child {
    background-color: #003366; /* 확인 버튼 */
    color: white;
}
</style>
</head>
<body>
<div class="container">
    <h1 class="title">본인 인증 결과</h1>

    <!-- 사용자 정보 -->
    <div class="section">
        <h2>사용자 정보</h2>
        <table class="table">
            <tr>
                <th>이름</th>
                <td>${sessionScope.response.data.resName}</td>
            </tr>
            <tr>
                <th>처방 기관</th>
                <td>${sessionScope.response.data.resPrescribeOrg}</td>
            </tr>
            <tr>
                <th>처방 번호</th>
                <td>${sessionScope.response.data.resPrescribeNo}</td>
            </tr>
            <tr>
                <th>제조 일자</th>
                <td>${sessionScope.response.data.resManufactureDate}</td>
            </tr>
        </table>
    </div>

    <!-- 약물 정보 -->
    <div class="section">
        <h2>약물 정보</h2>

        <!-- 약물 리스트 -->
        <c:forEach var="drug" items="${sessionScope.response.data.resDrugList}">
            <table class="table">
                <tr>
                    <th>약물 이름</th>
                    <td>${drug.resDrugName}</td>
                </tr>
                <tr>
                    <th>약물 코드</th>
                    <td>${drug.resDrugCode}</td>
                </tr>
                <tr>
                    <th>성분</th>
                    <td>${drug.resIngredients}</td>
                </tr>
                <tr>
                    <th>효능</th>
                    <td>${drug.resPrescribeDrugEffect}</td>
                </tr>
                <tr>
                    <th>복용 방법</th>
                    <td>${drug.resMedicationDirection}</td>
                </tr>
                <tr>
                    <th>복용 기간</th>
                    <td>${drug.resTotalDosingdays}일</td>
                </tr>
                <tr>
                    <th>하루 복용 횟수</th>
                    <td>${drug.resDailyDosesNumber}회</td>
                </tr>
                <tr>
                    <th>복용량</th>
                    <td>${drug.resOneDose}</td>
                </tr>

                <!-- 약물 이미지 -->
                <c:if test="${not empty drug.resDrugImageLink}">
                    <tr>
                        <th>약물 이미지</th>
                        <td><img src="${drug.resDrugImageLink}" alt="약물 이미지" style="max-width:200px;"></td>
                    </tr>
                </c:if>

            </table>

            <!-- 약물 간격 -->
            <hr style="margin-top:20px; margin-bottom:20px;">
        </c:forEach>

        <!-- 약물이 없는 경우 -->
        <c:if test="${empty sessionScope.response.data.resDrugList}">
            <p>조회된 약물 정보가 없습니다.</p>
        </c:if>

    </div>

    <!-- 버튼 -->
    <div class="button-group">
        <!-- 다시 시도 버튼 -->
        <button onclick="window.location.href='${pageContext.request.contextPath}/prescription/certification.do'">다시 시도</button>

        <!-- 완료 버튼 -->
        <button onclick="window.location.href='${pageContext.request.contextPath}/prescription/completion.do'">완료</button>
    </div>

</div>

</body>
</html>

