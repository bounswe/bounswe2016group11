
/*
get the cookie information
*/
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


/*
used to collect all the information when sending a tag, the get_hidden_tags
method is used to get the hidden_tags of an entity.

*/
function get_tags(wikiId,theTags){
  var waitNum;
  var resultTag;
  var hidden_tags=[];
  var props=["P31","P610","P37","P36", "P30","P190","P17","P190",
  "P27", "P425","P279"];// the properties of an entity to store in our system as hidden tags
  /*
  get the tags of each tag, wikidata entity added.
  then get its hidden tags using get hidden tags and adds its
  */
  $.ajax({
    url : "/cocomapapp/wikidataQuery/" +wikiId + "/" ,
    async:false,
    dataType:"json"
  }).fail( function() {
    console.log("error in wikidata");
  }).done( function(data) {
    hidden_tags=get_hidden_tags(props,data);
    var occupation = get_hidden_tags(["P106"],data);
    waitNum = occupation.length;
    $.each(occupation,function(i,value){
      $.ajax({
        url : "/cocomapapp/wikidataQuery/" +value + "/" ,
        async:false,
        dataType:"json"
      }).fail( function() {
        console.log("error in wikidata");
      }).done( function(data2) {
        hidden_tags = hidden_tags.concat(get_hidden_tags(["P425"],data2) );
      });
    });
    var result = $.grep(theTags, function(e){ return e.id == wikiId; });

    resultTag = {
      "id" : wikiId,
      "label" : result[0].name ,
      "hidden_tags": hidden_tags
    };
  });
  return resultTag;
}

/*
  from the given wikidata entity json(the_json), the specified
  properties(in props variable) are extracted and returned.
*/
function get_hidden_tags(props,the_json){
  var hidden_tags=[];
  var sem_att_list=props;
  $.each(the_json,function(i,value){
    var temp_url = value.propUrl.value;
    var cond=false;
    for(var j=0; j<sem_att_list.length;j++){
      if(temp_url.endsWith(sem_att_list[j])){
        cond=true;
        //console.log(value.propLabel.value);
        break;
      }
    }
    if(cond){
      var toRegex = value.valUrl.value;
      var theRegex = /.*\/([a-z]+?\d+?)$/im ;
      var fetchedWikiId = theRegex.exec(toRegex)[1];
      hidden_tags.push(fetchedWikiId);
    }
  });
  //console.log(hidden_tags);
  return hidden_tags;
}

