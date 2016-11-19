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
                      text = $("<a href=\"\"></a>").text(value.name);
                      html = $("<li class=\"list-group-item\"></li>").html(text);

                      //html2 = $("<li class=\"list-group-item\"></li>").text(html);
                      $("#results").append(html);
                    }else if(j == "posts"){
                      var text = $("<a href=\"\"></a>").html(value.name); //value.content de olabilir. post a link
                      var text2 = $("<a href=\"\"></a>").html(value.topicName); //post un ait olduğu topic e link
                      var text3 = text.add("<br> @ </br>");

                      var html2 = $("<li class=\"list-group-item\"></li>").html(text3.add(text2));

                      $("#results").append(html2);
                    }
                });
              });
           });

       });
//    });
});
