$(document).ready(function () {
    var churchId = sessionStorage.getItem('churchId');

    var event = $('#event').val();

    function descFormat(codename, codevalue) {
        let desc = "";
        $.ajax({
            async: false,
            type: "GET",
            url: "/common/util/cdtbl/findOne?codename="+codename+"&codevalue="+codevalue,
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
                //..
            }
        });
        return desc;
    }


    // ******************* initialise data tables using ajax call *******************
    var table = $('#registrantTable').DataTable({
        "info":     true,
        "paging":   true,
        "processing": true,
        "bLengthChange": false,
        "ordering": false,
        "bAutoWidth": true,
        "searching": true,
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
            { "mData": "birthDate" },
            { "mData": "subsidy" },
            { "mData": "roomType" },
            { "mData": "fee" },
            { "mData": "remarks" },
            { "mData": null}
        ],
        "columnDefs": [
            {
                "responsivePriority":1,
                "targets": -1,
                "data": null,
                "defaultContent": '',
                "className": 'select-checkbox'
            },
            {
                targets:3, render:function(data){
                return moment(data).format('MM DD YYYY');
            }
            },
            {
                "targets": [ 0 ],
                "visible": false
            }
        ],

        select: {
            style:    'os',
            selector: 'td:last-child'
        },

        "footerCallback": function ( row, data, start, end, display ) {
            var api = this.api(), data;

            // Remove the formatting to get integer data for summation
            var intVal = function ( i ) {
                return typeof i === 'string' ?
                    i.replace(/[\$,]/g, '')*1 :
                    typeof i === 'number' ?
                        i : 0;
            };

            // Total over all pages
            total = api
                .column( 6 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );

            // Total over this page
            pageTotal = api
                .column( 6, { page: 'current'} )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );

            // Update footer
            $( api.column( 6 ).footer() ).html(
                'P '+pageTotal
            );
            $( api.column( 7 ).footer() ).html(
                'Total fees: P '+total
            );
        }

    });


    var rSelected = [];

    $('#editFee').on('click', function () {

        rSelected = table.rows({selected: true}).data().toArray();

        if(rSelected.length <= 0){
            $.confirm({
                title: 'No delegate(s) selected!',
                content: 'Please select delegate(s)',
                type: 'red',
                typeAnimated: false,
                buttons: {
                    close: function () {
                    }
                }
            });
        }else{
            $('#feeModal').modal();
        }

    });

});