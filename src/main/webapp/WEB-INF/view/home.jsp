<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags" %><%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en" style="position: relative; min-height: 100%; margin: 0">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>OnBoard</title>
    <!-- Bootstrap CSS & JS -->
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
      integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
      crossorigin="anonymous"
    />
    <script
      src="https://code.jquery.com/jquery-3.5.1.js"
      integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
      crossorigin="anonymous"
    ></script>
    <link
      href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css"
      rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
  </head>
  <body class="bg-light" style="min-height: 100%">
    <!-- 게시판 -->
    <%@ include file="/WEB-INF/view/navbar.jsp" %>
    <br />
    <div
      class="container"
      style="min-height: 100%; position: relative; padding-bottom: 100px"
    >
      <h1>Bulletin Board</h1>
      <div class="d-flex justify-content-between">
        <div class="dropdown">
          <a
            aria-expanded="false"
            aria-haspopup="true"
            class="btn btn-info dropdown-toggle"
            id="dropdown"
            role="button"
            data-toggle="dropdown"
          >
            <span id="sortSelected">Sort Post By</span
            ><span class="caret"></span>
          </a>
          <div
            class="dropdown-menu sort"
            id="dropdownSort"
            data-toggle="dropdown"
          >
            <a class="dropdown-item">Title Asc</a>
            <a class="dropdown-item">Title Desc</a>
            <a class="dropdown-item">Posted Date Asc</a>
            <a class="dropdown-item">Posted Date Desc</a>
            <a class="dropdown-item">Most Comments</a>
          </div>
        </div>
        <form class="form-inline" method="GET" action="/search">
          <div class="form-group">
            <select class="form-control" id="searchType" name="searchType">
              <option>Title</option>
              <option>Content</option>
            </select>
          </div>
          <input
            class="form-control mr-sm-2"
            type="search"
            id="keyword"
            name="keyword"
            placeholder="Enter Keyword"
            aria-label="Search"
          />
          <button
            class="btn btn-info my-2 my-sm-0"
            type="button"
            onclick="searchPost();"
          >
            Search
          </button>
        </form>
      </div>
      <table class="table table-striped table-hover">
        <thead class="thead-dark">
          <tr>
            <th scope="col">#</th>
            <th scope="col">Title</th>
            <th scope="col">Author</th>
            <th scope="col">Posted Date</th>
            <th scope="col">Modified Date</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <c:forEach var="post" items="${boardList}">
              <tr>
                <td>${post.boardNo}</td>
                <td>
                  <c:choose>
                    <c:when test="${post.getCommentCount() gt 0}">
                      <a href="/post/${post.boardNo}">
                        ${post.title} [${post.getCommentCount()}]</a
                      >
                    </c:when>
                    <c:otherwise>
                      <a href="/post/${post.boardNo}"> ${post.title}</a>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>${post.author}</td>
                <td>
                  <fmt:formatDate
                    value="${post.createdDate}"
                    pattern="yyyy-MM-dd HH:mm"
                  />
                </td>
                <td>
                  <fmt:formatDate
                    value="${post.modifiedDate}"
                    pattern="yyyy-MM-dd HH:mm"
                  />
                </td>
              </tr>
            </c:forEach>
          </tr>
        </tbody>
      </table>
      <div class="text-right">
        <sec:authorize access="isAuthenticated()">
          <button
            class="btn btn-primary"
            type="button"
            onclick="location.href='/post/write'"
          >
            WRITE
          </button>
        </sec:authorize>
      </div>
      <nav aria-label="Page Navigation">
        <ul class="pagination justify-content-center">
          <li class="page-item enabled">
            <a class="page-link" href="?page=0">First</a>
          </li>
          <c:choose>
            <c:when test="${currentPage != 0}">
              <li class="page-item">
                <a
                  class="page-link"
                  href="?page=${currentPage - 1}"
                  tabindex="-1"
                  aria-disabled="true"
                  aria-label="Previous"
                >
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a
                  class="page-link"
                  href="?page=${currentPage - 1}"
                  tabindex="-1"
                  aria-disabled="true"
                  aria-label="Previous"
                >
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
            </c:otherwise>
          </c:choose>
          <c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
            <c:choose>
              <c:when test="${currentPage eq pageNum}">
                <li class="page-item active">
                  <a class="page-link" name="page" href="?page=${pageNum}">
                    ${pageNum + 1}
                  </a>
                </li>
              </c:when>
              <c:otherwise>
                <li class="page-item">
                  <a class="page-link" name="page" href="?page=${pageNum}">
                    ${pageNum + 1}
                  </a>
                </li>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          <c:choose>
            <c:when test="${currentPage lt lastPage - 1}">
              <li class="page-item">
                <a
                  class="page-link"
                  href="?page=${currentPage + 1}"
                  aria-label="Next"
                >
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="page-link" href="#" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </c:otherwise>
          </c:choose>
          <li class="page-item">
            <a class="page-link" href="?page=${lastPage - 1}">Last</a>
          </li>
        </ul>
      </nav>
    </div>
    <script>
      $("#dropdownSort a").click(function () {
        $("#sortSelected").text($(this).text());
      });
    </script>
    <script type="text/javascript">
      function searchPost() {
        var searchType = $("#searchType").val();
        var keyword = $("#keyword").val();
        if (keyword != "")
          window.location.href =
            "http://localhost:8080/search/" + searchType + "/" + keyword;
        else {
          alert("Keyword is Empty!");
          $("#keyword").focus();
          return false;
        }
      }
    </script>
    <br />
    <%@ include file="/WEB-INF/view/footer.jsp" %>
  </body>
</html>
