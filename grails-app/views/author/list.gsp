<%@ page import="lybrary.Author" %>
<!DOCTYPE html>
<html>
<head>
    <title>Author</title>
    <style>
    table {
        border-collapse: collapse;
        min-width: 600px;
    }
    tr {
        border-bottom: 1px solid black;
    }
    a {
        margin-right: 10px;
    }
    </style>
</head>
<body>

<div>
    <h1>Authors</h1>
    <g:link action="create">Create</g:link><br/><br/>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Books</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${Author.list()}" var="author">
            <tr>
                <td><g:link action="show" id="${author.id}">${author.name}</g:link></td>
                <td>
                    <g:each in="${author.book.asList()}" var="book">
                        <g:link controller="book" action="show" id="${book.id}">${book.name}</g:link><br/>
                    </g:each>
                </td>
                <td><g:link action="delete" id="${author.id}">Remove</g:link><g:link action="edit" id="${author.id}">Edit</g:link></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>
