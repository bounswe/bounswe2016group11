/*
    This function gets the cookie value
*/
function getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = jQuery.trim(cookies[i]);
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}
var csrftoken = getCookie('csrftoken');
function csrfSafeMethod(method) {
    // these HTTP methods do not require CSRF protection
    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
}
$.ajaxSetup({
    beforeSend: function(xhr, settings) {
        if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
            xhr.setRequestHeader("X-CSRFToken", csrftoken);
        }
    }
});

var searchItem = window.location.pathname;
console.log(searchItem);

var theRegex1 = /\+\+([a-zA-Z0-9_.%]*)/igm;
var theRegex2 = /Q([0-9])*/igm;

var searchText = theRegex1.exec(searchItem)[0];
var searchId = theRegex2.exec(searchItem)[0];

var theRegex3 = /([a-zA-Z0-9_.%]+)/igm;
searchText = theRegex3.exec(searchText)[0];
fixedText=decodeURIComponent(searchText);

/*
  gets the result coming from Wikidata in json format and sends to searchRenderer function.
*/
$.getJSON("/cocomapapp/wikidataQuery/" +searchId + "/"
).fail( function() {
  console.log("error in wikidata");
}).done( function(data) {
    var wikiItems=[];
    wikiItems.push(searchId);
    $.each(data,function(i,value){
      var toRegex = value.valUrl.value;
      var theRegex = /.*\/([a-z]+?\d+?)$/im ;
      var fetchedWikiId = theRegex.exec(toRegex)[1];
      wikiItems.push(fetchedWikiId);
    });
    console.log(wikiItems);
    console.log(fixedText);
    console.log(JSON.stringify({"tags":wikiItems, "query":fixedText}));
    $.ajax({
        async: false,
        url: '/cocomapapp/searchByTags',
        type: 'POST',
        data:JSON.stringify({"tags":wikiItems, "query":fixedText}),
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            searchRenderer(data);
        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }
    });
  }
);

/*
    This function prepares the search page view fed with result variable.
*/
function searchRenderer(result){

    var topics = result.topics;
    var posts = result.posts;
    $.each(topics,function(i,topic){
      ref = "<a href=\"/cocomapapp/topics/"+topic.id+"\"></a>";
      text = $(ref).text(topic.name);
      html = $("<li class=\"list-group-item\"></li>").html(text);
      $("#results").append(html);
    });

    $.each(posts,function(i,value){
      var ref = "<a href=\"/cocomapapp/topics/"+value.topic.id+"\"></a>";  //link to belonging topic (not linked to post directly)
      var text = $(ref).html(value.content); //value.content de olabilir. post a link
      ref1 = "<a href=\"/cocomapapp/topics/"+value.topic.id+"\"></a>"; //again, link to belonging topic
      var text2 = $(ref1).html(value.topic.name); //post un ait olduğu topic e link
      var text3 = text.add("<br> belonging to: </br>");

      var html2 = $("<li class=\"list-group-item\"></li>").html(text3.add(text2));

      $("#results").append(html2);
    });
}
