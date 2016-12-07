$(document).ready(function(){
  $.getJSON("/cocomapapp/recommendedPosts"
  ).fail( function() {
    console.log("error in recommendation");
  }).done( function(data) {
      recommendationRenderer(data);
  });
});
function get_recommended_topics(){
  var topics;
  $.ajax({
    url: "/cocomapapp/recommendedTopics",
    async: false,
    dataType:"json"
  }).done(function(data){
    topics = data;
  }).fail(function(){
    return "Server Connection Failed";
  });
  return topics;
}
function get_recommended_posts(){
  var posts;
  $.ajax({
    url: "/cocomapapp/recommendedPosts",
    async: false,
    dataType:"json"
  }).done(function(data){
    posts = data;
  }).fail(function(){
    return "Server Connection Failed";
  });
  return posts;
}

function recommendationRenderer(result){

    var topics = get_recommended_topics();
    var posts = get_recommended_posts();
    //console.log(topics);
    //console.log(posts);
    $.each(topics,function(i,topic){
      //console.log(topic);
      ref = "<a href='topics/"+topic.id +"'>"+topic.name+"</a>";
      html = $("<li class=\"list-group-item\"></li>").html(ref);

      $("#recommendedTopics").append(html);
    });

    $.each(posts,function(i,value){
      //console.log(value.username);
      ref = "<a href='topics/"+ value.topic.id+ "/'>"+value.user.username+" posted to "+ value.topic.name +" <span class=\"sr-only\">(current)</span></a><p>"+value.content+"</p>";
      html = $("<li class=\"list-group-item\"></li>").html(ref);

      $("#recommendedposts").append(html);
    });
}
