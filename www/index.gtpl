{{define "index"}}

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
                    result.innerHTML = "error: " + json.error;
                }
            }
        };
        xhttp.open("POST", `/send_message?host=${host}&port=${port}&topic=${topic}&message=${message}`, true);
        xhttp.send(); 
    }
</script>

<h1>MQTT Broker</h1>

<input id="host" placeholder="host" value="192.168.0.1"></input>
<input id="port" placeholder="port" value="1883"></input>
<br/>
<input id="topic" placeholder="topic"></input>
<br/>
<input id="message" placeholder="message"></input>
<br/>
<button onclick="onPublish()">Publish</button>

<p id="result"></p>

{{end}}
