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
    <h1>Bookshelves</h1>
    <g:link action="create">Create</g:link><g:link action="generate">Generate</g:link><br/><br/>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Books</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${bookshelves}" var="bookshelf">
            <tr>
                <td><g:link action="show" id="${bookshelf.id}">${bookshelf.name}</g:link></td>
                <td>
                    <g:each in="${bookshelf.books}" var="book">
                        ${book.name}<br/>
                    </g:each>
                </td>
                <td><g:link action="delete" id="${bookshelf.id}">Remove</g:link><g:link action="edit" id="${bookshelf.id}">Edit</g:link></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>
