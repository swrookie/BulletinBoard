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
      </div>
    </nav>
    <br />
    <div class="container">
      <button class="btn btn-secondary" onclick="history.back()">List</button>
      <sec:authorize access="isAuthenticated()">
        <sec:authentication var="username" property="principal.username" />
        <c:if test="${username eq boardDto.author}">
          <a class="btn btn-warning" href="/post/${boardDto.boardNo}/update">Edit</a>
          <input id="boardNo" type="hidden" value="${boardDto.boardNo}" />
          <button id="btn-delete" class="btn btn-danger">Delete</button>
        </c:if>
      </sec:authorize>
      <br />
      <br />
      <div>
        Post No: <span id="boardNo"><i>${boardDto.boardNo}</i></span> 
        Author: <span id="author"><i>${boardDto.author}</i></span>
      </div>
      <br />
      <div class="form-group">
        <h3>${boardDto.title}</h3>
      </div>
      <hr />
      <div class="form-group">
        <div>${boardDto.content}</div>
      </div>
      <hr />
      <div>
        <strong>Attachments: </strong>
        <c:forEach var="file" items="${fileList}">
          <a class="card-text" href="/post/${boardDto.boardNo}/download/${file.fileNo}">
            ${file.origFileName}</a
          >
        </c:forEach>
      </div>
      <hr />
      <div class="card">
        <form
          method="POST"
          action="${pageContext.request.contextPath}/save_comment"
        >
          <input type="hidden" name="boardNo" value="${boardDto.boardNo}" />
          <div class="card-body">
            <textarea name="content" class="form-control" row="1"></textarea>
          </div>
          <div class="card-footer">
            <input type="submit" class="btn btn-primary" value="POST" />
          </div>
        </form>
      </div>
      <br />
      <div class="card">
        <div class="card-header">Comments</div>
        <ul class="list-group" id="commentBlockList">
          <c:forEach var="comment" items="${commentList}">
            <li
              class="list-group-item d-flex justify-content-between ml-${comment.depth}"
              id="commentBlock"
            >
                ${comment.content}
              <div class="d-flex">
                <div class="font-italic">Author: ${comment.author} &nbsp;</div>
                <button class="badge">DELETE</button>
                <button class="badge">UPDATE</button>
                <button
                  class="badge"
                  data-toggle="collapse"
                  data-target="#collapseCommentReply${comment.commentNo}"
                >
                  REPLY
                </button>
              </div>
            </li>
            <div class="collapse" id="collapseCommentReply${comment.commentNo}">
              <li
                class="list-group-item d-flex justify-content-between"
                id="replyBlock"
              >
                <form
                  method="POST"
                  action="${pageContext.request.contextPath}/save_comment/"
                >
                  <input
                    type="hidden"
                    name="boardNo"
                    value="${boardDto.boardNo}"
                  />
                  <input
                    type="hidden"
                    name="parent"
                    value="${comment.commentNo}"
                  />
                  <textarea
                    name="content"
                    class="form-control"
                    row="1"
                  ></textarea>
                  <input type="submit" class="btn btn-primary" value="POST" />
                </form>
              </li>
            </div>
          </c:forEach>
        </ul>
      </div>
    </div>
    <script src="${pageContext.request.contextPath}/resources/js/board.js"></script>
    <br />
    <footer>
      <div class="jumbotron text-center" style="margin-bottom: 0">
        <p>Bulletin Board Project by swrookie</p>
        <p>dpdjflr@gmail.com</p>
      </div>
    </footer>
  </body>
</html>
