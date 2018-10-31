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
        $.get(href, function (codetable, status) {
                $('.codetableForm #codename').val(codetable.id.codeName);
                $('.codetableForm #codevalue').val(codetable.id.codeValue);
                $('.codetableForm #desc1').val(codetable.desc1);
                $('.codetableForm #desc2').val(codetable.desc2);
                $('.codetableForm #remarks').val(codetable.remarks);
        });
        $('#codename').prop('disabled', true);
        $('#codevalue').prop('disabled', true);
        $('#modal-delete-btn').show();
        $('.codetableForm #codetableModal').modal();
    });


    // add

    $('#add-btn').on('click', function () {
        $('.codetableForm #codename').val('');
        $('.codetableForm #codevalue').val('');
        $('.codetableForm #desc1').val('');
        $('.codetableForm #desc2').val('');
        $('.codetableForm #remarks').val('');

        $('#codename').prop('disabled', false);
        $('#codevalue').prop('disabled', false);
        $('#modal-delete-btn').hide();

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
            url: "/codetable/save",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function () {
                $('#codetableModal').modal('toggle');
                window.location.href = "/codetable";
            },
            error: function (e) {
                //...
            }
        });
    });

    //delete
    $('#modal-delete-btn').on('click', function () {
        $.ajax({
            type: "GET",
            url: "/codetable/delete?codename="+$('#codename').val()+"&codevalue="+$('#codevalue').val(),
            success: function () {

            },
            error: function (e) {
                //...
            }
        });
        $('#codetableModal').modal('toggle');
    });
});