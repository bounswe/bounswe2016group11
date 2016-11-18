$(document).ready(function(){
  topic = JSON.parse(topic);
  hot_topics = JSON.parse(hot_topics);

  console.log(topic);

  var title = topic.name;
  var topic_tags = topic.tags;
  var posts = topic.posts;
  $("#theTitle").text(title);
  $.each(topic_tags, function(i,val){
    $("#topicTags").append(
      "<a><b>#</b>"+val.name +"</a>"
    );
  });



  //console.log(data);

  $.each(posts, function(i, obj) {
        var text = obj.content;
        //var post_tags = obj.tags;
        var post_tags = obj.tags;
        var user = obj.user;
        //for accuracy first positive then negative
        var accuracy = [obj.positive_reaction_count,obj.negative_reaction_count];
        var tagsAsStr="";
        $.each(post_tags, function(i,val){
            tagsAsStr +="<a><b>#</b>"+val.name +"</a>";
        });
        $(".panelContainer").append(

          '<div class="panel panel-default panel-margined">'
            +'<div class="panel-body">'
              +'<p><a href="#">'+ user.first_name + ' '+ user.last_name +': </a><br />'+text+'</p>'
            +'</div>'
            +'<div class="panel-footer">'
              <!-- Tags -->
              +tagsAsStr
              <!-- Thumbs up, down -->
              +'<div class="pull-right">'
                +'Accuracy:'
                +'<span class="glyphicon glyphicon-thumbs-up" style="color: blue;"><span style="color:green;">'+ accuracy[0]+'</span></span>'
                +'<span class="glyphicon glyphicon-thumbs-down"><span style="color:red;">'+accuracy[1]+'</span> </span>'
              +'</div>'
            +'</div>'
          +'</div>'
        );



      });
});
