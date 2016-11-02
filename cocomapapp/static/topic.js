$(document).ready(function(){



  $.getJSON("/static/topic.json"
).done( function(data) {
      $("#theTitle").text(data.title);

      $.each(data.tags, function(i,val){
        $("#topicTags").append(
          "<a><b>#</b>"+val +"</a>"
        );
      });

      var title = data.title;
      var topic_tags = data.tags;
      var posts = data.posts;

      //console.log(data);

      $.each(posts, function(i, obj) {
            //use obj.id and obj.name here, for example:
            var username = obj.username;
            var text = obj.text;
            var post_tags = obj.tags;
            var accuracy = obj.accuracy;
            var tagsAsStr="";
            $.each(post_tags, function(i,val){
                tagsAsStr +="<a><b>#</b>"+val +"</a>";
            });
            $(".panelContainer").append(

              '<div class="panel panel-default panel-margined">'
                +'<div class="panel-body">'
                  +'<p><a href="#">'+ username +'</a>'+text+'</p>'
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

    }
  );

  $("#post_button").click(function(){

    var str = $("#content").text().trim();
    if(str == ""){
      
    }else{

      console.log(str);
    }


  });

});
