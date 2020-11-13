<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Registration</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Registration</title>

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
    <style>
      html,
      body {
        height: 100%;
      }

      html {
        display: table;
        margin: auto;
      }

      body {
        display: table-cell;
        vertical-align: middle;
      }
    </style>
  </head>

  <body>
    <form method="POST" action="${pageContext.request.contextPath}/do_register">
      <div class="form-row">
        <div class="col-md-6 mb-3">
          <label for="validationCustom01">First name</label>
          <input
            type="text"
            name="firstName"
            class="form-control"
            id="validationCustom01"
            required
          />
          <div class="valid-feedback">Looks good!</div>
        </div>
        <div class="col-md-6 mb-3">
          <label for="validationCustom02">Last name</label>
          <input
            type="text"
            name="lastName"
            class="form-control"
            id="validationCustom02"
            required
          />
          <div class="valid-feedback">Looks good!</div>
        </div>
      </div>
      <div class="form-row"></div>
      <div class="form-row">
        <div class="form-group col-md-12">
          <label for="inputEmail4">Username</label>
          <input
            type="text"
            name="userName"
            class="form-control"
            id="inputUsername"
          />
        </div>
      </div>
      <div class="form-row">
        <div class="form-group col-md-12">
          <label for="inputEmail4">Email</label>
          <input
            type="email"
            name="email"
            class="form-control"
            id="inputEmail4"
          />
        </div>
      </div>
      <div class="form-row">
        <div class="form-group col-md-6">
          <label for="inputPassword4">Password</label>
          <input
            type="password"
            name="password"
            class="form-control"
            id="inputPassword4"
          />
        </div>
        <div class="form-group col-md-6">
          <label for="inputPassword5">Password Confirm</label>
          <input type="password" name="passwordConfirm" class="form-control
          id="inputPassword5" />
        </div>
      </div>
      <div class="col text-center">
        <button type="submit" class="btn btn-primary">Register</button>
      </div>
    </form>
  </body>
</html>
