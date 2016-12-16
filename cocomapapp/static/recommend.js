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
    {% if user.is_authenticated %}

      url: "/cocomapapp/getRecommendedTopics/5",
    {% else %}
      url: "/cocomapapp/recommendedTopics"
    {% endif %}

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
      html = $("<li class=\"list-group-item list-group-item-success\"></li>").html(ref);

      $("#recommendedTopics").append(html);
    });

    $.each(posts,function(i,value){
      //console.log(value.username);
      ref = "<span><p style='color:#6E6E6E; display:inline; font-weight:bold; font-family: \"Russo One\", sans-serif;' >"+value.user.username +"</p> posted to "+"<a style='color:#29088A; display:inline; font-weight:bold; font-family: \"Sriracha\", cursive;' href='topics/"+ value.topic.id+ "/'>"+value.topic.name +" <span class=\"sr-only\">(current)</span></a></span></br></br><p align='center' style='color:#000000; font-weight:bold; font-family:\"Lobster\", cursive;'>\""+value.content+"\"</p>";
      html = $("<li style='padding-top:13px;' class=\"list-group-item list-group-item-info\"></li>").html(ref);

      $("#recommendedposts").append(html);
    });
}
