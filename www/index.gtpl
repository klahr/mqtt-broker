{{define "index"}}
<html>

<head>
    <style>
        body {
            background-color: #3e434c;
        }

        table {
            background-color: #3c3f41;
            border-radius: 12px;
            border: 4px solid #2b2b2b;
            padding: 16px;
        }

        p {
            font-family: sans-serif;
            font-size: 14px;
            font-weight: bold;
            color: #bbbbbb;
        }

        h1 {
            margin-top: 24px;
            color: #499c54;
            font-family: sans-serif;
        }

        input {
            padding: 4px;
            font-family: monospace;
            background-color: #2b2b2b;
            color: #a6b5bf;
            border: none;
        }

        textarea {
            padding: 4px;
            font-family: monospace;
            resize: none;
            background-color: #2b2b2b;
            color: #a6b5bf;
            border: none;
        }

        button {
            padding: 8px;
            background-color: #365880;
            border: 2px solid #4c708c;
            border-radius: 4px;
            color: #babab6;
            font-weight: bold;
            font-size: 14px;
        }

        button:active {
            border: 2px solid #43688c;
            box-shadow: inset white 0 0 2px;
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

        result.style.color = "white";
        result.innerHTML = "Please wait...";

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(data) {
            if (this.readyState == 4) {
                var result = document.getElementById("result");
                var json = JSON.parse(this.responseText);
                if (this.status == 200) {
                    result.style.color = "#499c54";
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
