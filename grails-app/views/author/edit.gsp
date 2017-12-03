<%@ page import="lybrary.Book" %>
<!DOCTYPE html>
<html>
<head>
    <title>Author</title>
</head>

<body>

<div>
    <h1>Edit author ${author.name}</h1>
    <g:form action="save">
        <g:hiddenField name="id" value="${author.id}"/>
        <g:render template="form"/>
        <g:actionSubmit action="save" value="Update"/>
    </g:form>
</div>
<br/>
<g:link action="index">Back</g:link>
</body>
</html>
