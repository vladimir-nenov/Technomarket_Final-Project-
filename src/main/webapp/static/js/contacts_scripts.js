function isEmpty() {
    var searchedText;
    searchedText = document.getElementById("searched-text").value;
    if (!searchedText || 0 === searchedText) {
        return false;
    };
}