function getProjectNoteById() {
    $.ajax({
        url : 'projectNote/findOne',
        type : 'get',
        data: {id: id},
        dataType: 'json',
        success : function(projectNote) {
            $('#project_note_title').val(projectNote.title);
            tinyMCE.activeEditor.setContent(projectNote.content);
            $('#projectId').val(projectNote.projectId);
        },
        error : function() {
            swal('系统错误', '获取项目资料失败，请稍候重试！', 'error');
        }
    });
}

function updateProjectNote() {
    var title = $('#project_note_title').val();
    var content = tinyMCE.activeEditor.getContent();
    var projectId = $('#projectId').val();
    var data = JSON.stringify({
        id: id,
        title: title,
        content: content,
        projectId: projectId
    });
    updateOne('projectNote/update', data, 'projectNote/tablePage', '项目资料');
}

$(document).ready(function () {
    initProjectNoteContentContainer();
    initProjectSelector();
    getProjectNoteById();
});