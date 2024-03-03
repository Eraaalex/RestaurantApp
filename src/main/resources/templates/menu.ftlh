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
        <p><b>${product.name}</b> ${product.price} $$$ |  Be ready, in stock only: ${product.amount} pieces  | Time to cook in seconds: ${product.time} </div>
    <hr>
</#list>

<a href="/design">Make order</a>
</body>
</html>