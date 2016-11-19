$(document).ready(function(){
//  $("#myButton").click(function(){
       $.getJSON("/static/searchRes.json", function(result){
          var items = [];
           $.each(result, function(i, obj){
              $.each(obj, function(j, field){
                $.each(field, function(k, value){
                      items.push(value.name);
                });
              });
           });
           var html =  "";
           $.each(items, function( index, value ) {
             text = $("<a href=\"\"></a>").text(value);
             html = $("<li class=\"list-group-item\"></li>").html(text);

             //html2 = $("<li class=\"list-group-item\"></li>").text(html);
             $("#results").append(html);
           });
       });
//    });
});
