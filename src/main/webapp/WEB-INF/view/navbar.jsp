<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags" %> <%@ taglib
uri="http://www.springframework.org/tags" prefix="spring" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html>
<html>
<body>
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
                  <input type="text" class="form-control" name="userName" id="userName" placeholder="Username" required>
                </div>
                <div class="form-group">
                  <label for="exampleDropdownFormPassword1">Password</label>
                  <input type="password" class="form-control" name="password" id="password" placeholder="Password" required>
                </div>
                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                  <font color="red">
                    <p class="error">Login Failed: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
                    <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
                  </font>
                </c:if>
                <div class="form-group">
                  <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="dropdownCheck">
                    <label class="form-check-label" for="dropdownCheck">
                      Remember me
                    </label>
                  </div>
                </div>
                <button type="submit" class="btn btn-primary" name="signIn">Sign in</button>
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
</body>
</html>
