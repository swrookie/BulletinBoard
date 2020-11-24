<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
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

  <body>
    <!-- 게시판 -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="/">OnBoard</a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              Menu
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <sec:authorize access="isAnonymous()">
                <form class="px-4 py-3" method="POST" action="${pageContext.request.contextPath}/login">
                  <div class="form-group">
                    <label for="exampleDropdownFormEmail1">Username</label>
                    <input type="text" class="form-control" name="userName" id="exampleDropdownFormEmail1" placeholder="Username">
                  </div>
                  <div class="form-group">
                    <label for="exampleDropdownFormPassword1">Password</label>
                    <input type="password" class="form-control" name="password" id="exampleDropdownFormPassword1" placeholder="Password">
                  </div>
                  <div class="form-group">
                    <div class="form-check">
                      <input type="checkbox" class="form-check-input" id="dropdownCheck">
                      <label class="form-check-label" for="dropdownCheck">
                        Remember me
                      </label>
                    </div>
                  </div>
                  <button type="submit" class="btn btn-primary">Sign in</button>
                </form>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/sign_up">New around here? Sign up</a>
                <a class="dropdown-item" href="#">Forgot password?</a>
              </sec:authorize>
              <sec:authorize access="isAuthenticated()">
                <a class="dropdown-item" href="#">Profile</a>
                <div class="dropdown-divider"></div>
                <a
                  class="dropdown-item"
                  href="${pageContext.request.contextPath}/logout"
                  >Logout</a
                >
              </sec:authorize>
            </div>
          </li>
        </ul>
        <form
          class="form-inline my-2 my-lg-0"
          method="GET"
          action="/go_home/search_posts"
        >
          <input
            class="form-control mr-sm-2"
            type="search"
            name="keyword"
            placeholder="Search by Post Title"
            aria-label="Search"
          />
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">
            Search
          </button>
        </form>
      </div>
    </nav>
    <br />
    <div class="container">
      <table class="table table-bordered table-striped table-hover">
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
                  <a href="/post/${post.boardNo}">
                    ${post.title}
                  </a>
                </td>
                <td>${post.author}</td>
                <td><fmt:formatDate value="${post.createdDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><fmt:formatDate value="${post.modifiedDate}" pattern="yyyy-MM-dd HH:mm"/></td>
              </tr>
            </c:forEach>
          </tr>
        </tbody>
      </table>
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

    <div class="text-center">
      <sec:authorize access="isAnonymous()">
        <button
          class="btn btn-primary"
          type="button"
          onclick="location.href='/create_post'"
          disabled
        >
          WRITE
        </button>
      </sec:authorize>
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

    <br />
    <footer>
      <div class="jumbotron text-center" style="margin-bottom: 0">
        <p>Bulletin Board Project by swrookie</p>
        <p>dpdjflr@gmail.com</p>
      </div>
    </footer>
  </body>
</html>
