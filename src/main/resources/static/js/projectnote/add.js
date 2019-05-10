function addProjectNote() {
    var title = $('#project_note_title').val();
    var content = tinyMCE.activeEditor.getContent();
    var projectId = $('#projectId').val();
    var data = JSON.stringify({
        title: title,
        content: content,
        projectId: projectId
    });
    addOne('projectNote/add', data, 'projectNote/tablePage', '项目资料');
}

$(document).ready(function () {
    initProjectNoteContentContainer();
    initProjectSelector();
});