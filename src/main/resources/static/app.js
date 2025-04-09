const messagesDiv = document.getElementById("messages");
const messageInput = document.getElementById("messageInput");
const sendBtn = document.getElementById("sendBtn");

let conversationId = null;

function addMessage(text, type) {
const msgDiv = document.createElement("div");
msgDiv.classList.add("message", type);
msgDiv.textContent = text;
messagesDiv.appendChild(msgDiv);
messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

function sendMessage() {
const userText = messageInput.value.trim();
if (!userText) return;

addMessage("Vous : " + userText, "user");

const payload = { message: userText };

if (conversationId) {
  payload.conversationId = conversationId;
}

fetch("/chat", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify(payload)
})
.then(response => {
  if (!response.ok) {
    throw new Error("Erreur lors de la communication avec l'API");
  }
  return response.json();
})
.then(data => {
  conversationId = data.conversationId;
  addMessage("Mickaël Andrieu (via l'IA) : " + data.generation, "bot");
})
.catch(err => {
  console.error(err);
  addMessage("Oups, une erreur est survenue...", "bot");
});

messageInput.value = "";
messageInput.focus();
}

sendBtn.addEventListener("click", sendMessage);
messageInput.addEventListener("keydown", (e) => {
if (e.key === "Enter") {
  sendMessage();
}
});