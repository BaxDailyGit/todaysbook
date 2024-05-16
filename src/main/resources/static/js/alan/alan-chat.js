document.addEventListener('DOMContentLoaded', function () {
    const chatMessages = document.getElementById('chat-messages');
    const userInput = document.getElementById('user-input');
    const sendBtn = document.getElementById('send-btn');
    const resetBtn = document.getElementById('reset-btn');
    const chattingScroll = document.querySelector('.chatting-read-area');
    let eventSource = null;
    let isResetting = false;
    window.onbeforeunload = function (event) {
        if (!isResetting) {
            event.preventDefault();
            event.returnValue = '';
        }
    }
    resetState();
    sendBtn.addEventListener('click', sendMessage);
    resetBtn.addEventListener('click', resetbutton);
    userInput.addEventListener('keyup', function (event) {
        if (event.key === 'Enter') {
            sendMessage();
        }
    });
    function sendMessage() {
        const content = userInput.value.trim();
        if (content !== '') {
            const welcomeText = document.getElementById('welcome-text');
            if (welcomeText) {
                welcomeText.style.display = 'none';
            }
            appendMessage('User', content);
            userInput.value = '';
            if (eventSource) {
                eventSource.close();
            }
            const typingMessageElement = createMessageElement('Alan', true);
            typingMessageElement.querySelector('.read-text').textContent = "";
            chatMessages.appendChild(typingMessageElement);
            chattingScroll.scrollTop = chattingScroll.scrollHeight;
            eventSource = new EventSource(`/alan/sse-streaming?content=${encodeURIComponent(content)}`);
            let isFirstChunk = true;
            let alanMessageElement = null;
            eventSource.onmessage = function (event) {
                const response = JSON.parse(event.data);
                if (response.type === 'continue') {
                    if (isFirstChunk) {
                        chatMessages.removeChild(typingMessageElement);
                        alanMessageElement = createMessageElement('Alan');
                        chatMessages.appendChild(alanMessageElement);
                        isFirstChunk = false;
                    }
                    const contentElement = document.createElement('span');
                    contentElement.innerHTML = formatContent(response.data.content);
                    alanMessageElement.querySelector('.read-text').appendChild(contentElement);
                    chattingScroll.scrollTop = chattingScroll.scrollHeight;
                } else if (response.type === 'complete') {
                    const readText = alanMessageElement.querySelector('.read-text');
                    while (readText.firstChild) {
                        readText.removeChild(readText.firstChild);
                    }
                    const contentElement = document.createElement('span');
                    contentElement.innerHTML = formatContent(response.data.content);
                    readText.appendChild(contentElement);
                    chattingScroll.scrollTop = chattingScroll.scrollHeight;
                    eventSource.close();
                }
            };
            eventSource.onerror = function () {
                eventSource.close();
            };
        }
    }
    function appendMessage(sender, content) {
        const messageElement = createMessageElement(sender);
        messageElement.querySelector('.read-text').textContent = content;
        chatMessages.appendChild(messageElement);
        chattingScroll.scrollTop = chattingScroll.scrollHeight;
    }
    function createMessageElement(sender, isTyping = false) {
        const messageElement = document.createElement('div');
        messageElement.className = sender === 'User' ? 'user-chat' : 'alan-chat';
        messageElement.innerHTML = `
        ${sender === 'User' ? '<div class="read-text"></div><img src="/images/user_chat.png">' : '<img src="/images/alan_chat.png"><div class="read-text"></div>'}
    `;
        if (isTyping) {
            messageElement.classList.add('typing');
        }
        return messageElement;
    }
    function formatContent(content) {
        content = content.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
        content = content.replace(/\n/g, '<br>');
        content = content.replace(/\[(.*?)\]\((.*?)\)/g, '<a href="$2" target="_blank">$1</a>');
        return content;
    }
    function resetbutton() { // 이유: beforeunload 표시가 되지 않게 하기 위해
        resetState();
        setTimeout(function () {
            location.reload();
        }, 500);
    }
    function resetState() {
        isResetting = true;
        fetch('/alan/reset-state', {
            method: 'GET'
        })
            .then(response => {
                if (response.ok) {
                    console.log('상태 초기화 완료');
                    isResetting = false;
                } else {
                    console.error('상태 초기화 실패');
                    isResetting = false;
                }
            })
            .catch(error => {
                console.error('상태 초기화 오류', error);
                isResetting = false;
            });
    }
});
