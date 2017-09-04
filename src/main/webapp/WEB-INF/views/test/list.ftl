<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>列表</title>
</head>
<body>
<#if tests?size gt 0>
    <table border="0" bordercolor="#cccccc" cellpadding="0" cellspacing="0">
        <tr>
            <th class="al_list1">编号</th>
            <th class="al_list3">名称</th>
            <th class="al_list1">用户ID</th>
            <th class="al_list3">操作</th>
        </tr>
        <#list tests as obj>
        <tr>
            <td class="al_list1">
                ${obj.id}
            </td>
            <td class="al_list3">${obj.name}</td>
            <td class="al_list1">${obj.userId}</td>
            <td class="al_list3">
                <a href="${request.contextPath}/test/toEdit?id=${obj.id}">修改</a>&nbsp;
            </td>
        </tr>
        </#list>
    </table>
</#if>
</body>
</html>