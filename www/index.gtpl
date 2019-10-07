{{define "index"}}
<html>

<head>
    <style>
        body {
            background-color: #c9f391;
        }

        table {
            background-color: white;
            border-radius: 24px;
            border: none;
            padding: 16px;
            box-shadow: #0000001f 2px 2px 0px 2px;
        }

        p {
            font-family: verdana;
            font-size: 14px;
            font-weight: bold;
        }

        h1 {
            margin-top: 24px;
            color: #03a9f4;
            text-shadow: #4c4c4c 2px 2px 0px;
        }

        input {
            padding: 4px;
            font-family: monospace;
        }

        textarea {
            padding: 4px;
            font-family: monospace;
            resize: none;
        }

        button {
            padding: 8px;
            background-color: #03A9F4;
            border: 2px solid #00000038;
            border-radius: 4px;
            color: white;
            font-weight: bold;
            font-size: 10px;
        }

    </style>
</head>

<body>

<script type="text/javascript">
    function onPublish() {
        var host = document.getElementById("host").value;
        var port = document.getElementById("port").value;
        var topic = document.getElementById("topic").value;
        var message = document.getElementById("message").value;

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(data) {
            if (this.readyState == 4) {
                var result = document.getElementById("result");
                var json = JSON.parse(this.responseText);
                if (this.status == 200) {
                    result.style.color = "green";
                    result.innerHTML = json.data;
                } else {
                    result.style.color = "red";
                    result.innerHTML = "ERROR: " + json.error;
                }
            }
        };
        xhttp.open("POST", `/send_message?host=${host}&port=${port}&topic=${topic}&message=${message}`, true);
        xhttp.send(); 
    }

    window.onkeypress = function(e) {
        if ((e.ctrlKey || e.metaKey) && (e.keyCode == 13 || e.keyCode == 10)) {
            onPublish();
        }
    }
</script>

<center>

<h1>MQTT Broker</h1>

<div>
    <table>
        <tr>
            <td>
                <p>Host and port</p>
            </td>
            <td>
                <input id="host" placeholder="host" value="192.168.0.1"></input>
            </td>
            <td>
                <input id="port" placeholder="port" value="1883"></input>
            </td>
        </tr>
        <tr>
            <td>
                <p>Topic</p>
            </td>
            <td colspan="2">
                <input id="topic" style="width: 100%;"></input>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <textarea id="message" style="width: 100%;" rows="16" placeholder="Message"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="3" style="text-align: right;">
                <button onclick="onPublish()">PUBLISH</button>
            </td>
        </tr>
    </table>
</div>

<p id="result"></p>

</center>

</body>
</html>

{{end}}
