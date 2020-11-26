<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %> <%@ taglib prefix="spring"
uri="http://www.springframework.org/tags" %>

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
    <form:form
      id="signUpForm"
      method="POST"
      action="${pageContext.request.contextPath}/sign_up"
      modelAttribute="signUpForm"
    >
      <div class="form-row">
        <div class="col-md-6 mb-3">
          <label for="firstName">First name</label>
          <input
            type="text"
            class="form-control"
            id="firstName"
            name="firstName"
            required
            oninvalid="setCustomValidity('Please enter first name')"
            oninput="setCustomValidity('')"
          />
        </div>
        <div class="col-md-6 mb-3">
          <label for="lastName">Last name</label>
          <input
            type="text"
            class="form-control"
            id="lastName"
            name="lastName"
            required
            oninvalid="setCustomValidity('Please enter last name')"
            oninput="setCustomValidity('')"
          />
        </div>
      </div>
      <div class="form-row">
        <label for="userName">Username</label>
        <div class="form-group col-md-12">
          <input
            type="text"
            class="form-control"
            id="userName"
            name="userName"
            required
            oninvalid="this.setCustomValidity('Please enter user name')"
            oninput="setCustomValidity('')"
          />
          <font color="red">
            <form:errors path="userName" />
          </font>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group col-md-12">
          <label for="email">Email</label>
          <input
            type="email"
            class="form-control"
            id="email"
            name="email"
            required
            oninvalid="this.setCustomValidity('Please enter email')"
            oninput="setCustomValidity('')"
          />
          <font color="red">
            <form:errors path="email" />
          </font>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group col-md-6">
          <label for="password">Password</label>
          <input
            type="password"
            class="form-control"
            id="password"
            name="password"
            required
            oninvalid="this.setCustomValidity('Please enter password')"
            oninput="setCustomValidity('')"
          />
          <font color="red">
            <form:errors path="password" />
          </font>
        </div>
        <div class="form-group col-md-6">
          <label for="passwordConfirm">Password Confirm</label>
          <input
            type="password"
            class="form-control"
            id="passwordConfirm"
            name="passwordConfirm"
            required
            oninvalid="this.setCustomValidity('Please re-enter password')"
            oninput="setCustomValidity('')"
          />
          <font color="red">
            <form:errors path="passwordConfirm" />
          </font>
        </div>
      </div>
      <div class="col text-center">
        <button
          id="btn-createMember"
          type="submit"
          class="btn btn-primary"
          oninvalid="alert('Sign Up Failed..')"
        >
          Sign Up
        </button>
      </div>
    </form:form>
  </body>
</html>
