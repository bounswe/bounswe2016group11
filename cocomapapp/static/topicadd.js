$(document).ready(function(){
  var theTags=[];
  $("#submit").click( function() {
      // console.log("asfdlkjsafdjkladfjs");
      jQuery.support.cors = true;
      var resultTags =[];
      if($('#tags').val() != ""){
          var resultTagIds = $('#tags').val().split(",");
          // console.log(theTags);
          $.each(resultTagIds,function(i,value){
            var result = $.grep(theTags, function(e){ return e.id == value; });
            resultTags.push({
              id : value,
              label : result[0].name
            });
          });
      }
      console.log(resultTags);

      var topic = {
          name: $('#name').val(),
          relates_to: [{topic_id : $('#relates_to').val(), rel_name : $('#relationships-name').val()}],
          tags: resultTags,
          postAdd: $("#postCheckbox").prop("checked"),
          post: {the_text: $("#postText").val(), tags:$('#tags2').val()}
          // relationships_name: $('#relationships-name').val()
      };
      $.ajax({
          url: 'add',
          type: 'POST',
          data:JSON.stringify(topic),
          contentType: "application/json;charset=utf-8",
          success: function (data) {
              window.location.href = "/cocomapapp";
          },
          error: function (x, y, z) {
              alert(x + '\n' + y + '\n' + z);
          }
      });
  });
  //disfunctional code
  $("#name1").on("keyup",function(){

    if($("#name").val().length <2 ){

      return;
    }
    $.getJSON("/cocomapapp/wikidataSearch/" +$("#name").val() + "/"
    ).fail( function() {
      console.log("error in wikidata");
    }).done( function(data) {

        $.each(data,function(i,value){
            theTags.push({
              name : (value.label +" "+ value.description)
            });
            console.log("my values are: "+i+" "+value.label+" "+value.description);

        });

      }
    );
  });



  var relations=[];
  topicsList= JSON.parse(topicsList);


  //relations = relations.join(",");
  $('#relates_to').selectize({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();
        $.each(topicsList,function(i,value){
          relations.push({
            id: value.pk,
            name: value.fields.name
          });
        });
        callback(relations);
      }
  });

  var relations2=[];
  $('#relates_to2').selectize({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();
        $.each(topicsList,function(i,value){
          relations2.push({
            id: value.pk,
            name: value.fields.name
          });
        });
        callback(relations2);
      }
  });

  var relations3=[];
  $('#relates_to3').selectize({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();
        $.each(topicsList,function(i,value){
          relations3.push({
            id: value.pk,
            name: value.fields.name
          });
        });
        callback(relations3);
      }
  });

  $('#tags').selectize({
      maxOptions: 6,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      plugins: ['remove_button'],
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
              $.each(data,function(i,value){
                  theTags.push({
                    id : value.id,
                    name : (value.label +" "+ value.description)
                  });
                  console.log("my values are: "+i+" "+value.label+" "+value.description);
              });
              callback(theTags);
          });

        }
  });

  $('#tags2').selectize({
    maxOptions: 6,
    valueField: 'id',
    labelField: 'name',
    searchField: ['name'],
    plugins: ['remove_button'],
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
            $.each(data,function(i,value){
                theTags.push({
                  id : value.id,
                  name : (value.label +" "+ value.description)
                });
                console.log("my values are: "+i+" "+value.label+" "+value.description);
            });
            callback(theTags);
        });

      }
  });

  $('#cancel_bt').click(function(){
  		parent.history.back();
  		return false;
  });

});
