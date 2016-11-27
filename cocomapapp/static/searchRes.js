$(document).ready(function(){
//  $("#myButton").click(function(){
       $.getJSON("/static/searchRes.json", function(result){
          //var items = [];
           $.each(result, function(i, obj){
              $.each(obj, function(j, field){
                $.each(field, function(k, value){
                                          //console.log(j);
                      //items.push({j : value.name});
                    if(j == "topics"){
                      ref = "<a href=\"/cocomapapp/topics/"+value.id+"\"></a>";
                      text = $(ref).text(value.name);
                      html = $("<li class=\"list-group-item\"></li>").html(text);

                      //html2 = $("<li class=\"list-group-item\"></li>").text(html);
                      $("#results").append(html);
                    }else if(j == "posts"){
                      var ref = "<a href=\"/cocomapapp/topics/"+value.topicId+"\"></a>";  //link to belonging topic (not linked to post directly)
                      var text = $(ref).html(value.name); //value.content de olabilir. post a link
                      ref1 = "<a href=\"/cocomapapp/topics/"+value.topicId+"\"></a>"; //again, link to belonging topic
                      var text2 = $(ref1).html(value.topicName); //post un ait olduğu topic e link
                      var text3 = text.add("<br> belonging to: </br>");

                      var html2 = $("<li class=\"list-group-item\"></li>").html(text3.add(text2));

                      $("#results").append(html2);
                    }
                });
              });
           });

       });
//    });
});
