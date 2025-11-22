const chatContainer = document.getElementById("chat-container");
const chatForm = document.getElementById("chatForm");
const chatInput = document.getElementById("chatInput");

chatForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const inputQuestion = chatInput.value.trim();
    if (!inputQuestion) return;

    const response = await fetch('/text/chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: new URLSearchParams({
            q: inputQuestion
        })
    });

    if (!response.ok) {
        console.error('요청 실패 : ', response.status);
        return;
    }

    const answer = await response.text();

    console.log(answer);
})
