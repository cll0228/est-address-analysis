// jQuery Tip Dialogs Plugin
//
// Author:
//       lufeng
//
// Usage:
//		$.messageTip.info(option);
// 
// History:
//		1.00 - Released (19 July 2014)
//
(function($) {
	
	var defaults = {
		zIndex : 9999 ,
		fadeInTimeOut : 1,   //second
		contentTimeOut : 3,  
		fadeOutTimeOut : 1,
		position : "top"     //show position     top  or bottom
	};
				 
	$.messageTip = {
		// Public methods
		show: function(option){
			$.messageTip._show(option);
		},
		
		info: function(option){
			option.type = "info";
			$.messageTip._show(option);
		},
		
		warning: function(option){
			option.type = "warning";
			$.messageTip._show(option);
		},
		
		error: function(option){
			option.type = "error";
			$.messageTip._show(option);
		},
		
		success: function(option){
			option.type = "success";
			$.messageTip._show(option);
		},
		// Private methods
		
		_show: function(option) {
			
			var option = $.extend(defaults, option);

			var message = option.message,
			    type = option.type,
				timeout = option.timeout;
			var isie6 =  ($.browser.msie && parseInt($.browser.version) <= 9)  ;

			var callback = function(result) {
				try{
				if( option.callback ) option.callback(result);
				}catch(e){}
			};
			$("#slideout_message").remove();
		 			
			$("BODY").append(
			  '<div id="slideout_container" class="ui-slideout-tip">' +
			    '<div id="slideout_content" class="slideout_content slideout_content_' + type + '">' +
			      '<div id="slideout_message"></div>' +
				'</div>' +
			  '</div>');
			
			// IE6 Fix
			var pos = isie6 ? 'absolute' : 'fixed'; 
			
			$("#slideout_container").css({
				position: pos,
				zIndex: option.zIndex,
				padding: 0,
				margin: 0
			});
		
			$("#slideout_message").html(message);
			$("#slideout_content").addClass("slideout-corner-" + option.position);
			
			
			$.messageTip._reposition(option.position);
			$.messageTip._fadeIn(option, isie6);

			
		},
		_fadeIn :function(option, isie6){

			var beforeTop, afterTop;
			var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
			if(option.position == "top"){
				//:这里不简写，容易理解些
				if(isie6){
					
					beforeTop = scrollTop + $("#slideout_container").outerHeight() * -1;
					afterTop = scrollTop;
				} else{
					beforeTop = $("#slideout_container").outerHeight() * -1;
					afterTop = 0;
				}
			}
			if(option.position == "bottom"){
				var clientHeight = document.documentElement.clientHeight;
				if(isie6){
					beforeTop = scrollTop + clientHeight;
					afterTop = scrollTop + clientHeight + $("#slideout_container").outerHeight() * -1;				
				} else{
					beforeTop = clientHeight;
					afterTop =  clientHeight + $("#slideout_container").outerHeight() * -1;			
				}
			}
			$("#slideout_container").css({ top : beforeTop});
			$("#slideout_container").animate({ top : afterTop}, option.fadeInTimeOut * 1000);
			
			//内容显示
			window.messageTipTimeout = window.setTimeout(function(){
					$.messageTip._fadeOut(option, isie6) ;
			},option.contentTimeOut * 1000) ;
		},
		_fadeOut: function(option, isie6) {
			var beforeTop, afterTop;
			var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
			if(option.position == "top"){
				if(isie6){
					beforeTop = scrollTop;
					afterTop = scrollTop + $("#slideout_container").outerHeight() * -1;
				} else{
					beforeTop = 0;
					afterTop =  $("#slideout_container").outerHeight() * -1;	
				}
			}
			if(option.position == "bottom"){
				var clientHeight = document.documentElement.clientHeight;
				if(isie6){
					beforeTop = scrollTop + clientHeight+ $("#slideout_container").outerHeight() * -1;
					afterTop = scrollTop + clientHeight;
				} else {
					beforeTop = clientHeight+ $("#slideout_container").outerHeight() * -1;
					afterTop =  clientHeight;					
				}
			}
			$("#slideout_container").css({ top : beforeTop});
			$("#slideout_container").animate({ top : afterTop}, option.fadeOutTimeOut * 1000);
			window.setTimeout(function(){
				$("#slideout_container").remove();
			},option.fadeOutTimeOut * 1000) ;
		},	
		_reposition: function(position) {

			var left = (($(window).width() / 2) - ($("#slideout_container").outerWidth() / 2));
			var css = {} ;
 			css["left"] = left + 'px';
			$("#slideout_container").css(css);
			
		}
		
	}
	
	// Shortuct functions
})(jQuery);