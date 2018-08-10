$(document).ready(function () {
    //initialise data tables
    var table = $('#registrantTable').DataTable({
        "sAjaxSource": "/register/registrants",
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
            { "mData": "church" },
            { "mData": null}
        ],
        "columnDefs": [ {
            "targets": -1,
            "data": null,
            "defaultContent": "<button>Edit</button>"
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

    $('#registrantForm').submit(function (event) {
        event.preventDefault();

    });
});