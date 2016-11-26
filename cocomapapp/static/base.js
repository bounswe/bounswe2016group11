$(function(){
  var wikiItems=[];
  var searchId="";
  $("#search_field").on("keyup",function(event){

    var searchInput = $("#search_field").val();
    if(searchInput.length <2 ){

      return;
    }
    if(event.which != 13){// if enter not hit
      $.getJSON("/cocomapapp/wikidataSearch/" +searchInput + "/"
      ).fail( function() {
        console.log("error in wikidata");
      }).done( function(data) {

          $.each(data,function(i,value){
              wikiItems.push({
                id: value.id ,
                name : (value.label +" "+ value.description)
              });
              console.log("my values are: "+i+" "+value.label+" "+value.description);

          });

        }
      );
    }
    else if(searchId != ""){
      $.getJSON("/cocomapapp/wikidataQuery/" +searchInput + "/"
      ).fail( function() {
        console.log("error in wikidata");
      }).done( function(data) {
          if(data.length>0){
            wikiItems = [];
          }
          $.each(data,function(i,value){
            var toRegex = value.valUrl.value;
            var theRegex = /.*\/([a-z]+?\d+?)$/im ;
            var fetchedWikiId = theRegex.exec(toRegex)[1];
            wikiItems.push({
              wikiId : fetchedWikiId
            });
            console.log("my values are: "+i+" "+value.label+" "+value.description);
            console.log("My Id: "+fetchedWikiId);
          });

        }
      );
      searchId = "";
    }

  });
});
