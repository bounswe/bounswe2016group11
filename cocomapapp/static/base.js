$(function(){

  var searchId="";
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
  $('#search_field').selectize({
      maxItems: 1 ,
      maxOptions: 6,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
          if(query.length <2 ){
            return [];
          }
          $.getJSON("/cocomapapp/wikidataSearch/" +query+ "/"
          ).fail( function() {
            console.log("error in wikidata");
          }).done( function(data) {
              var wikiItems = [];
              $.each(data,function(i,value){
                  wikiItems.push({
                    id : value.id,
                    name : (value.label +" "+ value.description)
                  });
                  console.log("my values are: "+i+" "+value.label+" "+value.description);
              });
              callback(wikiItems);
          });

        }
  });
  */

  function formatWiki(wikiItem){

    var markup = "<div><p>" + wikiItem.text+"</p></div>";
    return markup;
  }
  function formatWikiSelection(wikiItem){
    return wikiItem.text;
  }
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
          items.push({id:value.id, text: value.label +" "+ value.description});

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

  $("#search_field").on("select2:select",function(e){
    var searchId = e.params.data.id;
    $.getJSON("/cocomapapp/wikidataQuery/" +searchId + "/"
    ).fail( function() {
      console.log("error in wikidata");
    }).done( function(data) {
        var wikiItems=[];
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
  });

});
