<!DOCTYPE html>
<html>
<head>
    <title>Bookshelf</title>
</head>

<body>
<div>
    <h1>Create bookshelf</h1>
    <g:form action="save">
        <g:render template="form"/>
        <g:submitButton name="create" value="Create"/>
    </g:form>
</div>
<br/>
<g:link action="index">Back</g:link>
</body>
</html>
