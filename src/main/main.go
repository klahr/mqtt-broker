package main

import (
	"fmt"
	"net/http"
	"os/exec"
	"text/template"
)

type RequestHandler struct {
	mTemplates *template.Template
}

func (this RequestHandler) initialize() {
	this.mTemplates, _ = template.ParseFiles("index.gtpl")

	http.HandleFunc("/", this.index)
	http.HandleFunc("/send_message", this.sendMessage)
}

func (this RequestHandler) run() {
	if err := http.ListenAndServe(":8080", http.DefaultServeMux); err != nil {
		print(err.Error())
	}
}

func (this RequestHandler) index(w http.ResponseWriter, r *http.Request) {
	this.mTemplates.ExecuteTemplate(w, "index", nil)
}

func (this RequestHandler) sendMessage(w http.ResponseWriter, r *http.Request) {
	host := r.URL.Query().Get("host")
	port := r.URL.Query().Get("port")
	topic := r.URL.Query().Get("topic")
	message := r.URL.Query().Get("message")

	if host == "" {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("{\"error\":\"missing 'host'\"}"))
		return
	}
	if port == "" {
		port = "1883"
	}
	if topic == "" {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("{\"error\":\"missing 'topic'\"}"))
		return
	}
	if message == "" {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("{\"error\":\"missing 'message'\"}"))
		return
	}

	cmd := fmt.Sprintf("/usr/bin/mosquitto_pub -h %s -p %s -t %s -m \"%s\"", host, port, topic, message)
	print(cmd)
	_, err := exec.Command("mosquitto_pub", "-h", host, "-p", port, "-t", topic, "-m", message).Output()
	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("{\"error\":\"" + err.Error() + "\"}"))
		return
	}

	w.Write([]byte("{\"data\":\"Message was successfully published!\"}"))
}

func main() {
	rh := RequestHandler{}
	rh.initialize()
	rh.run()
}
