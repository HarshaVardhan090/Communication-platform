async function globalSearch(event) {
    event.preventDefault();

    const keyword = document.getElementById('search-keyword').value.trim();
    if (!keyword) {
        showAlert('Please enter a search keyword', 'warning');
        return;
    }

    try {
        const response = await apiRequest(`/api/search/global?keyword=${encodeURIComponent(keyword)}`);
        displayResponse('api-response', response);
        renderSearchResults(response);
        showAlert(`Found ${response.length} result(s)`, 'success');
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

function getBadgeClass(type) {
    const classes = {
        USER: 'bg-primary',
        COMMUNITY: 'bg-success',
        CHANNEL: 'bg-info text-dark',
        MESSAGE: 'bg-warning text-dark',
        FILE: 'bg-secondary'
    };
    return classes[type] || 'bg-dark';
}

function renderSearchResults(results) {
    const listElement = document.getElementById('search-results');
    if (!listElement) {
        return;
    }

    if (!results || results.length === 0) {
        listElement.innerHTML = '<p class="text-muted mb-0">No results found.</p>';
        return;
    }

    listElement.innerHTML = results.map(result => `
        <div class="border rounded p-3 mb-2 bg-white">
            <div class="d-flex justify-content-between align-items-start">
                <div>
                    <h6 class="mb-1">${result.title}</h6>
                    <p class="mb-1 text-muted">${result.description || 'No description'}</p>
                    <span class="list-item-meta">ID: ${result.id}</span>
                </div>
                <span class="badge ${getBadgeClass(result.type)} search-result-badge">${result.type}</span>
            </div>
        </div>
    `).join('');
}

function initSearchPage() {
    if (!requireAuth()) {
        return;
    }

    const searchForm = document.getElementById('search-form');
    if (searchForm) {
        searchForm.addEventListener('submit', globalSearch);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('search-page')) {
        initSearchPage();
    }
});
