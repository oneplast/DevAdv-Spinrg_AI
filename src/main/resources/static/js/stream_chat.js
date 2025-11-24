const chatContainer = document.getElementById("chat-container");
const chatForm = document.getElementById("chatForm");
const chatInput = document.getElementById("chatInput");

chatForm.addEventListener('submit', async (e) => {

    e.preventDefault();

    const inputQuestion = chatInput.value.trim();
    if (!inputQuestion) return;

    const messageDiv = document.createElement('div');
    messageDiv.classList.add('flex', 'justify-end');

    messageDiv.innerHTML = `
                    <div class="max-w-s bg gray-200 p-3 rounded-lg text-sm">${inputQuestion}</div>
                    `;

    chatContainer.appendChild(messageDiv);
    chatInput.value = '';

    try {
        const response = await fetchStreamWithRetry(`/stream/chat/answer?q=${encodeURIComponent(inputQuestion)}`);

        const reader = response.body.getReader();

        const decoder = new TextDecoder();

        const answerDiv = document.createElement('div');
        answerDiv.classList.add('flex', 'justify-end');

        const textDiv = document.createElement('div');
        textDiv.classList.add('max-w-s', 'p-3', 'rounded-lg', 'text-sm');

        answerDiv.appendChild(textDiv);
        chatContainer.appendChild(answerDiv);

        while (true) {
            const {value, done} = await reader.read();

            if (done) break;

            textDiv.textContent += decoder.decode(value, {stream: true});
            chatContainer.scrollTop = chatContainer.scrollHeight;
        }
    } catch (e) {
        console.error(e);
    }

    chatContainer.scrollTop = chatContainer.scrollHeight;
})

async function fetchStreamWithRetry(url, retry = 3) {

    for (let i = 0; i < retry; i++) {
        try {
            const response = await fetch(url);
            if (response.ok) {
                return response;
            }
        } catch (e) {
            if (i === retry - 1) {
                throw new Error(e);
            }
        }
    }
}