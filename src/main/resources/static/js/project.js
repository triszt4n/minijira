function buildAssigneeElement(user) {
    return `<div class="card w-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div class="h5">
                            ${user.roleName === "Manager" ? `<i className="fa-solid fa-user-shield"></i>` : `<i class="fa-solid fa-user-vneck"></i>`}
                            <a href="/users/${user.id}">${user.username}</a>
                        </div>
                        <div>
                            <span class="text-muted">${user.roleName}</span>
                        </div>
                    </div>
                </div>
            </div>`
}

function buildCommentElement(comment) {
    return `<div class="card w-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div class="h5">
                            <i class="fa-solid fa-circle-user"></i>
                            By: <a href="/users/${comment.publisherId}">${comment.publisherUsername}</a>
                        </div>
                    </div>
                    <p>${comment.body}</p>
                </div>
            </div>`
}

function openAssignees(id) {
    const contentDiv = document.querySelector('#assignees-comments-modal-content')
    const titleDiv = document.querySelector('#exampleModalLabel')
    titleDiv.textContent = "Assignees of task"
    contentDiv.innerHTML = ""
    fetch(`/api/tasks/${id}/assignees`)
        .then(res => res.json())
        .then(users => {
            contentDiv.innerHTML = users.map(buildAssigneeElement)
        })
}

function openComments(id) {
    const contentDiv = document.querySelector('#assignees-comments-modal-content')
    const titleDiv = document.querySelector('#exampleModalLabel')
    titleDiv.textContent = "Comments under task"
    contentDiv.innerHTML = ""
    fetch(`/api/tasks/${id}/comments`)
        .then(res => res.json())
        .then(comments => {
            contentDiv.innerHTML = comments.map(buildCommentElement)
        })
}

function deleteProject(id) {
    if (confirm("Do you really want to delete Project with all of its tasks and comments?"))
        fetch(`/projects/${id}`, {method: 'DELETE'})
            .then(() => location.replace('/projects'))
}

function deleteTask(id) {
    if (confirm("Do you really want to delete Task with all of its comments?"))
        fetch(`/tasks/${id}`, {method: 'DELETE'})
            .then(() => location.reload())
}
