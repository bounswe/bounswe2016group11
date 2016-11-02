$(document).ready(function(){

var data;

  $.getJSON("/static/topic.json"
).done( function(post) {

      data = post;

      var posts = data.posts;

      $.each(data, function(i, obj) {
            //use obj.id and obj.name here, for example:
            
});

    }
  );



}
