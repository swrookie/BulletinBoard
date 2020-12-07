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
    <!-- bootstrap 4.x is supported. You can also use the bootstrap css 3.3.x versions -->
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
    <%@ include file="/WEB-INF/view/navbar.jsp" %>
    <br />
    <div class="container">
      <form
        name="writeForm"
        id="writeForm"
        enctype="multipart/form-data"
        accept-charset="UTF-8"
      >
        <sec:authentication var="username" property="principal.username" />
        <input type="hidden" id="author" value="${username}" />
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
            class="form-control summernote"
            rows="3"
            id="content"
          ></textarea>
        </div>
        <!-- <div class="custom-file">
          <input
            type="file"
            class="custom-file-input"
            id="customFile"
            name="files[]"
            multiple="multiple"
          />
          <label class="custom-file-label" id="file-label" for="customFile"
            >Choose files</label
          >
        </div> -->
      </form>
      <br />
      <div class="text-right">
        <button type="button" id="btn-createPost" class="btn btn-primary">
          POST
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
          theme: "fas",
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
        $("#btn-createPost").on("click", function () {
          escaped = false;
          $(window).off("beforeunload");
        });
      });
    </script>
    <!-- <script>
      $(".custom-file-input").on("change", function () {
        // var fileName = $(this).val().split("\\").pop();
        var input = document.getElementById("customFile");
        var files = "";
        for (var i = 0; i < input.files.length; ++i) {
          files += input.files.item(i).name + " ";
        }
        $(this).siblings(".custom-file-label").addClass("selected").html(files);
      });
    </script> -->
    <script src="${pageContext.request.contextPath}/resources/js/board.js"></script>
    <br />
    <br />
    <br />
    <br />
    <br />
    <%@ include file="/WEB-INF/view/footer.jsp" %>
  </body>
</html>
