$.cookie('firstorder', 'null');
$(function() {
	$('#slides').slidesjs({
		width : 558,
		height : 356,
		navigation : {
			effect : "fade"
		},
		pagination : {
			effect : "fade"
		},
		effect : {
			slide : {
				// Slide effect settings.
				speed : 1000
			// [number] Speed in milliseconds of the slide animation.
			},
			fade : {
				speed : 700
			}
		},
		play : {
			active : true,
			auto : true,
			interval : 4000,
			swap : true,
			effect : "fade"
		}
	});
});

$(document).ready(function(e) {

	getcategories(cityid, '#catul', cityname);

});