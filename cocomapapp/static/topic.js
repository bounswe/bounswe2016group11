$(document).ready(function(){

var data;

  $.getJSON("/static/topic.json"
).done( function(post_page) {

      data = post_page;

      $("#theTitle").text(data.title);
      $.each(data.tags, function(i,val){
        $("#topicTags").append(
          "<a><b>#</b>"+val +"</a>"
        );
      });

      var posts = data.posts;

      $.each(data, function(i, obj) {
            //use obj.id and obj.name here, for example:

});

    }
  );



});
