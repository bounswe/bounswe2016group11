$(document).ready(function(){
  topic = JSON.parse(topic);
  hot_topics = JSON.parse(hot_topics);
  posts2 = JSON.parse(posts2);
  console.log(posts2);

  var title = topic[0]["fields"]["name"];
  //var topic_tags = data.tags;
  var posts = posts2;

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
        var username = "sss";
        var text = obj.fields.content;
        //var post_tags = obj.tags;
        var post_tags = ["sda","asdasd","asda"];
        //var accuracy = obj.accuracy;
        var accuracy = [50,50];
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
                +'Accuracy:'
                +'<span class="glyphicon glyphicon-thumbs-up" style="color: blue;"><span style="color:green;">'+ accuracy[0]+'</span></span>'
                +'<span class="glyphicon glyphicon-thumbs-down"><span style="color:red;">'+accuracy[1]+'</span> </span>'
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
