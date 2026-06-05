async function sendMessage(event) {
    event.preventDefault();

    const user = getCurrentUser();
    if (!user) {
        showAlert('Please log in first', 'warning');
        return;
    }

    const form = event.target;
    const receiverId = form.receiverId.value;
    const channelId = form.channelId.value;

    const payload = {
        senderId: user.id,
        content: form.content.value.trim(),
        receiverId: receiverId ? Number(receiverId) : null,
        channelId: channelId ? Number(channelId) : null
    };

    try {
        const response = await apiRequest('/api/messages/send', {
            method: 'POST',
            body: payload
        });
        displayResponse('api-response', response);
        showAlert('Message sent successfully!', 'success');
        form.reset();
        loadMessages();
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

async function loadMessages() {
    try {
        const messages = await apiRequest('/api/messages');
        displayResponse('api-response', messages);
        renderMessagesList(messages);
        return messages;
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
        return [];
    }
}

async function getMessagesByChannel(event) {
    event.preventDefault();

    const channelId = document.getElementById('message-channel-id').value;
    if (!channelId) {
        showAlert('Please enter a channel ID', 'warning');
        return;
    }

    try {
        const response = await apiRequest(`/api/messages/channel/${channelId}`);
        displayResponse('api-response', response);
        renderMessagesList(response);
        showAlert('Channel messages loaded!', 'success');
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

function renderMessagesList(messages) {
    const listElement = document.getElementById('messages-list');
    if (!listElement) {
        return;
    }

    if (!messages || messages.length === 0) {
        listElement.innerHTML = '<p class="text-muted mb-0">No messages found.</p>';
        return;
    }

    listElement.innerHTML = messages.map(message => `
        <div class="border rounded p-3 mb-2 bg-white">
            <p class="mb-2">${message.content}</p>
            <span class="list-item-meta">
                ID: ${message.id} | Sender: ${message.senderId}
                ${message.receiverId ? ` | Receiver: ${message.receiverId}` : ''}
                ${message.channelId ? ` | Channel: ${message.channelId}` : ''}
                | ${formatDateTime(message.sentAt)}
            </span>
        </div>
    `).join('');
}

function initMessagePage() {
    if (!requireAuth()) {
        return;
    }
    loadMessages();

    const sendForm = document.getElementById('message-form');
    if (sendForm) {
        sendForm.addEventListener('submit', sendMessage);
    }

    const lookupForm = document.getElementById('message-lookup-form');
    if (lookupForm) {
        lookupForm.addEventListener('submit', getMessagesByChannel);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('messages-page')) {
        initMessagePage();
    }
});
