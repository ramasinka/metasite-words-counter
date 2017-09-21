$(document).on('ready', function () {
    var fileList = [];
    $('#fileupload').fileupload({
        dataType: 'json',
        multiFileRequest: true,
        singleFileUploads: false,

        done: function (e, json) {
            $('#uploaded-files').bootstrapTable("destroy");
            $('#uploaded-files').bootstrapTable({
                data: json.result
            });

        }
    }).on("fileuploadadd", function (e, data) {
        for (var i = 0; i < data.files.length; i++) {
            fileList.push(data.files[i])
        }
    });

    $('#button').click(function () {
        $('#form').fileupload('send', {files: fileList});
    })
});
