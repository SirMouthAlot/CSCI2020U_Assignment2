HTTP/1.1 200 Ok
Content-Type: text/javascript
Date: Thu Apr 01 00:48:24 EDT 2021
Server: Simple-Http-Server v1.0.0
Content-Length: 204
Connection: Close

$(document).ready(function() {
	$('#gallery').coinslider({ 
        hoverPause: true,
        delay: 5000, 
        sDelay: 30, 
        opacity: 0.7, 
        titleSpeed: 1500, 
        effect: ''});
});
