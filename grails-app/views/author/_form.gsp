<div>
    <label for="name"></label>
    <g:textField name="name" required="" value="${author?.name}"/>
    <label for="book"></label>
    <br/>
    <g:each in="${author.book.asList()}" var="book">
        <g:link controller="book" action="show" id="${book.id}">${book.name}</g:link><br/>
    </g:each>
</div>

