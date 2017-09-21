<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>jQuery File Upload Example</title>
    <script src="../../js/jquery.1.9.1.min.js"></script>

    <script src="../../js/vendor/jquery.ui.widget.js"></script>
    <script src="../../js/jquery.iframe-transport.js"></script>
    <script src="../../js/jquery.fileupload.js"></script>

    <!-- bootstrap just to have good looking page -->
    <script src="../../bootstrap/css/bootstrap-table.css"></script>
    <script src="../../bootstrap/js/bootstrap-table.js"></script>
    <script src="../../bootstrap/js/bootstrap.min.js"></script>
    <link href="../../bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- we code these -->
    <script src="../../js/upload-function.js"></script>
</head>

<body>
<h1>File Upload</h1>
<div style="width:500px;padding:20px">
    <input id="fileupload" type="file" name="files[]" data-url="/uploadFiles" multiple>
    <div id="progress">
        <div style="width: 0%;"></div>
    </div>
    <table class="table table-inverse"
           data-sort-name="word"
           style="height: 200px" style="width: 400px" id="uploaded-files">

        <h3>Found words result</h3>
        <thead>
        <tr>
            <th data-field="word" data-sortable="true">Item Name</th>
            <th data-field="count" data-sortable="true">Count</th>
        </tr>
        </thead>
    </table>
</div>

</body>
</html>