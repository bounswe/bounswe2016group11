$(document).ready(function(){
    recommendationRenderer();
});
function get_recommended_topics(){
  var topics;
  $.ajax({
    url: recommendedTopicURL,
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
    url: recommendedPostURL,
    async: false,
    dataType:"json"
  }).done(function(data){
    posts = data;
  }).fail(function(){
    return "Server Connection Failed";
  });
  return posts;
}

function recommendationRenderer(){

    var topics = get_recommended_topics();
    var posts = get_recommended_posts();


    //console.log(topics);
    //console.log(posts);
    $.each(topics,function(i,topic){
      //console.log(topic);

      ref = "<div align='center' ><a  style='color:#000000; font-size:140%; font-weight:bold; font-family: \"Sriracha\", cursive;' href='/cocomapapp/topics/"+topic.id +"'>"+topic.name+"</a></div>";
      html = $("<li  class=\"list-group-item\"></li>").html(ref);

      $("#recommendedTopics").append(html);
    });

    $.each(posts,function(i,value){
      
      //console.log(value.username);
      ref = "<span><p style='color:#6E6E6E; display:inline; font-weight:bold; font-family: \"Russo One\", sans-serif;' >"+value.user.username +"</p> posted to "+"<a style='color:#29088A; display:inline; font-weight:bold; font-family: \"Sriracha\", cursive;' href='/cocomapapp/topics/"+ value.topic.id+ "/'>"+value.topic.name +" <span class=\"sr-only\">(current)</span></a></span></br></br><p align='center' style='color:#000000; font-weight:bold; font-family:\"Lobster\", cursive;'>\""+value.content+"\"</p>";
      html = $("<li style='padding-top:13px;' class=\"list-group-item\"></li>").html(ref);

      $("#recommendedposts").append(html);
    });
}
