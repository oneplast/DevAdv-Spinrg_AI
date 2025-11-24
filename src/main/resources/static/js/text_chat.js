const chatContainer = document.getElementById("chat-container");
const chatForm = document.getElementById("chatForm");
const chatInput = document.getElementById("chatInput");

chatForm.addEventListener('submit', async (e) => {

    e.preventDefault();

    const inputQuestion = chatInput.value.trim();
    if (!inputQuestion) return;

    const messageDiv = document.createElement('div');
    messageDiv.classList.add('flex', 'justify-end')

    messageDiv.innerHTML = `
                    <div class="max-w-s bg gray-200 p-3 rounded-lg text-sm">${inputQuestion}</div>
                    `;

    chatContainer.appendChild(messageDiv);
    chatInput.value = '';

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

    const answerDiv = document.createElement('div');
    answerDiv.classList.add('flex', 'justify-start');
    answerDiv.innerHTML = `
                    <div class="flex justify-start">
                        <div class="max-w-s p-3 rounded-lg text-sm">
                        ${answer}
                        </div>
                    </div>
                    `

    chatContainer.appendChild(answerDiv);

    chatContainer.scrollTop = chatContainer.scrollHeight;
})
