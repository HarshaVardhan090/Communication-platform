async function createChannel(event) {
    event.preventDefault();

    const form = event.target;
    const payload = {
        name: form.name.value.trim(),
        description: form.description.value.trim(),
        communityId: Number(form.communityId.value)
    };

    try {
        const response = await apiRequest('/api/channels', {
            method: 'POST',
            body: payload
        });
        displayResponse('api-response', response);
        showAlert('Channel created successfully!', 'success');
        form.reset();
        loadChannels();
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

async function loadChannels() {
    try {
        const channels = await apiRequest('/api/channels');
        displayResponse('api-response', channels);
        renderChannelsList(channels);
        return channels;
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
        return [];
    }
}

async function getChannelsByCommunity(event) {
    event.preventDefault();

    const communityId = document.getElementById('channel-community-id').value;
    if (!communityId) {
        showAlert('Please enter a community ID', 'warning');
        return;
    }

    try {
        const response = await apiRequest(`/api/channels/community/${communityId}`);
        displayResponse('api-response', response);
        renderChannelsList(response);
        showAlert('Channels loaded successfully!', 'success');
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

function renderChannelsList(channels) {
    const listElement = document.getElementById('channels-list');
    if (!listElement) {
        return;
    }

    if (!channels || channels.length === 0) {
        listElement.innerHTML = '<p class="text-muted mb-0">No channels found.</p>';
        return;
    }

    listElement.innerHTML = channels.map(channel => `
        <div class="border rounded p-3 mb-2 bg-white">
            <div class="d-flex justify-content-between align-items-start">
                <div>
                    <h6 class="mb-1"># ${channel.name}</h6>
                    <p class="mb-1 text-muted">${channel.description || 'No description'}</p>
                    <span class="list-item-meta">ID: ${channel.id} | Community: ${channel.communityId} | ${formatDateTime(channel.createdAt)}</span>
                </div>
                <span class="badge bg-info text-dark">Channel</span>
            </div>
        </div>
    `).join('');
}

function initChannelPage() {
    if (!requireAuth()) {
        return;
    }
    loadChannels();

    const createForm = document.getElementById('channel-form');
    if (createForm) {
        createForm.addEventListener('submit', createChannel);
    }

    const lookupForm = document.getElementById('channel-lookup-form');
    if (lookupForm) {
        lookupForm.addEventListener('submit', getChannelsByCommunity);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('channels-page')) {
        initChannelPage();
    }
});
