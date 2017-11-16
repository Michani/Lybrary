<%@ page import="lybrary.Book" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bookshelf</title>
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
    <h1>Books</h1>
    <g:link action="create">Create</g:link><br/><br/>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Author</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${Book.list()}" var="book">
            <tr>
                <td><g:link action="show" id="${book.id}">${book.name}</g:link></td>
                <td>
                    <g:each in="${book.author}" var="author">
                        <g:link controller="book" action="show" id="${book.author.id}">${book.author.name}</g:link><br/>
                    </g:each>
                </td>
                <td><g:link action="delete" id="${book.id}">Remove</g:link><g:link action="edit" id="${book.id}">Edit</g:link></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>
