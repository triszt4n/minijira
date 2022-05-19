function unassign(taskId, assigneeId) {
    fetch(`/tasks/${taskId}/unassign/${assigneeId}`, {method: 'POST'})
        .then(() => location.reload())
}

function deleteTask(id, projectId) {
    if (confirm("Do you really want to delete Task with all of its comments?"))
        fetch(`/tasks/${id}`, {method: 'DELETE'})
            .then(() => location.replace(`/projects/${projectId}`))
}

function deleteComment(id) {
    if (confirm("Do you really want to delete Comment?"))
        fetch(`/comments/${id}`, {method: 'DELETE'})
            .then(() => location.reload())
}
