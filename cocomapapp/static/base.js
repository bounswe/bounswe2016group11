$(function(){
  var searchId = window.location.pathname;
  console.log(searchId);
  var searchId="";

  /*
  When a key written in search area, this funciton is triggered.

  */
  $("#search_field1").on("keyup",function(event){

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

      searchId = "";
    }

  });

  /*
  to show the recommended tags better in the search area, this function is used.
  for ll items in select2 in the base.js the recommendation is shown with this
  template.
  */
  function formatWiki(wikiItem){

    var markup = "<div><p>" + wikiItem.text+" "+ wikiItem.desc + "</p></div>";
    return markup;
  }


  function formatWikiSelection(wikiItem){
    return wikiItem.text;
  }

  /*
    the select2 javascript framework is used to show the search results better
    in our UI. this searches for the query each time a input entered, in wikidata,
    then shows the results as suggested queries in the screen.
  */
  $("#search_field").select2({

    placeholder: "Search...",
    ajax: {
      url: function (params) {
        return "/cocomapapp/wikidataSearch/" +params.term + "/";
      },
      dataType: 'json',
      delay: 250,

      processResults: function (data, params) {
        // parse the results into the format expected by Select2
        // since we are using custom formatting functions we do not need to
        // alter the remote JSON data, except to indicate that infinite
        // scrolling can be used
         params.page = params.page || 1;
        var items =[];
        $.each(data,function(i,value){
          items.push({id:value.id, text: value.label , desc: value.description});

        });
        return {
          results: items,
          pagination: {
              more: (params.page * 30) < data.total_count
          }
        };
      },
      cache: true
    },
    escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
    minimumInputLength: 1,
    templateResult: formatWiki, // omitted for brevity, see the source of this page
    templateSelection: formatWikiSelection // omitted for brevity, see the source of this page

  });

  /*
    when an option is selected in the search field, then the resulting wikidata
    id and label is sent to the search link of our application
  */
  $("#search_field").on("select2:select",function(e){
    console.log(e.params);
    //return;
    var searchId = e.params.data.id;
    var search_url = "/cocomapapp/search/"+searchId+"++"+e.params.data.text+"/";
    window.location.href = search_url;
    return;

  });

});
