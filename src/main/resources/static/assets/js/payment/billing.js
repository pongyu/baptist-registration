$(document).ready(function () {

    var event = $('#event').val();

    $('.search').on('click', function () {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/billing/info",
            data: $('#churchId').val(),
            dataType: 'json',
            success: function (d) {
                $('.subtotal').val('₱ '+d.subtotal);
                $('.otherFee').val('₱ '+d.roomFee);
                $('.discount').val("₱ ( "+d.discount+" )");
                $('.total').val('₱ '+d.total);
                table.ajax.url( "/billing/delegates?churchId="+$('#churchId').val() ).load();
            },
            error: function (e) {
                //..
            }
        });

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
    var table = $('#registrants').DataTable({
        "searching": false,
        "bAutoWidth": true,
        "responsive": true,
        "sAjaxSource": "/billing/delegates?churchId="+$('#churchId').val(),
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": null, render: function ( data, type, row ) {
                // Combine the first and last names into a single table field
                return data.firstName+' '+data.lastName;
            } },
            { "mData": "subsidy" },
            { "mData": "roomType" },
            { "mData": "fee" }
        ],
        "columnDefs": [
            {
                targets:3,
                data: 'fee',
                render: $.fn.dataTable.render.number(',', '.', 2, '₱ '),
                className: 'text-right'
            },
            {
                targets:1,
                data: 'subsidy',
                render: $.fn.dataTable.render.number(',', '.', 2, '₱ '),
                className: 'text-right'
            },
            {
                targets:2,
                data: 'roomType',
                render: $.fn.dataTable.render.number(',', '.', 2, '₱ '),
                className: 'text-right'
            }
        ]
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

    $('.payout').on('click', function () {
        $.confirm({
            title: 'Pay out?',
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

});