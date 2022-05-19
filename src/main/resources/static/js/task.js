function unassign(taskId, assigneeId) {
    fetch(`/tasks/${taskId}/unassign/${assigneeId}`, { method: 'POST' })
        .then(() => location.reload())
}

function deleteTask(taskId) {
    fetch(`/tasks/${taskId}`, { method: 'DELETE' })
        .then(() => location.reload())
}
