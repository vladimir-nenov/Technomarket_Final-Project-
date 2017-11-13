function handleBasket(nameIndex){
			var quantity = document.getElementById("product" + nameIndex).value;
			if(quantity == "0"){
				removeProduct(nameIndex);
			}
			else{
				changeQuantity(quantity, nameIndex);
			}
		}

function changeQuantity(quantity, nameIndex) {
		var request = new XMLHttpRequest();
		var params = "?product=" + nameIndex + "&quantity=" + quantity;
		request.onreadystatechange = function() {
			//when response is received
			if (this.readyState == 4 && this.status == 200) {
			}
			else
			if (this.readyState == 4 && this.status == 401) {
				alert("Sorry!");
			}
				
		}
		request.open("post", "../basket/quantity" + params, true);
		request.send();
		window.location.reload();
}

function removeProduct(nameIndex) {
	var request = new XMLHttpRequest();
	var submitId = "remove" + nameIndex;
	document.getElementById(submitId).submit();
}