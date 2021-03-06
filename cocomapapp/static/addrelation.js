
function getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = jQuery.trim(cookies[i]);
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}
var csrftoken = getCookie('csrftoken');

function csrfSafeMethod(method) {
    // these HTTP methods do not require CSRF protection
    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
}
$.ajaxSetup({
    beforeSend: function(xhr, settings) {
        if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
            xhr.setRequestHeader("X-CSRFToken", csrftoken);
        }
    }
});

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

  $("#submit2").on("click", function() {
    var relations =[];
    if($('#relates_to').val() && $('#relates_to').val() != topic_id){
      var relation={};
      relation.topic_from = topic_id;
      relation.topic_to = $('#relates_to').val();
      relation.label = $('#relationships-name').val();
      relations.push(relation);
    }
    if($('#relates_to2').val() && $('#relates_to2').val() != topic_id){
      var relation2={};
      relation2.topic_from = topic_id;
      relation2.topic_to = $('#relates_to2').val();
      relation2.label = $('#relationships-name2').val();
      relations.push(relation2);
    }
    if($('#relates_to3').val()&& $('#relates_to3').val() != topic_id){
      var relation3={};
      relation3.topic_from = topic_id;
      relation3.topic_to = $('#relates_to3').val();
      relation3.label = $('#relationships-name3').val();
      relations.push(relation3);
    }
    if(relations.length ==0){
      return;
    }

    $.ajax({
        url: '/cocomapapp/relationCreate',
        type: 'POST',
        data:JSON.stringify(relations),
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            window.location.href = "/cocomapapp/infocus/"+topic_id;
        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }
    });
  });
  $('#cancel_bt').click(function(){
  		parent.history.back();
  		return false;
  });

});


function get_topic_list(){
  $.ajax({
    url: "/cocomapapp/topicList",
    contentType: "application/json;charset=utf-8",
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
