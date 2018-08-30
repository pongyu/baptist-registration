$( document ).ready(function() {
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

    $('.table .eBtn').on('click', function (e) {
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
            $.get(href, function (codetable, status) {
                $('.codetableForm #codename').val('');
                $('.codetableForm #codevalue').val('');
                $('.codetableForm #desc1').val('');
                $('.codetableForm #desc2').val('');
                $('.codetableForm #remarks').val('');
            });
        }

        $('.codetableForm #codetableModal').modal();
    });

    // for adding or updating registrant
    // var editReg = false;
    //
    // $('#codeTable tbody').on( 'click', 'button#editBtn', function () {
    //
    //     editReg = true;
    //     // var data = table.row( $(this).parents('tr') ).data();
    //
    //     $('#codetableModal').modal('toggle');
    //
    // });

});