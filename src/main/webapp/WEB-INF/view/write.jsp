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
    <%@ include file="/WEB-INF/view/navbar.jsp" %>
    <br />
    <div class="container">
      <form
        name="writeForm"
        id="writeForm"
        enctype="multipart/form-data"
        accept-charset="UTF-8"
      >
        <div class="form-group">
          <input
            type="text"
            class="form-control"
            placeholder="Enter title"
            id="title"
            maxlength="30"
          />
        </div>
        <div class="form-group">
          <textarea
            class="form-control summernote"
            rows="3"
            id="content"
          ></textarea>
        </div>
        <div class="custom-file">
          <input
            type="file"
            class="custom-file-input"
            id="customFile"
            name="files"
            multiple="multiple"
          />
          <label class="custom-file-label" for="customFile">Choose files</label>
        </div>
      </form>
      <br />
      <div class="text-right">
        <button type="button" id="btn-createPost" class="btn btn-primary">
          POST
        </button>
      </div>
    </div>
    <script>
      $(".summernote").summernote({
        placeholder: "Write Something...",
        tabsize: 2,
        height: 300,
      });
    </script>
    <script>
      $(".custom-file-input").on("change", function () {
        var fileName = $(this).val().split("\\").pop();
        $(this)
          .siblings(".custom-file-label")
          .addClass("selected")
          .html(fileName);
      });
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/board.js"></script>
    <br />
    <%@ include file="/WEB-INF/view/footer.jsp" %>
  </body>
</html>
