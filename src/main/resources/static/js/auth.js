async function registerUser(event) {
    event.preventDefault();

    const form = event.target;
    const payload = {
        username: form.username.value.trim(),
        email: form.email.value.trim(),
        password: form.password.value,
        role: form.role.value
    };

    try {
        const response = await apiRequest('/api/users/register', {
            method: 'POST',
            body: payload
        });
        displayResponse('api-response', response);
        showAlert('Registration successful! You can now log in.', 'success');
        setTimeout(() => {
            window.location.href = '/pages/login.html';
        }, 1500);
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

async function loginUser(event) {
    event.preventDefault();

    const form = event.target;
    const payload = {
        email: form.email.value.trim(),
        password: form.password.value
    };

    try {
        const response = await apiRequest('/api/users/login', {
            method: 'POST',
            body: payload
        });
        setCurrentUser(response);
        displayResponse('api-response', response);
        showAlert('Login successful!', 'success');
        setTimeout(() => {
            window.location.href = '/pages/dashboard.html';
        }, 1000);
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

async function loadAllUsers() {
    try {
        const users = await apiRequest('/api/users');
        displayResponse('users-response', users);
        renderUsersTable(users);
        return users;
    } catch (error) {
        displayResponse('users-response', { error: error.message });
        showAlert(error.message, 'danger');
        return [];
    }
}

function renderUsersTable(users) {
    const tableBody = document.getElementById('users-table-body');
    if (!tableBody) {
        return;
    }

    if (!users || users.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="4" class="text-center text-muted">No users found</td></tr>';
        return;
    }

    tableBody.innerHTML = users.map(user => `
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td><span class="badge bg-primary">${user.role}</span></td>
        </tr>
    `).join('');
}

async function updateUserRole(event) {
    event.preventDefault();

    const form = event.target;
    const payload = {
        userId: Number(form.userId.value),
        role: form.role.value
    };

    try {
        const response = await apiRequest('/api/roles/update', {
            method: 'PUT',
            body: payload
        });
        displayResponse('role-response', response);
        showAlert('Role updated successfully!', 'success');
        form.reset();
    } catch (error) {
        displayResponse('role-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

async function getUserRole(event) {
    event.preventDefault();

    const userId = document.getElementById('role-user-id').value;
    if (!userId) {
        showAlert('Please enter a user ID', 'warning');
        return;
    }

    try {
        const response = await apiRequest(`/api/roles/user/${userId}`);
        displayResponse('role-response', response);
        showAlert('Role fetched successfully!', 'success');
    } catch (error) {
        displayResponse('role-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

function initDashboard() {
    const user = requireAuth();
    if (!user) {
        return;
    }

    document.getElementById('welcome-user').textContent = user.username;
    document.getElementById('user-role').textContent = user.role;
    document.getElementById('user-id').textContent = user.id;
    document.getElementById('user-email').textContent = user.email;

    loadAllUsers();

    const roleForm = document.getElementById('role-update-form');
    if (roleForm) {
        roleForm.addEventListener('submit', updateUserRole);
    }

    const roleLookupForm = document.getElementById('role-lookup-form');
    if (roleLookupForm) {
        roleLookupForm.addEventListener('submit', getUserRole);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('register-form');
    if (registerForm) {
        registerForm.addEventListener('submit', registerUser);
    }

    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', loginUser);
    }

    if (document.getElementById('dashboard-page')) {
        initDashboard();
    }
});
