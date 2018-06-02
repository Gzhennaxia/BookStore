<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css'/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--webfonts-->
    <!--//webfonts-->
    <script src="js/setDate.js" type="text/javascript"></script>
    <script>

        //检查用户名是否存在
        function isValidUsername() {
            var usernameInput = document.getElementById("username");
            var username = usernameInput.value;

            //1、创建XMLHttpRequest对象
            var xhr = new XMLHttpRequest();


            //2、注册状态监控回调函数
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var hint = document.getElementById("hint");
                        hint.innerText = xhr.responseText;
                        var text = hint.innerText;
                        if (text == "ok") {
                            hint.style.color = "#0f0";
                        } else {
                            hint.style.color = "#f00";
                        }
                    }
                }
            };

            //3、建立与服务器的异步连接
            xhr.open("get", "${pageContext.request.contextPath}/user/UserServlet?op=isValidUsername&username=" + username, true);
            //4、发出异步请求
            xhr.send(null);

        }

        //检查密码（密码为6位数字）
        function checkPassword() {
            var passwordInput = document.getElementById("password").value;
            return /^\d{6}$/.test(passwordInput);
        }
    </script>
</head>

<body>
<div class="main" align="center">
    <div class="header">
        <h1>创建一个免费的新帐户！</h1>
    </div>
    <p></p>
    <form method="post" action="${pageContext.request.contextPath }/user/UserServlet">
        <input type="hidden" name="op" value="register"/>
        <ul class="left-form">
            <li>
                ${msg.error.username }<br/>
                <input type="text" name="username" id="username" placeholder="用户名" value="${msg.username}" required="required"
                       onblur="isValidUsername()"/><br/>
                <span id="hint" style=""></span>
                <a href="#" class="icon ticker"> </a>
                <div class="clear"></div>
            </li>
            <li>
                ${msg.error.nickname }<br/>
                <input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
                <a href="#" class="icon ticker"> </a>
                <div class="clear"></div>
            </li>
            <li>
                ${msg.error.email }<br/>
                <input type="text" name="email" placeholder="邮箱" value="${msg.email}" required="required"/>
                <a href="#" class="icon ticker"> </a>
                <div class="clear"></div>
            </li>
            <li>
                ${msg.error.password }<br/>
                <input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
                <a href="#" class="icon into"> </a>
                <div class="clear"></div>
            </li>
            <li>
                ${msg.error.birthday }<br/>
                <input type="text" placeholder="出生日期" name="birthday" value="${msg.birthday}" size="15"/>
                <div class="clear"></div>
            </li>
            <li>
                <input type="submit" value="创建账户">
                <div class="clear"></div>
            </li>
        </ul>

        <div class="clear"></div>

    </form>

</div>
<!-----start-copyright---->

<!-----//end-copyright---->

</body>

</html>