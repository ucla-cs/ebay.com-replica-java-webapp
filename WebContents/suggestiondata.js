/**
 * Provides suggestions for state names (USA).
 * @class
 * @scope public
 */
function StateSuggestions() {

}

/**
 * Request suggestions for the given autosuggest control. 
 * @scope protected
 * @param oAutoSuggestControl The autosuggest control to provide suggestions for.
 */
StateSuggestions.prototype.requestSuggestions = function (oAutoSuggestControl /*:AutoSuggestControl*/,
                                                          bTypeAhead /*:boolean*/) {
    var aSuggestions = [];
    var sTextboxValue = oAutoSuggestControl.textbox.value;
    
    console.log("HELLO");
    
    if (sTextboxValue.length > 0){
    	
    	var states = showSuggestion();
    	console.log(states);
        //search for matching states
        for (var i=0; i < states.length; i++) { 
            //if (states[i].indexOf(sTextboxValue) == 0) {
                aSuggestions.push(states[i]);
            
        }
        
    	
    	console.log("SENT Suggestion"+sTextboxValue);
		
		//provide suggestions to the control
		//oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead); 
        
    }

    //provide suggestions to the control
    oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead);
};