const API_BASE = '';

function getCurrentUser() {
    const userJson = sessionStorage.getItem('currentUser');
    if (!userJson) {
        return null;
    }
    try {
        return JSON.parse(userJson);
    } catch (error) {
        sessionStorage.removeItem('currentUser');
        return null;
    }
}

function setCurrentUser(user) {
    sessionStorage.setItem('currentUser', JSON.stringify(user));
}

function clearCurrentUser() {
    sessionStorage.removeItem('currentUser');
}

function requireAuth() {
    const user = getCurrentUser();
    if (!user) {
        window.location.href = '/pages/login.html';
        return null;
    }
    return user;
}

function showAlert(message, type = 'success') {
    const container = document.getElementById('alert-container');
    if (!container) {
        return;
    }

    const alertId = 'alert-' + Date.now();
    const alertHtml = `
        <div id="${alertId}" class="alert alert-${type} alert-dismissible fade show shadow-sm" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `;
    container.insertAdjacentHTML('beforeend', alertHtml);

    setTimeout(() => {
        const alertElement = document.getElementById(alertId);
        if (alertElement) {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alertElement);
            bsAlert.close();
        }
    }, 5000);
}

function displayResponse(elementId, data) {
    const element = document.getElementById(elementId);
    if (!element) {
        return;
    }
    element.textContent = typeof data === 'string' ? data : JSON.stringify(data, null, 2);
}

async function apiRequest(url, options = {}) {
    const config = {
        headers: {
            ...options.headers
        },
        ...options
    };

    if (config.body && typeof config.body === 'object' && !(config.body instanceof FormData)) {
        config.headers['Content-Type'] = 'application/json';
        config.body = JSON.stringify(config.body);
    }

    const response = await fetch(API_BASE + url, config);
    let responseData = null;

    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
        responseData = await response.json();
    } else if (response.status !== 204) {
        responseData = await response.text();
    }

    if (!response.ok) {
        const errorMessage = responseData?.error
            || responseData?.message
            || (responseData?.errors ? JSON.stringify(responseData.errors) : null)
            || `Request failed with status ${response.status}`;
        throw new Error(errorMessage);
    }

    return responseData;
}

function formatDateTime(dateString) {
    if (!dateString) {
        return '-';
    }
    return new Date(dateString).toLocaleString();
}

function updateNavbarUser() {
    const user = getCurrentUser();
    const userDisplay = document.getElementById('nav-user');
    if (userDisplay) {
        userDisplay.textContent = user ? user.username : 'Guest';
    }
}

function initNavbar() {
    updateNavbarUser();
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', (event) => {
            event.preventDefault();
            clearCurrentUser();
            window.location.href = '/pages/login.html';
        });
    }
}

document.addEventListener('DOMContentLoaded', initNavbar);
