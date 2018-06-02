<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/3/17
  Time: 上午 08:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <script>

        function hint() {

            //alert("aaa")

            var nameElement = document.getElementById("name");
            //获取输入的信息
            var nameValue = nameElement.value;
            //如果文本框不没有数据时，把div隐藏，且不向服务器发送请求
            if (nameValue == "") {
                div.style.display = "none";
                return;
            }

            //alert("bbb");
            //隐藏提示框
            var div = document.getElementById("content");
            div.innerHTML = "";

            //alert("ccc");
            //1.获取XMLHttpRequest对象
            var xmlhttp = new XMLHttpRequest();

            //2.绑定回调函数
            xmlhttp.onreadystatechange = function () {

                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

                    // alert(xmlhttp.responseText);
                    var ss = xmlhttp.responseText.split(",");
                    //	 alert(xmlhttp.responseText);
                    //	var ss =eval('('+xmlhttp.responseText+')');

                    // alert(ss);
                    var childdiv = "";

                    for (var i = 0; i < ss.length; i++) {
                        childdiv = childdiv +
                            "<div  onmouseover='changeBackground_over(this)' " +
                            "onmouseout='changeBackground_out(this)' " +
                            "onclick='fillNameValue(this)'>" + ss[i] + "</div>";
                    }

                    div.innerHTML = childdiv;
                    div.style.display = "block";
                }
            };
            //3.open
            xmlhttp.open("GET", "${pageContext.request.contextPath}/searchHint?key=" + nameValue);
            //	+ window.encodeURIComponent(nameValue, "utf-8")+ "&time=" + new Date().getTime()
            //4.send
            xmlhttp.send(null);
        }

        function changeBackground_over(div) {
            div.style.background = "gray";
        }

        function changeBackground_out(div) {
            div.style.background = "white";
        }

        function fillNameValue(subDiv) {
            document.getElementById("name").value = subDiv.innerHTML;
            document.getElementById("content").style.display = "none";
        }

        function dispear(div) {
            div.style.display = "none";
        }


    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/search" method="post">
    <input id="name" type="text" name="name" onkeyup="hint()"/>
    <input type="submit">

</form>
<div id="content"
     style="background-color:white;width:178px;  border:1px solid red; position: absolute; left:10px;top:55px "
     onmouseleave="dispear(this)"
>
</div>
</body>
</html>

