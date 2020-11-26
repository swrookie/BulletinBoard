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
    <!-- <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
    <link rel="manifest" href="/site.webmanifest"> -->
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
    <%@ include file="/WEB-INF/view/navbar.jsp" %>
    <br />
    <div class="container">
      <input id="boardNo" type="hidden" value="${boardDto.boardNo}" />
      <button class="btn btn-secondary" onclick="history.back()">LIST</button>
      <sec:authorize access="isAuthenticated()">
        <sec:authentication var="username" property="principal.username" />
        <c:if test="${username eq boardDto.author}">
          <a class="btn btn-warning" href="/post/${boardDto.boardNo}/update"
            >EDIT</a
          >
          <button id="btn-delete_post" class="btn btn-danger">DELETE</button>
        </c:if>
      </sec:authorize>
      <br />
      <br />
      <div>
        Post No: <span id="boardNo"><i>${boardDto.boardNo}</i></span> Author:
        <span id="author"><i>${boardDto.author}</i></span>
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
          <a
            class="card-text"
            href="/post/${boardDto.boardNo}/download/${file.fileNo}"
          >
            ${file.origFileName}</a
          >
        </c:forEach>
      </div>
      <hr />
      <div class="card">
        <form>
          <div class="card-body">
            <textarea id="content" class="form-control" row="1"></textarea>
          </div>
        </form>
        <div class="card-footer">
          <button id="btn-createComment" class="btn btn-primary">POST</button>
        </div>
      </div>
      <br />
      <div class="card">
        <div class="card-header">Comments</div>
        <ul class="list-group" id="commentBlockList">
          <c:forEach var="comment" items="${commentList}">
            <li
              class="list-group-item d-flex justify-content-between"
              id="commentBlock${comment.commentNo}"
            >
              <c:choose>
                <c:when test="${comment.depth gt 0}">
                  <div style="padding-left: ${comment.depth}em">
                    L ${comment.content}
                  </div>
                </c:when>
                <c:otherwise> ${comment.content} </c:otherwise>
              </c:choose>
              <div class="d-flex">
                <div class="font-italic">Author: ${comment.author} &nbsp;</div>
                <button
                  class="badge"
                  data-toggle="collapse"
                  data-target="#collapseCommentReply${comment.commentNo}"
                >
                  REPLY
                </button>
                <c:if test="${username eq comment.author}">
                  <button
                    class="badge"
                    data-toggle="collapse"
                    data-target="#collapseCommentUpdate${comment.commentNo}"
                  >
                    UPDATE
                  </button>
                  <button
                    onClick="board.deleteCommentDto(${boardDto.boardNo}, ${comment.commentNo})"
                    class="badge"
                  >
                    DELETE
                  </button>
                </c:if>
              </div>
            </li>
            <div class="collapse" id="collapseCommentReply${comment.commentNo}">
              <li class="list-group-item" id="commentReplyBlock">
                <form>
                  <input
                    type="hidden"
                    id="parent${comment.commentNo}"
                    value="${comment.commentNo}"
                  />
                  <textarea
                    id="contentReply${comment.commentNo}"
                    class="form-control"
                    row="1"
                  ></textarea>
                </form>
                <button
                  onclick="board.createCommentReplyDto(${comment.commentNo})"
                  class="btn btn-primary"
                >
                  REPLY
                </button>
              </li>
            </div>
            <div
              class="collapse"
              id="collapseCommentUpdate${comment.commentNo}"
            >
              <li class="list-group-item" id="commentUpdateBlock">
                <form>
                  <input
                    id="commentNo${comment.commentNo}"
                    type="hidden"
                    value="${comment.commentNo}"
                  />
                  <input
                    id="depth${comment.commentNo}"
                    type="hidden"
                    value="${comment.depth}"
                  />
                  <textarea
                    id="contentUpdate${comment.commentNo}"
                    class="form-control"
                    row="1"
                  ></textarea>
                </form>
                <button
                  onclick="board.updateCommentReplyDto(${comment.commentNo})"
                  class="btn btn-primary"
                >
                  UPDATE
                </button>
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
