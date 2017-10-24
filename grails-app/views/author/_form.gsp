<%@ page import="lybrary.Author" %>



<div class="fieldcontain ${hasErrors(bean: authorInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="author.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${authorInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: authorInstance, field: 'book', 'error')} ">
	<label for="book">
		<g:message code="author.book.label" default="Book" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${authorInstance?.book?}" var="b">
    <li><g:link controller="book" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="book" action="create" params="['author.id': authorInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'book.label', default: 'Book')])}</g:link>
</li>
</ul>

</div>

