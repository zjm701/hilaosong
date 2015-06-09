<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<form class="form-signin" role="form" action="/delivery/j_spring_security_check"
    method="post">
    <h2 class="form-signin-heading">Please sign in</h2>
 
    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            <p>Invalid username and password.
            <p>
        </div>
    </c:if>
 
    <c:if test="${param.logout != null}">
        <div class="alert alert-warning" role="alert">
            <p>You have been logged out.</p>
        </div>
    </c:if>
 
    <input name="j_username" id="j_username" type="email"
        class="form-control" placeholder="Email address" required autofocus>
    <input name="j_password" id="j_password" type="password"
        class="form-control" placeholder="Password" required>
    <div class="checkbox">
        <label> <input type="checkbox" value="remember-me">
            Remember me
        </label>
    </div>
    <input type="hidden" name="${_csrf.parameterName}"
        value="${_csrf.token}" />
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign
        in</button>
</form>