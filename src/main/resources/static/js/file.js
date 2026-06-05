async function uploadFile(event) {
    event.preventDefault();

    const user = getCurrentUser();
    if (!user) {
        showAlert('Please log in first', 'warning');
        return;
    }

    const form = event.target;
    const fileInput = form.file;
    if (!fileInput.files || fileInput.files.length === 0) {
        showAlert('Please select a file', 'warning');
        return;
    }

    const formData = new FormData();
    formData.append('file', fileInput.files[0]);
    formData.append('uploadedBy', user.id);

    const channelId = form.channelId.value;
    if (channelId) {
        formData.append('channelId', channelId);
    }

    try {
        const response = await apiRequest('/api/files/upload', {
            method: 'POST',
            body: formData
        });
        displayResponse('api-response', response);
        showAlert('File uploaded successfully!', 'success');
        form.reset();
        loadFiles();
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
    }
}

async function loadFiles() {
    try {
        const files = await apiRequest('/api/files');
        displayResponse('api-response', files);
        renderFilesList(files);
        return files;
    } catch (error) {
        displayResponse('api-response', { error: error.message });
        showAlert(error.message, 'danger');
        return [];
    }
}

function renderFilesList(files) {
    const listElement = document.getElementById('files-list');
    if (!listElement) {
        return;
    }

    if (!files || files.length === 0) {
        listElement.innerHTML = '<p class="text-muted mb-0">No files found.</p>';
        return;
    }

    listElement.innerHTML = files.map(file => `
        <div class="border rounded p-3 mb-2 bg-white">
            <div class="d-flex justify-content-between align-items-start">
                <div>
                    <h6 class="mb-1">${file.fileName}</h6>
                    <p class="mb-1 text-muted">${file.fileType}</p>
                    <span class="list-item-meta">
                        ID: ${file.id} | Uploaded by: ${file.uploadedBy}
                        ${file.channelId ? ` | Channel: ${file.channelId}` : ''}
                        | ${formatDateTime(file.uploadedAt)}
                    </span>
                </div>
                <a href="/api/files/${file.id}" class="btn btn-sm btn-outline-primary" download>Download</a>
            </div>
        </div>
    `).join('');
}

function initFilePage() {
    if (!requireAuth()) {
        return;
    }
    loadFiles();

    const uploadForm = document.getElementById('file-form');
    if (uploadForm) {
        uploadForm.addEventListener('submit', uploadFile);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('files-page')) {
        initFilePage();
    }
});
