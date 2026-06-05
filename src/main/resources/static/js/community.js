async function createCommunity(event) {
    event.preventDefault();

    const user = getCurrentUser();
    if (!user) {
        showAlert('Please log in first', 'warning');
        return;
    }

    const form = event.target;
    const payload = {
        name: form.name.value.trim(),
        description: form.description.value.trim(),
        createdBy: user.id
    };

    try {
        const response = await apiRequest('/api/communities', {
            method: 'POST',
            body: payload
        });
        displayResponse('api-response', response);
        showAlert('Community created successfully!', 'success');
        form.reset();
        loadCommunities();
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

async function loadCommunities() {
    try {
        const communities = await apiRequest('/api/communities');
        displayResponse('api-response', communities);
        renderCommunitiesList(communities);
        return communities;
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
        return [];
    }
}

async function getCommunityById(event) {
    event.preventDefault();

    const communityId = document.getElementById('community-id').value;
    if (!communityId) {
        showAlert('Please enter a community ID', 'warning');
        return;
    }

    try {
        const response = await apiRequest(`/api/communities/${communityId}`);
        displayResponse('api-response', response);
        showAlert('Community loaded successfully!', 'success');
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

function renderCommunitiesList(communities) {
    const listElement = document.getElementById('communities-list');
    if (!listElement) {
        return;
    }

    if (!communities || communities.length === 0) {
        listElement.innerHTML = '<p class="text-muted mb-0">No communities found.</p>';
        return;
    }

    listElement.innerHTML = communities.map(community => `
        <div class="border rounded p-3 mb-2 bg-white">
            <div class="d-flex justify-content-between align-items-start">
                <div>
                    <h6 class="mb-1">${community.name}</h6>
                    <p class="mb-1 text-muted">${community.description}</p>
                    <span class="list-item-meta">ID: ${community.id} | Created by: ${community.createdBy} | ${formatDateTime(community.createdAt)}</span>
                </div>
                <span class="badge bg-success">Community</span>
            </div>
        </div>
    `).join('');
}

function initCommunityPage() {
    if (!requireAuth()) {
        return;
    }
    loadCommunities();

    const createForm = document.getElementById('community-form');
    if (createForm) {
        createForm.addEventListener('submit', createCommunity);
    }

    const lookupForm = document.getElementById('community-lookup-form');
    if (lookupForm) {
        lookupForm.addEventListener('submit', getCommunityById);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('communities-page')) {
        initCommunityPage();
    }
});
