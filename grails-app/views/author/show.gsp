<!DOCTYPE html>
<html>
<head>
    <title>Author</title>
    <style>
        span {
            margin-left: 50px;
            margin-right: 20px;
        }
        a {
            margin-right: 20px;
        }
    </style>
</head>

<body>

<div>
    <h1>Author #${author.id}</h1>
    <br/>
    <b>Name:</b> ${author.name}<br/><br/>
    <b>Books:</b>
    <br/>
    <g:each in="${author.book.asList()}" var="book">
        <g:link controller="book" action="show" id="${book.id}">${book.name}</g:link><br/>
    </g:each>
    <br/>
</div>
<g:link action="index">Back</g:link><g:link action="edit" id="${author.id}">Edit</g:link>
</body>
</html>
