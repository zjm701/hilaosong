var tempH = 0;
var tempoff = 0;
$(function(){
 var h= $(document.body).outerHeight(true);
 var h= $(window).height()+tempoff;
// tempH = 1150;
 $("body").append('<iframe id="showIFrameHeight" style="display:none;" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="'+serverAndPort+'/pages/haidl/order_agent.jsp?t='+ new Date().getTime()+'#height=' + tempH + '" ></iframe>');
});

function caluteIFrameHeight()
{
	$("#showIFrameHeight").remove();
	//var h = $(document.body).outerHeight(true);
	//var h3 = $(document.body).outerHeight(true);
	//var h1 = $(document.body).outerHeight();
	var h = $(window).height();
	if(tempH != 1150)
	{
		tempH = 1150;
//		var h = h+tempoff;
		var h = tempH;
		$("body").append('<iframe id="showIFrameHeight" style="display:none;" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="'+serverAndPort+'/pages/haidl/order_agent.jsp?t='+ new Date().getTime()+'#height=' + h + '" ></iframe>');
		
	}
}
setInterval(caluteIFrameHeight, 1000);
