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
    <!-- <link
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
    ></script> -->
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
      integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
      crossorigin="anonymous"
    />
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/css/fileinput.min.css"
      media="all"
      rel="stylesheet"
      type="text/css"
    />
    <!-- if using RTL (Right-To-Left) orientation, load the RTL CSS file after fileinput.css by uncommenting below -->
    <!-- link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/css/fileinput-rtl.min.css" media="all" rel="stylesheet" type="text/css" /-->
    <!-- the font awesome icon library if using with `fas` theme (or Bootstrap 4.x). Note that default icons used in the plugin are glyphicons that are bundled only with Bootstrap 3.x. -->
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
      crossorigin="anonymous"
    />
    <script
      src="https://code.jquery.com/jquery-3.5.1.js"
      integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
      crossorigin="anonymous"
    ></script>
    <!-- piexif.min.js is needed for auto orienting image files OR when restoring exif data in resized images and when you
  wish to resize images before upload. This must be loaded before fileinput.min.js -->
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/js/plugins/piexif.min.js"
      type="text/javascript"
    ></script>
    <!-- sortable.min.js is only needed if you wish to sort / rearrange files in initial preview. 
  This must be loaded before fileinput.min.js -->
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/js/plugins/sortable.min.js"
      type="text/javascript"
    ></script>
    <!-- popper.min.js below is needed if you use bootstrap 4.x (for popover and tooltips). You can also use the bootstrap js 
 3.3.x versions without popper.min.js. -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <!-- bootstrap.min.js below is needed if you wish to zoom and preview file content in a detail modal
  dialog. bootstrap 4.x is supported. You can also use the bootstrap js 3.3.x versions. -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
      crossorigin="anonymous"
    ></script>
    <!-- the main fileinput plugin file -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/js/fileinput.min.js"></script>
    <!-- following theme script is needed to use the Font Awesome 5.x theme (`fas`) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/themes/fas/theme.min.js"></script>
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
    <div class="container">
      <form
        name="writeForm"
        id="writeForm"
        enctype="multipart/form-data"
        accept-charset="UTF-8"
      >
        <div class="form-group">
          <input id="boardNo" type="hidden" value="${boardDto.boardNo}" />
          <input
            id="title"
            type="text"
            class="form-control"
            value="${boardDto.title}"
            maxlength="50"
            required
          />
        </div>
        <div class="form-group">
          <input
            type="file"
            class="custom-file-input"
            id="customFile"
            name="files[]"
            multiple="multiple"
          />
        </div>
        <div class="form-group">
          <textarea
            id="content"
            class="form-control summernote"
            rows="3"
            required
          >
            ${boardDto.content}
          </textarea>
        </div>
        <hr />
        <div>
          <strong>Attachments: </strong>
          <br />
          <c:forEach var="file" items="${fileList}">
            <div class="form-group">
              <!-- <button
                id="btn-deleteFile"
                onclick="board.deleteFileDto(${boardDto.boardNo}, ${file.fileNo})"
                class="btn btn-danger btn-sm"
              > -->
              <button
                id="btn-deleteFile${file.fileNo}"
                type="button"
                onclick="prepare_fileDeletion(${file.fileNo})"
                class="btn btn-danger btn-sm"
              >
                ${file.origFileName}
                <svg
                  width="1em"
                  height="1em"
                  viewBox="0 0 16 16"
                  class="bi bi-x-square-fill"
                  fill="currentColor"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fill-rule="evenodd"
                    d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm3.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z"
                  />
                </svg>
              </button>
            </div>
          </c:forEach>
        </div>
        <hr />
        <!-- <div class="form-group">
          <div class="col-sm-10">
            <input
              name="files"
              multiple="multiple"
              type="file"
              class="custom-file-input"
              id="customFile"
            />
            <label class="custom-file-label" for="customFile"
              >Choose files</label
            >
          </div>
        </div> -->
      </form>
      <div class="text-right">
        <button id="btn-updatePost" class="btn btn-warning btn-sm">
          UPDATE
        </button>
      </div>
    </div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <script>
      $(".summernote").summernote({
        placeholder: "Write Something...",
        tabsize: 2,
        height: 300,
      });
    </script>
    <script>
      $(document).ready(function () {
        $("#customFile").fileinput({
          uploadUrl: "/upload",
          maxFileCount: 5,
          showBrowse: false,
          browseOnZoneClick: true,
        });
      });
    </script>
    <script>
      var escaped = true;
      $(document).ready(function () {
        $(window).on("beforeunload", function () {
          if (escaped) return warning();
        });
        function warning() {
          $.ajax({
            type: "GET",
            url: "/escaped",
            success: function (data) {
              alert(data);
            },
          });
        }
        $("#btn-updatePost").on("click", function () {
          escaped = false;
          $(window).off("beforeunload");
        });
      });
    </script>
    <script>
      function prepare_fileDeletion(id) {
        $("#btn-deleteFile" + id).hide();
        var fileDeleteBtn = document.getElementById("btn-deleteFile" + id);
        var form = document.getElementById("writeForm");
        var input = document.createElement("input");
        input.className = "fileToBeDeleted";
        input.type = "hidden";
        input.id = "file" + id;
        input.value = id;
        console.log(input);
        form.appendChild(input);
      }
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/board.js"></script>
    <br />
    <br />
    <br />
    <br />
    <br />
    <%@ include file="/WEB-INF/view/footer.jsp" %>
  </body>
</html>
