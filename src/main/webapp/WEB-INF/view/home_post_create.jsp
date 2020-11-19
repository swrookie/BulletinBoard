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
    <title>Bulletin Board</title>
    <!-- Bootstrap CSS & JS -->
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
      integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
      crossorigin="anonymous"
    />
    <script
      src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
      integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
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
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="/">Bulletin Board</a>
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
                <form class="px-4 py-3" method="POST" action="${pageContext.request.contextPath}/do_login">
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
                <a class="dropdown-item" href="#">New around here? Sign up</a>
                <a class="dropdown-item" href="#">Forgot password?</a>
              </sec:authorize>
              <sec:authorize access="isAuthenticated()">
                <a class="dropdown-item" href="#">Profile</a>
                <div class="dropdown-divider"></div>
                <a
                  class="dropdown-item"
                  href="${pageContext.request.contextPath}/do_logout"
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
      <form
        method="POST"
        action="${pageContext.request.contextPath}/do_create"
        enctype="multipart/form-data"
      >
        <div class="form-group">
          <input
            type="text"
            class="form-control"
            placeholder="Enter title"
            id="title"
            name="title"
            maxlength="50"
          />
        </div>
        <div class="form-group">
          <textarea
            class="form-control summernote"
            rows="3"
            id="content"
            name="content"
          ></textarea>
        </div>
        <div class="form-group">
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
        </div>
        <input
          type="submit"
          class="btn btn-primary pull-right"
          name="page"
          value="POST"
        />
      </form>
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
    <br />
    <footer>
      <div class="jumbotron text-center" style="margin-bottom: 0">
        <p>Bulletin Board Project by swrookie</p>
        <p>dpdjflr@gmail.com</p>
      </div>
    </footer>
  </body>
</html>
