<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script
  src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
  integrity="sha256-pasqAKBDmFT4eHoN2ndd6lN370kFiGUFyTiUHWhU7k8="
  crossorigin="anonymous"></script>
  
</head>
<body>
<script type="text/javascript">
	$(function() {//삭제 후 삭제된 리스트 목록 불러오는 것
		$("#portfolioList :button").click(function() {
			
			location.href = "portfolio_remove.do?no="+$(this).attr("data-no");
		});
	});//EL이랑 햇갈리지 말기.
</script>  
	<h1> 포트폴리오 리스트</h1>
	
	<form action="portfolio_search.do" method="post">
		<input type="text" name="title" placeholder="제목을 입력하세요" value="${param.title}">
		<input type="submit" value="검섹">
	</form>
	
	<table>
		<thead>
				<tr>
					<th>No.</th>
					<th>제목</th>
					<th>시작일</th>
					<th>종료일</th>
					<th>자료수</th>
					<th>대표자</th>
					<th>참여자수</th>
					<th></th>
					
				</tr>
				</thead>
			<tbody id="portfolioList">
			<c:forEach items="${list}" var="portfolio"><!-- EL 꼭 해주기  html과 jsp 에 쓸 수 있다-->
				<tr>
					<td>${portfolio.no}</td>
					<td><a href = "portfolio_detail.do?no=${portfolio.title}">${portfolio.title}</a></td>
					<td>${portfolio.startDate}</td>
					<td>${portfolio.endDate}</td>
					<td>${portfolio.dataCount}</td>
					<td>${portfolio.leader}</td>
					<td>${portfolio.memberCount}</td>
					<td><input type="button" value="삭제"  data-no="${portfolio.no}"/></td>
										
				</tr>
				</c:forEach>
			</tbody>
		
	</table> 
</body>
</html>