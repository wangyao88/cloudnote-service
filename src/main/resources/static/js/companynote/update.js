function getCompanyNoteById() {
    $.ajax({
        url : 'companyNote/findOne',
        type : 'get',
        data: {id: id},
        dataType: 'json',
        success : function(companyNote) {
            $('#company_note_title').val(companyNote.title);
            tinyMCE.activeEditor.setContent(companyNote.content);
            $('#companyId').val(companyNote.companyId);
        },
        error : function() {
            swal('系统错误', '获取办公资料失败，请稍候重试！', 'error');
        }
    });
}

function updateCompanyNote() {
    var title = $('#company_note_title').val();
    var content = tinyMCE.activeEditor.getContent();
    var companyId = $('#companyId').val();
    var data = JSON.stringify({
        id: id,
        title: title,
        content: content,
        companyId: companyId
    });
    updateOne('companyNote/update', data, 'companyNote/tablePage', '办公资料');
}

$(document).ready(function () {
    tinyMCE.activeEditor.remove("#company_note_content");
    initCompanyNoteContentContainer();
    getCompanyNoteById();
});