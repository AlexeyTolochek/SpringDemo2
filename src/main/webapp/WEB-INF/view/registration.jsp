<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration form</title>
</head>
<body>

<h1>Java Mentor!</h1>

<h2>Форма регистрации</h2><br/>

<h2>${requestScope.message}</h2>

<form method='post'>
    <p><b>Введите данные</b></p>

    <table width='100%' cellspacing='0' cellpadding='4'>
        <tr>
            <td align='right' width='100'>Имя</td>
            <td><input type='text' name='name' maxlength='50' size='20' value= "${user.name}"></td>
        </tr>
        <tr>
            <td align='right'>login</td>
            <td><input type='text' name='login' maxlength='50' size='20' value="${user.login}"></td>
        </tr>
        <tr>
            <td align='right'>Пароль</td>
            <td><input type='password' name='password' maxlength='50' size='20' value="${user.password}"></td>
        </tr>
        <tr>
            <td align='right'>Адрес</td>
            <td><input type='text' name='address' maxlength='50' size='20' value="${user.address}"></td>
        </tr>
    </table>
    <input type='submit' value='Подтвердить' name='Ok' formaction="${pageContext.request.contextPath}/reg/user"><br>
</form>




</body>

</html>
