<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>新增用户信息</title>
</head>
<body>
<form id="addForm" method="post" action="${request.contextPath}/user/add">
    <table border="1" bordercolor="#cccccc" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="4" bgcolor="#1d98db"><font size="3">新增用户信息</font></td>
        </tr>
        <tr>
            <td>姓名:</td>
            <td>
                <input type="text" name="name">
            </td>
            <td>角色id:</td>
            <td>
                <input type="text" name="roleId">
            </td>
        </tr>
    </table>
    <p>
        <button type="submit">保存</button>&nbsp;&nbsp;
        <button onclick="history.go(-1);return false;">返回</button>
    </p>
</form>
</body>
</html>