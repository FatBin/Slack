<%@ page  language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<body>

<p>studentName is ${person.name}</p>
<p>studentDescription is ${person.description}</p>
<p>
<form action="/applicationSecurity/updateDescription" method="POST" onsubmit="false">
    update description: <input type="text" name="description">
    <input type="submit" value="submit"/>
    <input type="hidden" name="name" value=${person.name}>
</form>

<form action="/applicationSecurity/countFibonacci" method="POST" onsubmit="false">
    count  Fibonacci: <input type="text" name="Fibonacci">
    <input type="submit" value="submit"/>
</form>
</p>
</body>
</html>

