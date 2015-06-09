<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags"%>
 
<div class="col-sm-9 col-md-10 main">
<sec:authentication property="principal.username" />
 
<sec:authorize access="hasRole('ROLE_ADMIN')">
<h1 class="page-header">YOU ARE ADMIN.</h1>
</sec:authorize>
 
<sec:authorize access="hasRole('ROLE_USER')">
<h1 class="page-header">YOU ARE USER.</h1>
</sec:authorize>
 
<h2 class="sub-header">Section title</h2>
<div class="table-responsive">
<table class="table table-striped">
<thead>
<tr>
<th>#</th>
<th>Header</th>
</tr>
</thead>
<tbody>
<tr>
<td>1,001</td>
<td>Lorem</td>
</tr>
<tr>
<td>1,002</td>
<td>amet</td>
</tr>
</tbody>
</table>
</div>
</div>