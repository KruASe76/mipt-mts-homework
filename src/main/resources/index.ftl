<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Главная страница</title>
  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/gh/yegor256/tacit@gh-pages/tacit-css.min.css"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>

<body>

<h1>Статьи</h1>
<table>
  <tr>
    <th>ID</th>
    <th>Заголовок</th>
    <th>Теги</th>
    <th>Количество комментариев</th>
  </tr>
    <#list articles as article>
      <tr>
        <td>${article.id}</td>
        <td>${article.title}</td>
        <td>${article.tags?replace(" ", "<br>")}</td>
        <td>${article.comment_count}</td>
      </tr>
    </#list>
</table>

</body>

</html>
