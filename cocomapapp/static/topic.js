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

            $(".panelContainer").append(

              '<div class="panel panel-default panel-margined">'
                +'<div class="panel-body">'
                  +'<p><a href="#">'+ username +'</a>'+text+'</p>'
                +'</div>'
                +'<div class="panel-footer">'
                  <!-- Tags -->
                  +'<a href="#">#trump</a> <a href="#">#clinton</a> <a href="#">#president</a> <a href="#">#politics</a>'
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



});
