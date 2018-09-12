"use strict";
var churchId;
if (typeof(Storage) !== "undefined") {
    // Code for localStorage/sessionStorage.
} else {
    // Sorry! No Web Storage support..
}
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

    //set an unique id for delegates so we can easily update the delegate array.
    function getSequence() {

	    var s = 0;
        // Get the existing data
        var existing = sessionStorage.getItem('sequence');

        // If no existing data, use the value by itself
        // Otherwise, add the new value to it
        var data = existing ? JSON.parse(existing) + 1 : s;

        // console.log(">>>>>> data sequence: "+data);

        // Save back to localStorage
        sessionStorage.setItem('sequence', data.toString());

        var r = JSON.parse(sessionStorage.getItem('sequence'));

        return r;
    }

	jQuery(document).ready(function() {

        var church = {
            churchName: '',
            churchEmail: '',
            churchContactNumber: '',
            contactPerson: '',
            contactPersonMobileNumber: '',
            address: {
                street: '',
                city: '',
                state: '',
                country: ''
            },
            registrants : []
        };

	    var churches = [];
        $.ajax({
            type: "GET",
            url: "/church/list",
            success: function (result) {
                for (var i = 0; i < result.length; i++) {
                    churches.push(result[i]);
                }
            },
            error: function (e) {
                //...
            }
        });

        // church look up
        $('#church').autocomplete({
            lookup: churches
        });

        $('#church').val('');
        $('#church_address').val('');
        $('#city').val('');
        $('#state').val('');
        $('#country').val('PH');
        $('#church_email').val('');
        $('#church_contact_number').val('');
        $('#church_contact_person').val('');
        $('#church_contact_person_number').val('');

        $('#edit-church-btn').hide();

        //populate city by default philippines
        var cities = [];
        $.ajax({
            type: "GET",
            url: "/common/util/country/states?code=PH",
            success: function (result) {
                cities.push('<option></option>');
                for (var i = 0; i < result.length; i++) {
                    cities.push('<option value="',
                        result[i], '">',
                        result[i], '</option>');
                }
                $("#state").html(cities.join(''));
            },
            error: function (e) {
                //...
            }
        });

		/*
			Form
		*/
		$('.form-wizard fieldset:first').fadeIn('slow');
		
		$('.form-wizard .required').on('focus', function() {
			$(this).removeClass('has-danger');
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
					$(this).addClass('has-danger');
					next_step = false;
				}
				else {
					$(this).removeClass('has-danger');
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

		// church name onchange
        $('#church').on('change', function () {
            $('#churchModal').modal('toggle');
        });

        $('#edit-church-btn').on('click', function () {
            $('#churchModal').modal('toggle');
        });

        // save function for church;
		$('#church-save-btn').on('click', function () {
            //validate require fields
            // var modalField = $(this).parents();
            // // fields validation
            // modalField.find('.required').each(function() {
            //     if( $(this).val() === "" ||  $(this).val() === null || $(this).val() === undefined) {
            //         $(this).addClass('has-danger');
            //     }
            //     else {
            //         $(this).removeClass('has-danger');
            //         $('#churchModal').modal('toggle');
            //     }
            // });
            $('#churchModal').modal('toggle');
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

        // ******************* initialise data tables using ajax call *******************
        // var table = $('#registrantTable').DataTable({
        //     "info":     false,
        //     "paging":   false,
        //     "processing": true,
        //     "bLengthChange": false,
        //     "ordering": false,
        //     "bAutoWidth": true,
        //     "searching": false,
        //     "responsive": true,
        //     "sAjaxSource": "/register/delegates?churchId="+churchId,
        //     "sAjaxDataProp": "",
        //     "order": [[ 0, "asc" ]],
        //     "aoColumns": [
        //         // { "mData": "id"},
        //         { "mData": null, render: function ( data, type, row ) {
        //             // Combine the first and last names into a single table field
        //             return data.firstName+' '+data.lastName;
        //         } },
        //         { "mData": "designation" },
        //         { "mData": "birthDate" },
        //         { "mData": "gender" },
        //         { "mData": "civilStatus" },
        //         { "mData": "mobileNumber" },
        //         { "mData": "email" },
        //         { "mData": null}
        //     ],
        //     "columnDefs": [
        //         {
        //             "responsivePriority":1,
        //             "targets": -1,
        //             "data": null,
        //             "defaultContent": '<button type="button" id="editBtn"><i class="fa fa-edit"></i></button>'
        //         },
        //         {
        //             targets:2, render:function(data){
        //             return moment(data).format('MM DD YYYY');
        //             }
        //         }
        //     ]
        // });

        var delegates = [];

        sessionStorage.setItem("delegates", JSON.stringify(delegates));

        // console.log("**** "+JSON.parse(sessionStorage.getItem("delegates")));

        var table = $('#registrantTable').DataTable( {
            // "info":     false,
            // "paging":   false,
            "processing": true,
            "bLengthChange": false,
            "ordering": false,
            "bAutoWidth": true,
            "searching": false,
            "responsive": true,
            data: delegates,
                "aoColumns": [
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
                    { "mData": null},
                    { "mData": "rId"}
                ],
                "columnDefs": [
                    {
                        "responsivePriority":1,
                        "targets": -2,
                        "data": null,
                        "defaultContent": '<button type="button" id="editBtn"><i class="fa fa-edit"></i></button>'
                    },
                    {
                        targets:2, render:function(data){
                        return moment(data).format('MM DD YYYY');
                        }
                    },
                    {
                        "targets": [ -1 ],
                        "visible": false
                    }
                ]
        } );

        // delegate saving

        $('#newRegistrantBtn').on('click', function (e) {
            var ch = $('#church').val();
            if(ch==null || ch == ""){
                $.confirm({
                    title: 'No church selected!',
                    content: 'Please select church to add delegates.',
                    type: 'red',
                    typeAnimated: false,
                    buttons: {
                        close: function () {
                        }
                    }
                });
            }else {
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
            }
        });

        var editReg = false;

        $('#registrantSaveBtn').on('click', function () {
            // console.log("saving delegates");
            let data = {};

            let rId;

            if(!editReg){
                data['rId'] = getSequence();
                data['firstName'] = $('#firstname').val();
                data['middleName'] = $('#middlename').val();
                data['lastName'] = $('#lastname').val();
                data['designation'] = $('#designation').val();
                data['birthDate'] = $('#birthday').val();
                data['mobileNumber'] = $('#mobilenumber').val();
                data['gender'] = $('#gender').val();
                data['civilStatus'] = $('#civilstatus').val();
                data['email'] = $('#email').val();


                let existing = sessionStorage.getItem("delegates");

                delegates = JSON.parse(existing);

                // move element to first index
                delegates.splice(0,0, data);

                sessionStorage.setItem("delegates", JSON.stringify(delegates));

                let n = sessionStorage.getItem("delegates");

                table.clear();
                table.rows.add(JSON.parse(n)).draw();

                // console.log("delegates added: "+JSON.parse(n));
            } else {

                let existing = sessionStorage.getItem("delegates");
                delegates = JSON.parse(existing);

                rId = $('.registrantForm #id').val();

                let d = delegates.find((p) => {
                        return p.rId == rId;
                });

                d.firstName = $('#firstname').val();
                d.middleName = $('#middlename').val();
                d.lastName = $('#lastname').val();
                d.designation = $('#designation').val();
                d.birthDate = $('#birthday').val();
                d.mobileNumber = $('#mobilenumber').val();
                d.gender = $('#gender').val();
                d.civilStatus = $('#civilstatus').val();
                d.email = $('#email').val();

                sessionStorage.setItem("delegates", JSON.stringify(delegates));

                let n = sessionStorage.getItem("delegates");

                table.clear();
                table.rows.add(JSON.parse(n)).draw();

                // console.log("delegates edited: "+JSON.parse(n));
            }
            $('#registrantModal').modal('toggle');
            editReg = false;
        });

        $('#registrantTable tbody').on( 'click', 'button#editBtn', function () {
            // console.log("editing delegate");
            editReg = true;

            $('#registrantDeleteBtn').show();
            var r = table.row( $(this).parents('tr') ).data();

            $('.registrantForm #id').val(r.rId);
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

        // registrant delete

        $('#registrantDeleteBtn').on( 'click',function () {
            let id = $('.registrantForm #id').val();
            let name = $('.registrantForm #firstname').val() +" "+$('.registrantForm #lastname').val();
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
                            let existing = sessionStorage.getItem("delegates");

                            delegates = JSON.parse(existing);

                            delegates.splice(delegates.indexOf(id));

                            sessionStorage.setItem("delegates", JSON.stringify(delegates));

                            let n = sessionStorage.getItem("delegates");

                            table.clear();
                            table.rows.add(JSON.parse(n)).draw();

                            $('#registrantModal').modal('toggle');
                        }
                    },
                    cancel: function () {

                    }
                }
            });

        });

        // submit form
        $('.test').on('click', function () {

            church.churchName = $('#church').val();
            church.address = {
                'street' : $('#church_address').val(),
                'city' : $('#city').val(),
                'state' : $('#state').val(),
                'country' : $('#country').val()
            }
            church.churchEmail =  $('#church_email').val();
            church.churchContactNumber = $('#church_contact_number').val();
            church.contactPerson = $('#church_contact_person').val();
            church.contactPersonMobileNumber = $('#church_contact_person_number').val();

            let n = sessionStorage.getItem("delegates");

            let newDelegates = [];

            delegates = JSON.parse(n);

            church.registrants = delegates;

            console.log(church);

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/register",
                data: JSON.stringify(church),
                dataType: 'json',
                success: function (data) {
                    churchId = data;
                    console.log(data);
                },
                error: function (e) {
                    console.log(e);
                }
            });

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