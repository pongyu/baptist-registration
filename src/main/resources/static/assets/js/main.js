$(document).ready(function () {
    //initialise data tables
    var table = $('#registrantTable').DataTable({
        "processing": true,
        "bAutoWidth": true,
        "sAjaxSource": "/register/delegates",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "id"},
            { "mData": "designation" },
            { "mData": null, render: function ( data, type, row ) {
                // Combine the first and last names into a single table field
                return data.firstName+' '+data.lastName;
            } },
            { "mData": "birthDate" },
            { "mData": "gender" },
            { "mData": "email" },
            { "mData": null}
        ],
        "columnDefs": [ {
            "targets": -1,
            "data": null,
            "defaultContent": '<button type="button">Edit</button>'
        } ]
    });


    $('#registrantTable tbody').on( 'click', 'button', function () {
        var data = table.row( $(this).parents('tr') ).data();
        $('.registrantForm #id').val(data.id);
        $('.registrantForm #firstname').val(data.firstName);
        $('.registrantForm #middlename').val(data.middleName);
        $('.registrantForm #lastname').val(data.lastName);
        $('.registrantForm #designation').val(data.designation);

        $('.registrantForm #registrantModal').modal();

    });


    $('#newRegistrantBtn').on('click', function (e) {
        e.preventDefault();
        $('.registrantForm #id').val('');
        $('.registrantForm #firstname').val('');
        $('.registrantForm #middlename').val('');
        $('.registrantForm #lastname').val('');
        $('.registrantForm #designation').val('');
        $('#registrantModal').modal();
    });

    $('#registrantSaveBtn').on('click', function (e) {
        $.ajax({
            type: "POST",
            url: "delegate/add",
            data: '{"firstName":"Pongyu","lastName":"Quindoza"}',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(result) { //we got the response
                console.log('Delegate added!');
            },
            error: function(jqxhr, status, exception) {
                console.log('Exception:', exception);
            }
        });
        $('#registrantModal').modal('toggle');
        table.ajax.reload();
    });

});

