function get_tags(wikiId,theTags){
  var waitNum;
  var resultTag;
  var hidden_tags=[];
  var props=["P31","P610","P37","P36", "P30","P190","P17","P190",
  "P27", "P425","P279"];
  $.ajax({
    url : "/cocomapapp/wikidataQuery/" +wikiId + "/" ,
    async:false,
    dataType:"json"
  }).fail( function() {
    console.log("error in wikidata");
  }).done( function(data) {
    hidden_tags=get_hidden_tags(props,data);
    var occupation = get_hidden_tags(["P106"],data);
    waitNum = occupation.length;
    $.each(occupation,function(i,value){
      $.ajax({
        url : "/cocomapapp/wikidataQuery/" +value + "/" ,
        async:false,
        dataType:"json"
      }).fail( function() {
        console.log("error in wikidata");
      }).done( function(data2) {
        hidden_tags = hidden_tags.concat(get_hidden_tags(["P425"],data2) );
      });
    });
    var result = $.grep(theTags, function(e){ return e.id == wikiId; });

    resultTag = {
      "id" : wikiId,
      "label" : result[0].name ,
      "hidden_tags": hidden_tags
    };
  });
  return resultTag;
}

function get_hidden_tags(props,the_json){
  var hidden_tags=[];
  var sem_att_list=props;
  $.each(the_json,function(i,value){
    var temp_url = value.propUrl.value;
    var cond=false;
    for(var j=0; j<sem_att_list.length;j++){
      if(temp_url.endsWith(sem_att_list[j])){
        cond=true;
        //console.log(value.propLabel.value);
        break;
      }
    }
    if(cond){
      var toRegex = value.valUrl.value;
      var theRegex = /.*\/([a-z]+?\d+?)$/im ;
      var fetchedWikiId = theRegex.exec(toRegex)[1];
      hidden_tags.push(fetchedWikiId);
    }
  });
  //console.log(hidden_tags);
  return hidden_tags;
}

function upVote(id){  //For upvoting

  //console.log("upVote deyim");
  $.ajax({
    url: '/cocomapapp/postUpvote/'+id+'/',
    type: 'PUT',
    success: function (data) {
      //console.log('success: ', data);

      $('#t'+id).hide();
      $('#'+id).after('<span id="t'+id+'" style="color:green;">'+ data.positive_reaction_count +'</span>');
      var accuracy = (data.positive_reaction_count/(data.positive_reaction_count+data.negative_reaction_count))*100;

      $('#a'+id).replaceWith('<span id="a'+id+'">'+accuracy.toFixed(2)+'% </span>');


    },
    error: function (x, y, z) {
        console.log("error");
    }
  });
}
function downVote(id){   //For downvoting
  //console.log("downVote deyim");
  $.ajax({
    url: '/cocomapapp/postDownvote/'+id+'/',
    type: 'PUT',
    //data:JSON.stringify(post),
    contentType: "application/json;charset=utf-8",
    success: function (data) {
      //console.log('success: ', data);
      $('#d'+id).hide();
      $('#-'+id).after('<span id="d'+id+'" style="color:red;">'+ data.negative_reaction_count +'</span>');
      var accuracy = (data.positive_reaction_count/(data.positive_reaction_count+data.negative_reaction_count))*100;

      $('#a'+id).replaceWith('<span id="a'+id+'">'+accuracy.toFixed(2)+'% </span>');
    },
    error: function (x, y, z) {
        console.log("error");
    }
  });
}

$(document).ready(function(){
  topic = JSON.parse(topic);
  hot_topics = JSON.parse(hot_topics);
  console.log(topic);
  var title = topic.name;
  var topic_tags = topic.tags;
  var posts = topic.posts;

  $("#theTitle").text(title);
  var option_labels=["label-primary","label-success","label-info","label-warning","label-danger"];
  $.each(topic_tags, function(i,val){
    $("#topicTags").append(
      "<span style='display: inline-block;' class='label "+option_labels[i%option_labels.length]+"'>" +val.name +"</span>"
    );
  });
  $.each(posts, function(i, obj) {
        console.log(obj);
        var text = obj.content;
        //var post_tags = obj.tags;
        var post_tags = obj.tags;
        var user = obj.user;
        //for accuracy first positive then negative

        var accuracy = (obj.positive_reaction_count+obj.negative_reaction_count)>0?(obj.positive_reaction_count/(obj.positive_reaction_count+obj.negative_reaction_count))*100:0;
        var tagsAsStr="";
        $.each(post_tags, function(i,val){
            tagsAsStr +="<span style='display: inline-block;' class='label "+option_labels[i%option_labels.length] +"'>" +val.name +"</span>";
        });
        $(".panelContainer").append(

          '<div class="panel panel-default panel-margined">'
            +'<div class="panel-body">'
              +'<p><a href="#">'+ user.username+': </a><br />'+text+'</p>'
            +'</div>'
            +'<div class="panel-footer">'
              <!-- Tags -->
              +tagsAsStr
              <!-- Thumbs up, down -->
              +'<div class="pull-right">'
              //+'<span><div align="right">'
                +'<a href="#" id="'+ obj.id +'" onclick="upVote('+ obj.id+');"><span class="glyphicon glyphicon-thumbs-up" style="color: blue;"></span></a><span id="t'+obj.id+'"style="color:green;">'+obj.positive_reaction_count+' </span>'
                +'<a href="#" id="-'+ obj.id +'" onclick="downVote('+ obj.id+');"><span class="glyphicon glyphicon-thumbs-down" style="color: red"></span></a><span id="d'+obj.id+'" style="color:red;">'+obj.negative_reaction_count+' </span>'
                +'  Accuracy: '
                +'<span id="a'+obj.id+'">'+accuracy.toFixed(2)+'% </span>'
              //+'</div></span>'
                +'</div><div class="clearfix"></div>'
              +'</div>'
            +'</div>'
          +'</div>'

        );

      });
      var theTags=[];
      $('#tags').selectize({
          maxOptions: 6,
          valueField: 'id',
          labelField: 'name',
          searchField: ['name'],
          plugins: ['remove_button'],
          options: [],
          create: false,
          load: function(query, callback) {
              if(query.length <2 ){
                return [];
              }
              $.getJSON("/cocomapapp/wikidataSearch/" +query+ "/"
              ).fail( function() {
                console.log("error in wikidata");
              }).done( function(data) {
                  $.each(data,function(i,value){
                      theTags.push({
                        id : value.id,
                        name : (value.label +" "+ value.description)
                      });
                      console.log("my values are: "+i+" "+value.label+" "+value.description);
                  });
                  callback(theTags);
              });

            }
      });
});
