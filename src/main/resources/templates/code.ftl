<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <title>${title}</title>
    <link rel="stylesheet" href="/css/code.css">

    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.0.0/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.0.0/build/highlight.min.js"></script>
    <script>
        hljs.initHighlightingOnLoad();
    </script>

</head>

<body>
<#list codes as code>
<span id="load_date">${code.dateTime}</span>
<pre id="code_snippet"><code>${code.code}</code></pre>
<#if code.restrictedTime> <span id="time_restriction">Time left: ${code.timeLeft}</span>
<br> </#if>
<#if code.restrictedViews> <span id="views_restriction">Views left: ${code.viewsLeft}</span> </#if>
<#sep><hr></#sep>
</#list>

</body>
</html>