function vote(currUser, post_id, is_positive){  //For upvoting
    var vote_data = {
        post_id: post_id,
        is_positive: is_positive
        // relationships_name: $('#relationships-name').val()
    };
  //console.log("upVote deyim");
  $.ajax({
    url: '/cocomapapp/postVote/',
    type: 'POST',
    data: vote_data,
    success: function (data) {
      //console.log('success: ', data);
      var upColor = "grey";
      var downColor = "grey";
      $.each(data.votes, function(j, obj2){
          var vote_user = obj2.user;
          if (currUser == vote_user){
              if (obj2.is_positive){
                  upColor = "blue";
              }
              else{
                  downColor = "blue"
              }
          }
      });

      $('#t'+post_id).hide();
      $('#'+post_id).replaceWith('<a href="#" id="'+ post_id +'" onclick="vote('+currUser+','+ post_id+ ',true);"><span class="glyphicon glyphicon-thumbs-up" style="color:'+upColor+'"></span></a>')
      $('#'+post_id).after('<span id="t'+post_id+'" style="color:green;">'+ data.positive_reaction_count +'</span>');
      $('#d'+post_id).hide();
      $('#-'+post_id).replaceWith('<a href="#" id="-'+ post_id +'" onclick="vote('+currUser+','+ post_id+ ',false);"><span class="glyphicon glyphicon-thumbs-down" style="color:'+downColor+'"></span></a>')
      $('#-'+post_id).after('<span id="d'+post_id+'" style="color:red;">'+ data.negative_reaction_count +'</span>');
      var accuracy = data.accuracy;//(data.positive_reaction_count+data.negative_reaction_count)>0?(data.positive_reaction_count/(data.positive_reaction_count+data.negative_reaction_count))*100:0;

      $('#a'+post_id).replaceWith('<span id="a'+post_id+'">'+accuracy.toFixed(2)+'% </span>');


    },
    error: function (x, y, z) {
        console.log("error");
    }
  });
}
$(document).ready(function(){
  var currUser = document.getElementById("userId").value;
  var url = document.baseURI.split('/');
  var lastSegment = '';
  lastSegment = url.pop()
  lastSegment = url.pop()

  console.log(lastSegment)
  var visitData = {
      user: currUser,
      topic: lastSegment,
  };
  $.ajax({
    url: '/cocomapapp/visitCreate/',
    type: 'POST',
    contentType: "application/json;charset=utf-8",
    data: JSON.stringify(visitData),
    success: function (data) {
      console.log('Visit recorded')
    }
    });
  //console.log( currUser )
  topic = JSON.parse(topic);
  hot_topics = JSON.parse(hot_topics);
  console.log(topic);
  var title = topic.name;
  var topic_tags = topic.tags;
  var posts = topic.posts;

  $("#theTitle").text(title);
  var option_labels=["label-primary","label-success","label-info","label-warning","label-danger"];
  $.each(topic_tags, function(i,val){
    $("#topicTags").append(
      "<span style='display: inline-block;' class='label "+option_labels[i%option_labels.length]+"'>" +val.name +"</span>"
    );
  });
  $.each(posts, function(i, obj) {
    var text = obj.content;
    //var post_tags = obj.tags;
    var post_tags = obj.tags;
    var user = obj.user;
    //for accuracy first positive then negative

    var accuracy = obj.accuracy;//(obj.positive_reaction_count+obj.negative_reaction_count)>0?(obj.positive_reaction_count/(obj.positive_reaction_count+obj.negative_reaction_count))*100:0;
    var tagsAsStr="";

    var upColor = "grey";
    var downColor = "grey";
    $.each(obj.votes, function(j, obj2){
        var vote_user = obj2.user;
        //console.log(user.id+" "+vote_user)
        if (currUser == vote_user){
            if (obj2.is_positive){
                upColor = "blue";
            }
            else{
                downColor = "blue"
            }
        }
    });

    $.each(post_tags, function(i,val){
        tagsAsStr +="<span style='display: inline-block;' class='label "+option_labels[i%option_labels.length] +"'>" +val.name +"</span>";
    });
    var updateDeleteSpan = ""
    if (user.id == currUser) {
      var updateDeleteSpan = "<span data-toggle='modal' data-target='.bs-example-modal-sm' data-id='"+obj.id+"' title='Add this item' class='updatePost btn btn-sm btn-warning' href='#addBookDialog'>Update</span><span data-toggle='modal' data-target='.bs-example-modal-sm2' data-id='"+obj.id+"' title='Add this item' class='deletePost btn btn-sm btn-danger' href='#addBookDialog'>Delete</span>"
    }
    $(".panelContainer").append(
      '<div class="panel panel-default panel-margined">'
        +'<div class="panel-body">'
          +'<p><a href="#">'+ user.username+': </a><br />'+text+'</p>'
        +'</div>'
        +'<div class="panel-footer">'
          <!-- Tags -->
          +tagsAsStr
          <!-- Thumbs up, down -->
          +'<div class="pull-right">'
          //+'<span><div align="right">'
            +'<a href="#" id="'+ obj.id +'" onclick="vote('+currUser+','+ obj.id+ ',true);"><span class="glyphicon glyphicon-thumbs-up" style="color:'+upColor+'"></span></a><span id="t'+obj.id+'"style="color:green;">'+obj.positive_reaction_count+' </span>'
            +'<a href="#" id="-'+ obj.id +'" onclick="vote('+currUser+','+ obj.id+ ',false);"><span class="glyphicon glyphicon-thumbs-down" style="color:'+downColor+'"></span></a><span id="d'+obj.id+'" style="color:red;">'+obj.negative_reaction_count+' </span>'
            +'  Accuracy: '
            +'<span id="a'+obj.id+'">'+accuracy.toFixed(2)+'% </span>'
            // (currUser == user.id) ? + ('<span class="btn btn-sm btn-danger" id="a'+obj.id+'">Delete</span>') : +''
            + updateDeleteSpan
            +'</div><div class="clearfix"></div>'
          +'</div>'
        +'</div>'
      +'</div>'
    );
  });
  var theTags=[];
  $('#tags').selectize({
    maxOptions: 6,
    valueField: 'id',
    labelField: 'description',
    searchField: ['description'],
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
                name : value.label,
                description: (value.label +" "+ value.description)
              });
              console.log("my values are: "+i+" "+value.label+" "+value.description);
          });
          callback(theTags);
      });
    }
  });
  $("#submit").click(function(){
    var post={};
    post.content=$("#content").val();
    post.topic_id=topic.id;

    var resultTagIds = $("#tags").val();
    if(resultTagIds != ""){
      resultTagIds = resultTagIds.split(",");
    }
    else{
      resultTagIds = [];
    }
    var resultTags = [];
    $.each(resultTagIds,function(i,value){
      resultTags.push(get_tags(value,theTags));
    });

    post.tags=resultTags;
    console.log(post);
    $.ajax({
      url: 'postAdd/',
      type: 'POST',
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify(post),
      success: function (data) {
        $("#content").val("");
        $("#tags").val("");
        location.reload();
      },
      error: function (x, y, z) {
          console.log("error");
      }
    });
  });
  $('.js-delete-post').click(function(e) {
    var post_id = $(".js-delete-post").data('postUpdate');
    console.log(post_id)
    $.ajax({
      url: '/cocomapapp/postDelete/'+post_id,
      type: 'DELETE',
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify({"user_id" : currUser}),
      success: function (data) {
        location.reload();
      },
      error: function (x, y, z) {
          console.log("error");
      }
    });
  });

  $('.js-update-post-content').click(function(e) {
    var post_id = $(".js-update-post-content").data('postUpdate');
    var post_content = $(".js-post-content").val()
    var post_tags = []
    topic.posts.forEach(function(data) {
      if (data.id == post_id) {
        post_tags = data.tags
      }
    });
    var data = {
      "topic_id" : topic.id,
      "user_id" : currUser,
      "tags" : post_tags,
      "content" : post_content,
    }
    $.ajax({
      url: '/cocomapapp/postUpdate/'+post_id+'/',
      type: 'PATCH',
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify(data),
      success: function (data) {
        location.reload();
      },
      error: function (x, y, z) {
          console.log("error");
      }
    });
  });
  $(document).on("click", ".updatePost", function () {
    $(".js-update-post-content").data('postUpdate', $(this).data('id'));
  });
  $(document).on("click", ".deletePost", function () {
    $(".js-delete-post").data('postUpdate', $(this).data('id'));
  });
});
