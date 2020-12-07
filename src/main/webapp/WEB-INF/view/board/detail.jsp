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
  <body class="bg-light" style="min-height: 100%; overflow-y: scroll">
    <!-- 게시판 -->
    <%@ include file="/WEB-INF/view/navbar.jsp" %>
    <br />
    <div class="container bg-light">
      <input id="boardNo" type="hidden" value="${boardDto.boardNo}" />
      <!-- <button class="btn btn-secondary" onclick="">LIST</button> -->
      <sec:authorize access="isAuthenticated()">
        <sec:authentication var="username" property="principal.username" />
        <input id="author" type="hidden" value="${username}" />
        <c:if test="${username eq boardDto.author}">
          <a class="btn btn-warning" href="/post/${boardDto.boardNo}/update"
            >EDIT</a
          >
          <button id="btn-deletePost" class="btn btn-danger">DELETE</button>
        </c:if>
      </sec:authorize>
      <button
        type="button"
        class="btn btn-info"
        data-toggle="modal"
        data-target="#exampleModal"
      >
        SUMMARY
      </button>
      <!-- Modal -->
      <div
        class="modal fade"
        id="exampleModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">SUMMARY</h5>
              <button
                type="button"
                class="close"
                data-dismiss="modal"
                aria-label="Close"
              >
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <div>${boardDto.contentSummary}</div>
              <hr />
              <div>${boardDto.contentKeyword}</div>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-dismiss="modal"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
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
        <div class="card bg-light shadow-lg">
          <div>${boardDto.content}</div>
        </div>
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
      <sec:authorize access="isAuthenticated()">
        <div class="card bg-light shadow-lg p-3">
          <form id="writeParentComment" name="writeParentComment">
            <div class="card-body">
              <textarea
                id="content"
                class="form-control"
                row="1"
                required
              ></textarea>
            </div>
          </form>
          <div class="card-footer">
            <div class="text-right">
              <button id="btn-createComment" class="btn btn-primary">
                POST
              </button>
            </div>
          </div>
        </div>
        <br />
      </sec:authorize>
      <div class="card">
        <div class="card-header">Comments</div>
        <ul class="list-group" id="commentBlockList">
          <c:forEach var="comment" items="${commentList}">
            <li class="list-group-item" id="commentBlock${comment.commentNo}">
              <c:choose>
                <c:when test="${comment.depth gt 0}">
                  <div style="padding-left: ${comment.depth}em">
                    &#x21B3; ${comment.content}
                  </div>
                </c:when>
                <c:otherwise> ${comment.content} </c:otherwise>
              </c:choose>
              <hr />
              <div class="d-flex justify-content-end">
                <div class="font-italic">Author: ${comment.author} &nbsp;</div>
                <sec:authorize access="isAuthenticated()">
                  <c:if test="${comment.depth lt 2}">
                    <button
                      class="badge"
                      data-toggle="collapse"
                      data-target="#collapseCommentReply${comment.commentNo}"
                    >
                      REPLY
                    </button>
                  </c:if>
                  <c:if test="${username eq comment.author}">
                    <button
                      class="badge"
                      data-toggle="collapse"
                      data-target="#collapseCommentUpdate${comment.commentNo}"
                    >
                      UPDATE
                    </button>
                    <button
                      onclick="board.deleteCommentDto(${boardDto.boardNo}, ${comment.commentNo})"
                      class="badge"
                    >
                      DELETE
                    </button>
                  </c:if>
                </sec:authorize>
              </div>
            </li>
            <div class="collapse" id="collapseCommentReply${comment.commentNo}">
              <li class="list-group-item" id="commentReplyBlock">
                <form
                  id="writeReply${comment.commentNo}"
                  name="writeReply${comment.commentNo}"
                >
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
                <br />
                <div class="text-right">
                  <button
                    onclick="board.createCommentReplyDto(${comment.commentNo})"
                    class="btn btn-primary"
                  >
                    REPLY
                  </button>
                </div>
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
                <br />
                <div class="text-right">
                  <button
                    onclick="board.updateCommentReplyDto(${comment.commentNo})"
                    class="btn btn-primary"
                  >
                    UPDATE
                  </button>
                </div>
              </li>
            </div>
          </c:forEach>
        </ul>
      </div>
      <br />
      <br />
      <br />
      <br />
      <br />
    </div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <script src="${pageContext.request.contextPath}/resources/js/board.js"></script>
    <%@ include file="/WEB-INF/view/footer.jsp" %>
  </body>
</html>
