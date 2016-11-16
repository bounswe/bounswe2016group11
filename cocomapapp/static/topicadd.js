$(document).ready(function(){

  var theTags=[{"name": "asd"},{"name": "sda"}];
  var citynames = new Bloodhound({

    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    local: theTags
  });
  citynames.initialize();

  $('#tags2').tagsinput({
    typeaheadjs: {
      name: 'name',
      displayKey: 'name',
      valueKey: 'name',
      source: citynames.ttAdapter()
    }
  });

  $('#tags').tagsinput({
    typeaheadjs: {
      allowDuplicates: false,
      itemValue: 'name',  // this will be used to set id of tag
      itemText: 'name', // this will be used to set text of tag
      source: citynames.ttAdapter()
    }
  });
  $("#name").on("keyup",function(){

    if($("#name").val().length <2 ){

      return;
    }
    $.getJSON("/cocomapapp/wikidataSearch/" +$("#name").val() + "/"
    ).fail( function() {
      console.log("error in wikidata");
    }).done( function(data) {

        $.each(data,function(i,value){
            theTags.push({
              name : (value.label + value.description)
            });

        });

        $("#tags").tagsinput("input").typeahead({
          source: function(query, process) {
              process(theTags);
          }
        });
        /*
        $.each(theTags,function(i,value){
          console.log(value);
          $("#tags").tagsinput("add",value);
        });*/
        console.log(theTags);

      }
    );

    //$('#tags').tagsinput('destroy');
    //$('#tags2').tagsinput('destroy');
    /*
    var citynames = new Bloodhound({

      datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
      queryTokenizer: Bloodhound.tokenizers.whitespace,
      local: theTags
    });
    citynames.initialize();

    $('#tags2').tagsinput({
      typeaheadjs: {
        name: 'name',
        displayKey: 'name',
        valueKey: 'name',
        source: citynames.ttAdapter()
      }
    });

    $('#tags').tagsinput({
      typeaheadjs: {
        name: 'name',
        displayKey: 'name',
        valueKey: 'name',
        source: citynames.ttAdapter()
      }
    });
    */
  });

  var relations=[];
  /*
  $.getJSON("/static/tags.json", function(data){
    $.each(data,function(i,value){
        relations.push({
          id: i,
          name: value
        });
    });
  });*/
  //relations = relations.join(",");
  $('#relationships-topic').selectize({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'name',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();
        $.getJSON("/static/tags.json"
        ).fail( function() {
            callback();
        }).done( function(data) {
            $.each(data,function(i,value){
                relations.push({
                  id: i,
                  name: value
                });
            });
            callback(relations);
          }
        );
        }
  });

  /*
  $("#tags").on("keypress",function(event){
    console.log("asda");
    if(event.which == 13){
        alert("x");
    }

  });*/
  /*
  $("#relationships-topic").removeAttr("id");
  $(".newRelation").append(
    "<input type='text' id='relationships-name' class='form-control makeinline' placeholder='Relationship Name'>"

    +"<input id='relationships-topic' class='makeinline' placeholder='Name of a Topic'>"
  );
*/

  //publish button functionalities...
  // if the django's own form is used, then this is to be removed
  $("#publish").click(function(){
      topic_name = $("#topicName").val();
      tags_name = $("#tags").val();
      relationships = $("#relationships").val();
      isChecked = $("#checkbox:checked").length;
      post = "";

      console.log(isChecked);
      if(isChecked == 1){
          post = $("#post").val();
      }
      console.log(post);
      var obj = { 'topic_name' : topic_name, 'tags_name' : tags_name, 'relationships' : relationships,
        'isChecked' : isChecked, 'post' : post };
      console.log(JSON.stringify(obj));
      window.location.href = "secondTopic.html";
  });
  $('#cancel_bt').click(function(){
  		parent.history.back();
  		return false;
  });
  /* //Example Get
  $("#tags2").on("keydown",function(event){
       console.log("geldi");
       if(event.which == 13)
         console.log($("#tags").val());
         console.log($("#tags").val());
         $.get("add",{"search": $("#tags").val()}).done(function(data){
         console.log("data come: "+ data);
        });
  });
  */
});
