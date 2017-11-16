<!DOCTYPE html>
<html>
<head>
    <title>Bookshelf</title>
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
    <h1>Bookshelf #${bookshelf.id}</h1>
    <br/>
    <b>Name:</b> ${bookshelf.name}<br/><br/>
    <b>Books:</b>
    <g:form action="addBook" style="display: inline-block">
        <g:hiddenField name="id" value="${bookshelf.id}" />
        <g:select name="bookId" optionKey="id" from="${lybrary.Book.list()}" />
        <g:submitButton name="add" value="Add"/>
    </g:form>
    <br/>
    <g:each in="${bookshelf.books}" var="book" status="i">
        <span>${book.name}</span><g:link action="removeBook" id="${bookshelf.id}" params="[bookIndex: i]">remove</g:link><br/>
    </g:each>
    <br/>
</div>
<g:link action="index">Back</g:link><g:link action="edit" id="${bookshelf.id}">Edit</g:link>
</body>
</html>
