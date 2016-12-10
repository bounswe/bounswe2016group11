var topics=[];
get_topic_list();
var relations=[];

$(document).ready(function () {


  $('#relates_to').selectize({
      maxItems: 1,
      maxOptions: 5,
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

  $('#relates_to2').selectize({
      maxItems: 1,
      maxOptions: 5,
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
      maxOptions: 5,
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


});

function get_topic_list(){
  $.ajax({
    url: "/cocomapapp/topicList",
    dataType:"json"
  }).done(function(data){
    topics = data;
    $.each(topics,function(i,value){
      relations.push({
        id: value.id,
        name: value.name
      });
    });
    return;
  }).fail(function(){
    return "Server Connection Failed";
  });
}
