$(document).ready(function(){
  var theTags=[];
  $("#submit").click( function() {
      // console.log("asfdlkjsafdjkladfjs");
      jQuery.support.cors = true;
      var resultTagIds = $('#tags').val();
      if(resultTagIds != ""){
        resultTagIds = resultTagIds.split(",");
      }
      else{
        resultTagIds = [];
      }
      var resultTags =[];
      console.log(resultTagIds);
      $.each(resultTagIds,function(i,value){
        var result = $.grep(theTags, function(e){ return e.id == value; });
        resultTags.push({
          id : value,
          label : result[0].name
        });
      });

      var topic = {
          name: $('#name').val(),
          relates_to: [{topic_id : $('#relates_to').val(), rel_name : $('#relationships-name').val()}],
          tags: resultTags,
          postAdd: $("#postCheckbox").prop("checked"),
          post: {the_text: $("#postText").val(), tags:$('#tags2').val()}
          // relationships_name: $('#relationships-name').val()
      };
      if($('#relates_to2').val()){
          topic.relates_to.push({topic_id : $('#relates_to2').val(), rel_name : $('#relationships-name2').val()});
      }
      if($('#relates_to3').val()){
          topic.relates_to.push({topic_id : $('#relates_to3').val(), rel_name : $('#relationships-name3').val()});
      }

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
        callback(relations);
      }
  });

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
        callback(relations);
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
