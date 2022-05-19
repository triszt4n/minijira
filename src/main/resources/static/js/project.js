function buildTaskElement(task) {
    return `<div class="card w-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div class="h5">
                            <i class="fa-solid fa-list-check"></i>
                            Task: <a href="/tasks/${task.id}">${task.title}</a>
                        </div>
                        <div>
                            <span class="text-muted">${task.statusName}</span>
                        </div>
                    </div>
                    <p class="text-muted mb-1">needs <span>${task.hoursNeeded}</span> hours</p>
                    <p>${task.description}</p>
                </div>
            </div>`
}

function openTodos(id) {
    const contentDiv = document.querySelector('#todo-modal-content')
    fetch(`/api/projects/${id}/todos`)
        .then(res => res.json())
        .then(tasks => {
            contentDiv.innerHTML = tasks.map(buildTaskElement)
        })
}
