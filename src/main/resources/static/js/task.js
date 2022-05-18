/**
 * @author triszt4n
 */
function unassign(taskId, assigneeId) {
    fetch(`/tasks/${id}/unassign/${assigneeId}`, { method: 'POST' })
        .then((res) => res.json())
        .then((res) => {
            console.log('DEBUG', res)
            location.reload();
        })
}

function deleteTask(taskId) {
    fetch(`/tasks/${id}`, { method: 'DELETE' })
        .then((res) => res.json())
        .then((res) => {
            console.log('DEBUG', res)
            location.reload();
        })
}
