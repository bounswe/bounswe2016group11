function AddTopic() {
    jQuery.support.cors = true;
    var topic = {
        name: $('#name').val(),
        relates_to: $('#relates_to').val(),
        tags: $('#tags').val(),
        posts: [],
    };
    $.ajax({
        url: 'add',
        type: 'POST',
        data:JSON.stringify(topic),
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            return data;
        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }
    });
}



$(document).ready(function(){


/*
  var citynames = new Bloodhound({

    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    prefetch: {
      url: '/static/tags.json',
      filter: function(list) {
        return $.map(list, function(cityname) {
          return { name: cityname }; });
      }
    }
  });
  citynames.initialize();

  $('#tags2').tagsinput({
    typeaheadjs: {
      name: 'citynames',
      displayKey: 'name',
      valueKey: 'name',
      source: citynames.ttAdapter()
    }
  });

  $('#tags').tagsinput({
    typeaheadjs: {
      name: 'citynames',
      displayKey: 'name',
      valueKey: 'name',
      source: citynames.ttAdapter()
    }
  });

  var relations=[];

  $.getJSON("/static/tags.json", function(data){
    $.each(data,function(i,value){
        relations.push({
          id: i,
          name: value
        });
    });
  });*/
  //relations = relations.join(",");
 /* $('#relationships-topic').selectize({
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
  });*/

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
