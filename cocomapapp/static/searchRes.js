var searchId = window.location.pathname;
console.log(searchId);
var theRegex = /.*\/(.+?)\/$/im ;
var searchId = theRegex.exec(searchId)[1];
$.getJSON("/cocomapapp/wikidataQuery/" +searchId + "/"
).fail( function() {
  console.log("error in wikidata");
}).done( function(data) {
    var wikiItems=[];
    if(data.length>0){
      wikiItems = [];
    }

    $.each(data,function(i,value){
      var toRegex = value.valUrl.value;
      var theRegex = /.*\/([a-z]+?\d+?)$/im ;
      var fetchedWikiId = theRegex.exec(toRegex)[1];
      wikiItems.push(fetchedWikiId);
    });

    $.ajax({
        url: '/cocomapapp/searchByTags',
        type: 'POST',
        data:JSON.stringify({tags:wikiItems}),
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            searchRenderer(data);
        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }
    });
  }
);

function searchRenderer(result){
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
}
