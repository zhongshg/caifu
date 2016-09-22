var swfu;
		window.onload = function  () {
			swfu = new SWFUpload1({
				// Backend Settings
				upload_url: "up_photo.jsp",
                post_params : {
                    "ASPSESSID" : ""
                },

				// File Upload Settings
				file_size_limit : "200 MB",
				file_types : "*.jpg;*.gif;*.png",
				file_types_description : "JPG Images",
				file_upload_limit : "0",    // Zero means unlimited

				// Event Handler Settings - these functions as defined in Handlers.js
				//  The handlers are not part of SWFUpload but are part of my website and control how
				//  my website reacts to the SWFUpload events.
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,

				// Button settings
				button_image_url : "/images/up.png",                          
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width: 87,
				button_height: 27,
				//button_text : '<span class="button">上传<span class="buttonSmall">(2 MB Max)</span></span>',
				//button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 14pt; } .buttonSmall { font-size: 10pt; }',
				button_text_top_padding: 1,
				button_text_left_padding: 5,

				// Flash Settings
				flash_url : "/swfupload/swfupload1.swf",	// Relative to this file

				custom_settings : {
					upload_target : "divFileProgressContainer"
				},

				// Debug Settings
				debug: false
			});
		}