function initCompanyNoteContentContainer() {
    $(tinyMCE.editors).each(function (index, editor) {
        editor.remove("#company_note_content");
    });
    tinymce.init({
        selector: "textarea#company_note_content",
        theme: "modern",
        height:300,
        plugins: [
            "advlist autolink link image lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "save table contextmenu directionality emoticons template paste textcolor"
        ],
        toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | l      ink image | print preview media fullpage | forecolor backcolor emoticons",
        style_formats: [
            {title: 'Bold text', inline: 'b'},
            {title: 'Red text', inline: 'span', styles: {color: '#ff0000'}},
            {title: 'Red header', block: 'h1', styles: {color: '#ff0000'}},
            {title: 'Example 1', inline: 'span', classes: 'example1'},
            {title: 'Example 2', inline: 'span', classes: 'example2'},
            {title: 'Table styles'},
            {title: 'Table row 1', selector: 'tr', classes: 'tablerow1'}
        ]
    });
}

function initCompanySelector() {
    $.ajax({
        url : 'company/findAll',
        type : 'get',
        dataType: 'json',
        success : function(companies) {
            var content = '<option value="" selected>请选择所属公司</option>';
            $(companies).each(function (index, company) {
                content += '<option value="'+company.id+'">'+company.name+'</option>';
            });
            $("#companyId").html(content);
        },
        error : function() {
            swal('系统错误', '获取公司信息失败，请稍候重试！', 'error');
        }
    });
}