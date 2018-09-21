$(document).ready(function () {
    var churchId = sessionStorage.getItem('churchId');

    var event = $('#event').val();

    function descFormat(codename, codevalue) {
        let desc = "";
        $.ajax({
            async: false,
            type: "GET",
            url: "common/util/cdtbl/findOne?codename="+codename+"&codevalue="+codevalue,
            success: function (d) {
                if (codename === "designation"){
                    desc = d.desc2+" "+d.desc1;
                } else if(codename === "classification"){
                    desc = d.id.codeValue;
                } else {
                    desc = d.desc1
                }
            },
            error: function (e) {
                //...
            }
        });
        return desc;
    }

    // ******************* initialise data tables using ajax call *******************
    var table = $('#registrantTable').DataTable({
        "info":     false,
        "paging":   false,
        "processing": true,
        "bLengthChange": false,
        "ordering": false,
        "bAutoWidth": true,
        "searching": false,
        "responsive": true,
        "sAjaxSource": "/assessment/church/delegates?churchId="+churchId,
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "id"},
            { "mData": null, render: function ( data, type, row ) {
                // Combine the first and last names into a single table field
                return data.firstName+' '+data.lastName;
            } },
            { "mData": null, render: function ( data, type, row ) {
                if(event == 1){
                    return descFormat('designation', data.designation);
                } else {
                    return descFormat('classification', data.classification);
                }
            } },
            { "mData": null, render: function ( data, type, row ) {
                if(event == 1){
                    return data.yearsOfMembership;
                } else {
                    return data.yearsOfTeaching;
                }
            } },
            { "mData": "birthDate" },
            { "mData": "gender" },
            { "mData": "civilStatus" },
            { "mData": "mobileNumber" },
            { "mData": "email" },
            { "mData": null}
        ],
        "columnDefs": [
            {
                "responsivePriority":1,
                "targets": -1,
                "data": null,
                "defaultContent": '<button class="btn btn-default btn-link btn-sm" type="button" id="editBtn"><i class="fa fa-edit"></i></button>'
            },
            {
                targets:4, render:function(data){
                return moment(data).format('MM DD YYYY');
            }
            },
            {
                targets:5, render:function(data){
                return descFormat('gender', data);
            }
            },
            {
                targets:6, render:function(data){
                return descFormat('civilstatus', data);
            }
            }
        ]
    });
});