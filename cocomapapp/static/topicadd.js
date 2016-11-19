function AddTopic() {
    jQuery.support.cors = true;
    var topic = {
        name: $('#name').val(),
        relates_to: $('#relates_to').val(),
        tags: $('#tags').val(),
        posts: [],
        relationships_name: $('#relationships-name').val(),
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
}

$(document).ready(function(){

  var theTags=[];
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
      valueField: 'name',
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


  $('#tags').selectize({
      maxOptions: 6,
      valueField: 'name',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();

          $.each(topicsList,function(i,value){
              theTags.push({
                id: value.pk,
                name: value.fields.name
              });
          });
          callback(theTags);
          }
  });

  $('#tags2').selectize({
      maxOptions: 6,
      valueField: 'name',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();

          $.each(topicsList,function(i,value){
              theTags.push({
                id: value.pk,
                name: value.fields.name
              });
          });
          callback(theTags);
          }
  });

  /*
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
  });*/
  $('#cancel_bt').click(function(){
  		parent.history.back();
  		return false;
  });

});
