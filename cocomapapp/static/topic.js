function function1(id){  //For upvoting

  //console.log("function1 deyim");
  $.ajax({
    url: '/cocomapapp/postUpvote/'+id+'/',
    type: 'PUT',
    success: function (data) {
      //console.log('success: ', data);

      $('#t'+id).hide();
      $('#'+id).after('<span id="t'+id+'" style="color:green;">'+ data.positive_reaction_count +'</span>');
      var accuracy = (data.positive_reaction_count/(data.positive_reaction_count+data.negative_reaction_count))*100;

      $('#a'+id).replaceWith('<span id="a'+id+'">'+accuracy.toFixed(2)+'% </span>');


    },
    error: function (x, y, z) {
        console.log("error");
    }
  });
}
function function2(id){   //For downvoting
  //console.log("function2 deyim");
  $.ajax({
    url: '/cocomapapp/postDownvote/'+id+'/',
    type: 'PUT',
    //data:JSON.stringify(post),
    contentType: "application/json;charset=utf-8",
    success: function (data) {
      //console.log('success: ', data);
      $('#d'+id).hide();
      $('#-'+id).after('<span id="d'+id+'" style="color:red;">'+ data.negative_reaction_count +'</span>');
      var accuracy = (data.positive_reaction_count/(data.positive_reaction_count+data.negative_reaction_count))*100;

      $('#a'+id).replaceWith('<span id="a'+id+'">'+accuracy.toFixed(2)+'% </span>');
    },
    error: function (x, y, z) {
        console.log("error");
    }
  });
}

$(document).ready(function(){
  topic = JSON.parse(topic);
  hot_topics = JSON.parse(hot_topics);
  posts2 = JSON.parse(posts2);
  user = JSON.parse(user);
  //console.log(user);
  //console.log(topic);

  var title = topic[0]["fields"]["name"];
  //var topic_tags = data.tags;
  var posts = posts2;
  //console.log(posts);
  $("#theTitle").text(title);
  /*
  $.each(data.tags, function(i,val){
    $("#topicTags").append(
      "<a><b>#</b>"+val +"</a>"
    );
  });
  */


  //console.log(data);

  $.each(posts2, function(i, obj) {
        //use obj.id and obj.name here, for example:
        //var username = obj.username;
        //var username = "{{ request.session.username }}";
        // var username = "username";
        //console.log(obj.pk);
        var text = obj.fields.content;
        //var post_tags = obj.tags;
        var post_tags = ["tag1","tag2","tag3"];
        //var accuracy = obj.accuracy;
        var accuracy = [50,50];
        var percent = 0;
        var tagsAsStr="";
        $.each(post_tags, function(i,val){
            tagsAsStr +="<a><b>#</b>"+val +"</a>";
        });

        $(".panelContainer").append(

          '<div class="panel panel-default panel-margined">'
            +'<div class="panel-body">'
              +'<p><a href="#">'+ username +'</a><br />'+text+'</p>'
            +'</div>'
            +'<div class="panel-footer">'
              <!-- Tags -->
              +tagsAsStr
              <!-- Thumbs up, down -->
              +'<div class="pull-right">'
                +'<a href="#" id="'+ obj.pk +'" onclick="function1(id);"><span class="glyphicon glyphicon-thumbs-up" style="color: blue;"></span></a><span id="t'+obj.pk+'"style="color:green;">'+0+' </span>'
                +'<a href="#" id="-'+ obj.pk +'" onclick="function2(-1*id);"><span class="glyphicon glyphicon-thumbs-down" style="color: red"></span></a><span id="d'+obj.pk+'" style="color:red;">'+0+' </span>'
                +'  Accuracy: '
                +'<span id="a'+obj.pk+'">'+0.0+'% </span>'
              +'</div>'
            +'</div>'
          +'</div>'

        );

      });

  $("#post_button").click(function(){

    var str = $("#content").text().trim();
    if(str == ""){

    }else{

      console.log(str);
    }


  });

});
