$(document).ready(function () {

    var event = $('#event').val();

    function numberFormat(num)
    {
        var num_parts = num.toString().split(".");
        num_parts[0] = num_parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        return num_parts.join(".");
    }

    $('.search').on('click', function () {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/billing/info",
            data: $('#churchId').val(),
            dataType: 'json',
            success: function (d) {
                $('.subtotal').val('₱ '+numberFormat(d.subtotal));
                $('.otherFee').val('₱ '+numberFormat(d.roomFee));
                $('.discount').val("₱ ( "+numberFormat(d.discount)+" )");
                $('.total').val('₱ '+numberFormat(d.total));
                table.ajax.url( "/billing/delegates?churchId="+$('#churchId').val() ).load();
            },
            error: function (e) {
                console.log(e);
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
        ],

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
            fee = api
                .column( 3 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );

            roomFee = api
                .column( 2 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );

            subtotal = fee + roomFee;

            discount = api
                .column( 1 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );


            // Total over this page
            // pageTotal = api
            //     .column( 6, { page: 'current'} )
            //     .data()
            //     .reduce( function (a, b) {
            //         return intVal(a) + intVal(b);
            //     }, 0 );

            // Update footer
            // $( api.column( 6 ).footer() ).html(
            //     '₱ '+pageTotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
            // );
            // $( api.column( 7 ).footer() ).html(
            //     'Total fees: ₱ '+subtotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
            // );

            // $('.subtotal').val('₱ '+fee.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            // $('.otherFee').val('₱ '+roomFee.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            // $('.discount').val('₱ '+discount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            // $('.total').val('₱ '+fee.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
        }

    });

    $('.payout').on('click', function () {
        $.confirm({
            title: 'Payout '+$('.total').val(),
            content: ' ',
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
                        url: "/billing/payout",
                        data: $('#churchId').val(),
                        success: function (data) {
                            $.confirm({
                                title: 'Paid!',
                                content: $('#churchId').val()+' '+data,
                                type: 'green',
                                typeAnimated: false,
                                escapeKey: true,
                                backgroundDismiss: false,
                                buttons: {
                                    ok: function () {
                                        window.location.href = "/billing";
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