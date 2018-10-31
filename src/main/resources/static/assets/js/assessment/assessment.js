$(document).ready(function () {
    var churchId = sessionStorage.getItem('churchId');

    var event = $('#event').val();

    $.ajax({
        type: "GET",
        url: "/assessment/find_one_church?churchId="+churchId,
        success: function (d) {
            $('.church_lbl').text(d.churchId+" - "+d.churchName);
        },
        error: function (e) {
            //..
        }
    });

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
                } else if(codename === "additionalfee"){
                    desc = d.id.codeValue+" - P "+d.desc1;
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
        "bAutoWidth": true,
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
            },
            {
                targets:6,
                data: 'fee',
                render: $.fn.dataTable.render.number(',', '.', 2, '₱ '),
                className: 'text-right'
            }
        ],

        select: {
            style:    'os',
            selector: 'td:last-child'
        },

        // Assessment does'nt need to view total amount of fees. should be in billing.
        // Unless you want to view, just uncomment it.

        // "footerCallback": function ( row, data, start, end, display ) {
        //     var api = this.api(), data;
        //
        //     // Remove the formatting to get integer data for summation
        //     var intVal = function ( i ) {
        //         return typeof i === 'string' ?
        //             i.replace(/[\$,]/g, '')*1 :
        //             typeof i === 'number' ?
        //                 i : 0;
        //     };
        //
        //     // Total over all pages
        //     total = api
        //         .column( 6 )
        //         .data()
        //         .reduce( function (a, b) {
        //             return intVal(a) + intVal(b);
        //         }, 0 );
        //
        //     // Total over this page
        //     pageTotal = api
        //         .column( 6, { page: 'current'} )
        //         .data()
        //         .reduce( function (a, b) {
        //             return intVal(a) + intVal(b);
        //         }, 0 );
        //
        //     // Update footer
        //     $( api.column( 6 ).footer() ).html(
        //         '₱ '+pageTotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        //     );
        //     $( api.column( 7 ).footer() ).html(
        //         'Total fees: ₱ '+total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        //     );
        // }

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

    $('.updateFeeBtn').on('click', function () {

        let d =
            {
                registrants : rSelected,
                roomType : $('#roomtype').val(),
                subsidy : $('#subsidy').val(),
                remarks : $('#remarks').val()
            }

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/assessment/update_fee",
            data: JSON.stringify(d),
            dataType: 'json',
            success: function (data) {
                table.ajax.url( "/assessment/church/delegates?churchId="+churchId ).load();
            },
            error: function (e) {
                console.log("error : "+e);
            }
        });

        $('#feeModal').modal('toggle');

    });

    $('.submitBtn').on('click', function () {
        $.confirm({
            title: 'Submit for payment?',
            content: '',
            theme: 'modern',
            type: 'green',
            animation: 'top',
            closeAnimation: 'top',
            animateFromElement: false,
            buttons: {
                confirm: function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/assessment/submit",
                        data: churchId,
                        dataType: 'json',
                        success: function (data) {
                            // generate invoice

                            saveAs("/report/invoice?churchid="+churchId);

                            $.confirm({
                                title: 'Submitted!',
                                content: data+' is submitted for Payment',
                                type: 'green',
                                typeAnimated: false,
                                escapeKey: true,
                                backgroundDismiss: false,
                                buttons: {
                                    ok: function () {
                                        window.location.href = "/assessment";
                                    }
                                }
                            });
                        },
                        error: function (e) {
                            console.log(e);
                        }
                    });
                },
                cancel: function () {

                }
            }
        });
    });

    function saveAs(uri) {
        var link = document.createElement('a');
        if (typeof link.download === 'string') {
            link.href = uri;

            //Firefox requires the link to be in the body
            document.body.appendChild(link);

            //simulate click
            link.click();

            //remove the link when done
            document.body.removeChild(link);
        } else {
            window.open(uri);
        }
    }

});