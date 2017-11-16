<%@ page import="lybrary.Book" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book</title>
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
    <h1>Book #${book.id}</h1>
    <br/>
    <b>Name:</b> ${book.name}<br/><br/>
    <b>Author:</b> <g:link controller="author" action="show" id="book.author.id">${book.author.name}</g:link><br/><br/>
</div>
<g:link action="index">Back</g:link><g:link action="edit" id="${book.id}">Edit</g:link>
</body>
</html>
