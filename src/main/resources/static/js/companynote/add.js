function addCompanyNote() {
    var title = $('#company_note_title').val();
    var content = tinyMCE.activeEditor.getContent();
    var companyId = $('#companyId').val();
    var data = JSON.stringify({
        title: title,
        content: content,
        companyId: companyId
    });
    addOne('companyNote/add', data, 'companyNote/tablePage', '办公资料');
}

$(document).ready(function () {
    initCompanyNoteContentContainer();
    initCompanySelector();
});