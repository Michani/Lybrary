
<%@ page import="lybrary.Author" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'author.label', default: 'Author')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<style type="text/css" media="screen">
			#list-author {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				height: 20em;
				float: left;
				overflow: scroll;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #list-author {
			display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#list-author ul {
			font-size: 0.9em;
			list-style-type: none;
			margin-bottom: 0.6em;
			padding: 0;
			}

			#list-author li {
			line-height: 1.3;
			}

			#list-author h1 {
			text-transform: uppercase;
			font-size: 1.1em;
			margin: 0 0 0.3em;
			}

			h2 {
			margin-top: 1em;
			margin-bottom: 0.3em;
			font-size: 1em;
			}

			p {
			line-height: 1.5;
			margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
			#list-author {
			display: none;
			}
			}
		</style>
	</head>
	<body>
		<a href="#list-author" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-author" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
				<g:each in="${authorInstanceList}" status="i" var="authorInstance">
						<li><g:link action="show" id="${authorInstance.id}">${fieldValue(bean: authorInstance, field: "name")}</g:link></li>
				</g:each>
		</div>
	</body>
</html>
