"use strict";
var churchId;
(function( $ ) {
	
	function scroll_to_class(element_class, removed_height) {
		var scroll_to = $(element_class).offset().top - removed_height;
		if($(window).scrollTop() != scroll_to) {
			$('.form-wizard').stop().animate({scrollTop: scroll_to}, 0);
		}
	}

	function bar_progress(progress_line_object, direction) {
		var number_of_steps = progress_line_object.data('number-of-steps');
		var now_value = progress_line_object.data('now-value');
		var new_value = 0;
		if(direction == 'right') {
			new_value = now_value + ( 100 / number_of_steps );
		}
		else if(direction == 'left') {
			new_value = now_value - ( 100 / number_of_steps );
		}
		progress_line_object.attr('style', 'width: ' + new_value + '%;').data('now-value', new_value);
	}
	
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
    function descFormat(codename, codevalue) {
        $.ajax({
            type: "GET",
            url: "/admin/system/cdtbl/findOne?codename="+codename+"&codevalue="+codevalue,
            success: function (d) {
            },
            error: function (e) {
                //...
            }
        });

    }

	jQuery(document).ready(function() {
        $('#churchname').val('');
        $('#church_address').val('');
        $('#city').val('');
        $('#state').val('');
        $('#country').val('PH');
        $('#church_email').val('');
        $('#church_contact_number').val('');
        $('#church_contact_person').val('');
        $('#church_contact_person_number').val('');

        //populate city by default philippines
        var options = [];
        $.ajax({
            type: "GET",
            url: "/common/util/country/states?code=PH",
            success: function (result) {
                options.push('<option></option>');
                for (var i = 0; i < result.length; i++) {
                    options.push('<option value="',
                        result[i], '">',
                        result[i], '</option>');
                }
                $("#state").html(options.join(''));
            },
            error: function (e) {
                //...
            }
        });

        $('.churchName').select2();
		/*
			Form
		*/
		$('.form-wizard fieldset:first').fadeIn('slow');
		
		$('.form-wizard .required').on('focus', function() {
			$(this).removeClass('input-error');
		});
		
		// next step
		$('.form-wizard .btn-next').on('click', function() {
			var parent_fieldset = $(this).parents('fieldset');
			var next_step = true;
			// navigation steps / progress steps
			var current_active_step = $(this).parents('.form-wizard').find('.form-wizard-step.active');
			var progress_line = $(this).parents('.form-wizard').find('.form-wizard-progress-line');
			
			// fields validation
			parent_fieldset.find('.required').each(function() {
				if( $(this).val() === "" ||  $(this).val() === null || $(this).val() === undefined) {
					$(this).addClass('input-error');
					next_step = false;
				}
				else {
					$(this).removeClass('input-error');
				}
			});
			// fields validation
			
			if( next_step ) {
				parent_fieldset.fadeOut(400, function() {
					// change icons
					current_active_step.removeClass('active').addClass('activated').next().addClass('active');
					// progress bar
					bar_progress(progress_line, 'right');
					// show next step
					$(this).next().fadeIn();
					// scroll window to beginning of the form
					scroll_to_class( $('.form-wizard'), 20 );
				});
			}
		});
		
		// previous step
		$('.form-wizard .btn-previous').on('click', function() {
			// navigation steps / progress steps
			var current_active_step = $(this).parents('.form-wizard').find('.form-wizard-step.active');
			var progress_line = $(this).parents('.form-wizard').find('.form-wizard-progress-line');
			
			$(this).parents('fieldset').fadeOut(400, function() {
				// change icons
				current_active_step.removeClass('active').prev().removeClass('activated').addClass('active');
				// progress bar
				bar_progress(progress_line, 'left');
				// show previous step
				$(this).prev().fadeIn();
				// scroll window to beginning of the form
				scroll_to_class( $('.form-wizard'), 20 );
			});
		});
		
		// submit
		$('.form-wizard').on('submit', function(e) {

			// fields validation
			$(this).find('.required').each(function() {
				if( $(this).val() == "" ) {
					e.preventDefault();
					$(this).addClass('input-error');
				}
				else {
					$(this).removeClass('input-error');
				}
			});
			// fields validation

		});

        // save function for church;
		$('.form-wizard .btn-save-church').on('click', function () {
			var data = {}
			data['churchId'] = churchId;
            data['churchName'] = $('#churchname').val();
            data['address'] = {
                'street' : $('#church_address').val(),
                'city' : $('#city').val(),
                'state' : $('#state').val(),
                'country' : $('#country').val()
            };
            data['churchEmail'] = $('#church_email').val();
            data['churchContactNumber'] = $('#church_contact_number').val();
            data['contactPerson'] = $('#church_contact_person').val();
            data['contactPersonMobileNumber'] = $('#church_contact_person_number').val();

            // $('.btn-save-church').prop("disabled", true);

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/register",
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (data) {
                    // $(".btn-save-church").prop("disabled", false);
                    //...
                    churchId = data;
                    console.log(data);
                },
                error: function (e) {
                    console.log(e);
                }
            });
        });

		// country option on change
        $('#country').on('change', function () {
            var valueSelected = this.value;
            $('#state').empty();
            $('#city').empty();
            var options = [];
            $.ajax({
                type: "GET",
                url: "/common/util/country/states?code="+valueSelected,
                success: function (result) {
                    options.push('<option></option>');
                    for (var i = 0; i < result.length; i++) {
                        options.push('<option value="',
                            result[i], '">',
                            result[i], '</option>');
                    }
                    $("#state").html(options.join(''));
                },
                error: function (e) {
                    //...
                }
            });
        });

        // state option on change
        $('#state').on('change', function () {
            var valueSelected = this.value;
            $('#city').empty();
            var options = [];
            $.ajax({
                type: "GET",
                url: "/common/util/country/cities?code="+$('#country').val()+"&state="+valueSelected,
                success: function (result) {
                    options.push('<option></option>');
                    for (var i = 0; i < result.length; i++) {
                        options.push('<option value="',
                            result[i], '">',
                            result[i], '</option>');
                    }
                    $("#city").html(options.join(''));
                },
                error: function (e) {
                    //...
                }
            });
        });

        // initialise data tables
        var table = $('#registrantTable').DataTable({
            "processing": true,
            "bAutoWidth": true,
            "searching": false,
            "responsive": true,
            "sAjaxSource": "/register/delegates?churchId="+churchId,
            "sAjaxDataProp": "",
            "order": [[ 0, "asc" ]],
            "aoColumns": [
                // { "mData": "id"},
                { "mData": null, render: function ( data, type, row ) {
                    // Combine the first and last names into a single table field
                    return data.firstName+' '+data.lastName;
                } },
                { "mData": "designation" },
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
                    "defaultContent": '<button type="button" id="editBtn"><i class="fa fa-edit"></i></button>'
                },
                {
                    targets:2, render:function(data){
                    return moment(data).format('MM DD YYYY');
                    }
                }
                // {
                //     targets:1, render:function (data) {
                //         console.log(descFormat("designation", data));
                //     return "";
                // }
                // }
            ]
        });

        // for adding or updating registrant
        var editReg = false;

        $('#registrantTable tbody').on( 'click', 'button#editBtn', function () {
            $('#registrantDeleteBtn').show();
            editReg = true;
            var data = table.row( $(this).parents('tr') ).data();

            // $('.registrantForm #id').val(data.id);
            // $('.registrantForm #firstname').val(data.firstName);
            // $('.registrantForm #middlename').val(data.middleName);
            // $('.registrantForm #lastname').val(data.lastName);
            // $('.registrantForm #designation').val(data.designation);
            // $('.registrantForm #birthday').val(data.birthDate);
            // $('.registrantForm #mobilenumber').val(data.mobileNumber);
            // $('.registrantForm #gender').val(data.gender);
            // $('.registrantForm #civilstatus').val(data.civilStatus);
            // $('.registrantForm #email').val(data.email);
            //
            // $('#registrantModal').modal('toggle');

            $.get("/register/delegate/findOne?id="+data.id, function (r, status) {
                $('.registrantForm #id').val(r.id);
                $('.registrantForm #firstname').val(r.firstName);
                $('.registrantForm #middlename').val(r.middleName);
                $('.registrantForm #lastname').val(r.lastName);
                $('.registrantForm #designation').val(r.designation);
                // $('.registrantForm #birthday').val(r.birthDate);
                document.getElementById("birthday").valueAsDate = new Date(r.birthDate);
                $('.registrantForm #mobilenumber').val(r.mobileNumber);
                $('.registrantForm #gender').val(r.gender);
                $('.registrantForm #civilstatus').val(r.civilStatus);
                $('.registrantForm #email').val(r.email);

                $('#registrantModal').modal('toggle');
            });



        });

        // registrant delete

        $('#registrantDeleteBtn').on( 'click',function () {
            var id = $('.registrantForm #id').val();
            var name = $('.registrantForm #firstname').val() +" "+$('.registrantForm #lastname').val();
            $.confirm({
                title: 'Delete delegate?',
                content: 'name: '+name,
                theme: 'modern',
                type: 'red',
                animation: 'top',
                closeAnimation: 'top',
                animateFromElement: false,
                buttons: {
                    tryAgain: {
                        text: 'Delete',
                        btnClass: 'btn-red',
                        action: function(){
                            $.ajax({
                                type: "GET",
                                contentType: "application/json",
                                url: "/register/delegate/delete?id="+id+"&churchId="+churchId,
                                success: function (r) {
                                    table.clear();
                                    table.rows.add(r).draw();
                                    $('#registrantModal').modal('toggle');
                                    toastSuccess(name+" deleted!");
                                },
                                error: function (e) {
                                    //...
                                    $('#registrantModal').modal('toggle');
                                }
                            });
                        }
                    },
                    cancel: function () {

                    }
                }
            });

        });

        // registrant saving

        $('#newRegistrantBtn').on('click', function (e) {
            e.preventDefault();
            $('.registrantForm #id').val('');
            $('.registrantForm #firstname').val('');
            $('.registrantForm #middlename').val('');
            $('.registrantForm #lastname').val('');
            $('.registrantForm #designation').val('');
            $('.registrantForm #birthday').val('');
            $('.registrantForm #mobilenumber').val('');
            $('.registrantForm #gender').val('');
            $('.registrantForm #civilstatus').val('');
            $('.registrantForm #email').val('');

            $('#registrantDeleteBtn').hide();

            $('#registrantModal').modal();
        });

        $('#registrantSaveBtn').on('click', function (e) {
            var data = {}
            if(editReg){
                data['id'] = $('#id').val();
            }
            data['churchId'] = churchId;
            data['firstName'] = $('#firstname').val();
            data['middleName'] = $('#middlename').val();
            data['lastName'] = $('#lastname').val();
            data['designation'] = $('#designation').val();
            data['birthDate'] = $('#birthday').val();
            data['mobileNumber'] = $('#mobilenumber').val();
            data['gender'] = $('#gender').val();
            data['civilStatus'] = $('#civilstatus').val();
            data['email'] = $('#email').val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/register/delegate/save",
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (r) {
                    table.clear();
                    table.rows.add(r).draw();
                    toastSuccess("saved!");
                },
                error: function (e) {
                    //...
                }
            });
            editReg = false;
            $('#registrantModal').modal('toggle');
        });



    });



	// image uploader scripts 

	var $dropzone = $('.image_picker'),
		$droptarget = $('.drop_target'),
		$dropinput = $('#inputFile'),
		$dropimg = $('.image_preview'),
		$remover = $('[data-action="remove_current_image"]');

	$dropzone.on('dragover', function() {
	  $droptarget.addClass('dropping');
	  return false;
	});

	$dropzone.on('dragend dragleave', function() {
	  $droptarget.removeClass('dropping');
	  return false;
	});

	$dropzone.on('drop', function(e) {
	  $droptarget.removeClass('dropping');
	  $droptarget.addClass('dropped');
	  $remover.removeClass('disabled');
	  e.preventDefault();
	  
	  var file = e.originalEvent.dataTransfer.files[0],
		  reader = new FileReader();

	  reader.onload = function(event) {
		$dropimg.css('background-image', 'url(' + event.target.result + ')');
	  };
	  
	  console.log(file);
	  reader.readAsDataURL(file);

	  return false;
	});

	$dropinput.change(function(e) {
	  $droptarget.addClass('dropped');
	  $remover.removeClass('disabled');
	  $('.image_title input').val('');
	  
	  var file = $dropinput.get(0).files[0],
		  reader = new FileReader();
	  
	  reader.onload = function(event) {
		$dropimg.css('background-image', 'url(' + event.target.result + ')');
	  }
	  
	  reader.readAsDataURL(file);
	});

	$remover.on('click', function() {
	  $dropimg.css('background-image', '');
	  $droptarget.removeClass('dropped');
	  $remover.addClass('disabled');
	  $('.image_title input').val('');
	});

	$('.image_title input').blur(function() {
	  if ($(this).val() != '') {
		$droptarget.removeClass('dropped');
	  }
	});

	// image uploader scripts

}( jQuery ));