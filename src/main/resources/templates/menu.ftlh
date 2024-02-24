<!DOCTYPE html>
<html lang="ru" xmlns= "http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Menu</title>
</head>
<body>
<h1>Menu</h1><hr>
<#list products as product>
    <div>
        <p><b>${product.name}</b> ${product.price} $$$ |  Поторопитесь, осталось всего: ${product.amount} штук  </div>
    <hr>
</#list>

<a href="/design">Make order</a>
</body>
</html>