function submitQuery(index) {
		var product = document.getElementById("product").value;
		var store = document.getElementById("store" + index).value;
		var amount = document.getElementById("amount" + index).value;
		var request = new XMLHttpRequest();
		var params = "?product=" + product + "&store=" + store + "&amount=" + amount;
		
		request.onreadystatechange = function() {
			//when response is received
			if (this.readyState == 4 && this.status == 200) {
			}
			else
			if (this.readyState == 4 && this.status == 401) {
				alert("Sorry, you must be admin to like this video!");
			}
				
		}
		request.open("post", "../store/quantity" + params, true);
		request.send();
	}


function empty() {
    var searchedText;
    searchedText = document.getElementById("searched-text").value;
    if (!searchedText || 0 === searchedText) {
        return false;
    };
}