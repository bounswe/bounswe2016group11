$(document).ready(function(){
$.getJSON("/static/recommended.json"
).fail( function() {
  console.log("error in recommendation");
}).done( function(data) {

    recommendationRenderer(data);
});

function recommendationRenderer(result){

    var topics = result[0].topics;
    var posts = result[1].posts;
    //console.log(topics);
    //console.log(posts);
    $.each(topics,function(i,topic){
      //console.log(topic);
      ref = "<a href=\"#\">"+topic.name+"</a>";
      html = $("<li class=\"list-group-item\"></li>").html(ref);

      $("#recommendedTopics").append(html);
    });

    $.each(posts,function(i,value){
      //console.log(value.username);
      ref = "<a href=\"#\">"+value.username+" posted to "+ value.topicName +" <span class=\"sr-only\">(current)</span></a><p>"+value.name+"</p>";
      html = $("<li class=\"list-group-item\"></li>").html(ref);

      $("#recommendedposts").append(html);
    });
}
});
