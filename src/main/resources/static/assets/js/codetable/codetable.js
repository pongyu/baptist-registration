$( document ).ready(function() {

    var table = $('#codeTable').DataTable(
        {
            "responsive": true
        }
    );
    function toastSuccess(message) {
        new PNotify({
            title: 'Success!',
            text: message,
            type: 'success',
            addclass: "stack-bottomright",
            nonblock: {
                nonblock: true
            },
            delay: 3000
        });
    }
    // $('.codename').on('change', function (e) {
    //     var valueSelected = this.value;
    //
    //     console.log(valueSelected)
    //
    //     var url = '/admin/system/codetable/codename?codename='+$(this).val().toLowerCase()+'&'; // get selected value
    //     if (url) { // require a URL
    //         window.location = url; // redirect
    //     }
    //
    // });

    $('.table .edit-btn').on('click', function (e) {
        e.preventDefault();
        var href = $(this).attr('href');
        var t = $(this).text();
        if(t=="Edit"){
            $.get(href, function (codetable, status) {
                $('.codetableForm #codename').val(codetable.id.codeName);
                $('.codetableForm #codevalue').val(codetable.id.codeValue);
                $('.codetableForm #desc1').val(codetable.desc1);
                $('.codetableForm #desc2').val(codetable.desc2);
                $('.codetableForm #remarks').val(codetable.remarks);
            });
        }else {
            // $.get(href, function (codetable, status) {
            //     $('.codetableForm #codename').val('');
            //     $('.codetableForm #codevalue').val('');
            //     $('.codetableForm #desc1').val('');
            //     $('.codetableForm #desc2').val('');
            //     $('.codetableForm #remarks').val('');
            // });
        }

        $('.codetableForm #codetableModal').modal();
    });


    // add

    $('#add-btn').on('click', function () {
        $('.codetableForm #codename').val('');
        $('.codetableForm #codevalue').val('');
        $('.codetableForm #desc1').val('');
        $('.codetableForm #desc2').val('');
        $('.codetableForm #remarks').val('');

        $('.codetableForm #codetableModal').modal();

    });

    $('#modal-save-btn').on('click', function () {

        var data = {}

        data['id'] = {
            'codeName': $('#codename').val(),
            'codeValue': $('#codevalue').val()
        }
        data['desc1'] = $('#desc1').val();
        data['desc2'] = $('#desc2').val();
        data['remarks'] = $('#remarks').val();
            $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/admin/system/cdtbl/save",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function () {
                console.log("test")
                toastSuccess("Codetable saved!")
            },
            error: function (e) {
                //...
            }
        });
        $('#codetableModal').modal('toggle');
        table.on( 'draw', function () {
            console.log( 'Redraw occurred at: '+new Date().getTime() );
        } );
    });
    // $('.eBtn').on('click', function () {
    //     var ids = $.map(table.rows('.selected').data(), function (item) {
    //         return item[0]
    //     });
    //     console.log(ids)
    //     alert(table.rows('.selected').data().length + ' row(s) selected');
    // });
    //
    // $('#registrantSaveBtn').on('click', function (e) {
    //     var data = {}
    //     if(editReg){
    //         data['id'] = $('#id').val();
    //     }
    //     data['churchId'] = churchId;
    //     data['firstName'] = $('#firstname').val();
    //     data['middleName'] = $('#middlename').val();
    //     data['lastName'] = $('#lastname').val();
    //     data['designation'] = $('#designation').val();
    //     data['birthDate'] = $('#birthday').val();
    //     data['mobileNumber'] = $('#mobilenumber').val();
    //     data['gender'] = $('#gender').val();
    //     data['civilStatus'] = $('#civilstatus').val();
    //     data['email'] = $('#email').val();
    //
    //     $.ajax({
    //         type: "POST",
    //         contentType: "application/json",
    //         url: "/register/delegate/save",
    //         data: JSON.stringify(data),
    //         dataType: 'json',
    //         success: function (r) {
    //             table.clear();
    //             table.rows.add(r).draw();
    //         },
    //         error: function (e) {
    //             //...
    //         }
    //     });
    //     editReg = false;
    //     $('#registrantModal').modal('toggle');
    // });

});