<%@ page import="lybrary.Book" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book</title>
</head>

<body>

<div>
    <h1>Edit book #${book.id}</h1>
    <g:form action="save">
        <g:hiddenField name="id" value="${book.id}"/>
        <g:render template="form"/>
        <g:actionSubmit action="save" value="Update"/>
    </g:form>
</div>
<br/>
<g:link action="index">Back</g:link>
</body>
</html>
