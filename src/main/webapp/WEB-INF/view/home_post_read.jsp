<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Bulletin Board</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS & JS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
    integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
    integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
    crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
    crossorigin="anonymous"></script>
</head>

<body>
  <header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="go_home_post_read">Bulletin Board</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <!-- <li class="nav-item active">
            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
          </li> -->
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
              aria-haspopup="true" aria-expanded="false">
              Dropdown
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="#">Action</a>
              <a class="dropdown-item" href="#">Another action</a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="#">Something else here</a>
            </div>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" method="GET" action="${pageContext.request.contextPath}/search_posts">
          <input class="form-control mr-sm-2" type="search" name="keyword" placeholder="게시글 제목으로 검색"
            aria-label="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색하기</button>
        </form>
      </div>
    </nav>
  </header>
  <table class="table table-hover">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">제목</th>
        <th scope="col">작성자</th>
        <th scope="col">작성일</th>
        <th scope="col">수정일</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <c:forEach var="post" items="${boardList}">
      <tr>
        <td>${post.boardNo}</td>
        <td>
          <a href="/post_detail_page/${post.boardNo}">
            ${post.title}
          </a>
        </td>
        <td>${post.author}</td>
        <td>${post.createDate}</td>
        <td>${post.updateDate}</td>
      </tr>
      </c:forEach>
      </tr>
    </tbody>
  </table>
  <nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
      <li class="page-item enabled">
        <a class="page-link" href="?page=1">First</a>
      </li>
      <li class="page-item enabled">
        <a class="page-link" href="#" tabindex="-1" aria-disabled="true" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
        <c:forEach var="pageNum" items="${pageList}">
          <c:if test="${pageNum gt 0}">
            <li class="page-item">
              <a class="page-link" name="page" href="?page=${pageNum}">${pageNum}</a>
          </li>
        </c:if>
      </c:forEach>
      <li class="page-item">
        <a class="page-link" href="#" aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
      <li class="page-item">
        <a class="page-link" href="#">Last</a>
      </li>
    </ul>
  </nav>
  <footer>
    <div class="text-center">
      <button class="btn btn-primary" type="button"
        onclick="location.href='${pageContext.request.contextPath}/go_home_post_create'">
        글쓰기
      </button>
    </div>
  </footer>
</body>

</html>