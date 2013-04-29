$('tags').on(function(){
	$(this).autocomplete({
		source: function(request, response) {
			$.getJSON("/AutocompleteServlet", { lastName: request.term }, response);
	}});
});

/*$(function() {
		$( "#tags" ).autocomplete({
		source: function(request, response) {
		    $.getJSON("/AutocompleteServlet", { lastName: request.term }, response);
		  }
	});
});
*/