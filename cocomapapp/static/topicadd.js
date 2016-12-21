jQuery.fn.shake = function() {
    this.each(function(i) {
        $(this).css({
            "position" : "relative"
        });
        for (var x = 1; x <= 3; x++) {
            $(this).animate({
                left : -25
            }, 10).animate({
                left : 0
            }, 50).animate({
                left : 25
            }, 10).animate({
                left : 0
            }, 50);
        }
    });
    return this;
}

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

$(document).ready(function(){
  $(".sidebar").remove();
  var theTags=[];
  $("#formsubmit").click( function() {
      // console.log("asfdlkjsafdjkladfjs");
      if($("#name").val()== ""){
        $("#name").shake();
        return;
      }
      jQuery.support.cors = true;
      var resultTagIds = $('#tags').val();
      if(resultTagIds != ""){
        resultTagIds = resultTagIds.split(",");
      }
      else{
        resultTagIds = [];
      }
      var resultTags =[];
      console.log(resultTags);
      console.log(resultTagIds);
      $.each(resultTagIds,function(i,value){
        resultTags.push(get_tags(value,theTags));
      });
      var resultTagIds2 = $('#tags2').val();
      var resultTags2 =[];
      console.log("asdasd");
      console.log($("#postCheckbox").prop("checked"));
      if($("#postCheckbox").prop("checked")){

        if(resultTagIds2 != ""){
          resultTagIds2 = resultTagIds2.split(",");
        }
        else{
          resultTagIds2 = [];
        }

        console.log(resultTags2);
        console.log(resultTagIds2);
        $.each(resultTagIds2,function(i,value){
          resultTags2.push(get_tags(value,theTags));
        });
      }
      console.log(resultTags2);
        var topic = {
            name: $('#name').val(),
            relates_to: [{topic_id : $('#relates_to').val(), rel_name : $('#relationships-name').val()}],
            tags: resultTags,
            postAdd: $("#postCheckbox").prop("checked"),
            post: {post_content: $("#postText").val(), post_tags:resultTags2}
            // relationships_name: $('#relationships-name').val()
        };
        if($('#relates_to2').val()){
            topic.relates_to.push({topic_id : $('#relates_to2').val(), rel_name : $('#relationships-name2').val()});
        }
        if($('#relates_to3').val()){
            topic.relates_to.push({topic_id : $('#relates_to3').val(), rel_name : $('#relationships-name3').val()});
        }

        $.ajax({
            url: 'add',
            type: 'POST',
            data:JSON.stringify(topic),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                window.location.href = "/cocomapapp";
            },
            error: function (x, y, z) {
                alert(x + '\n' + y + '\n' + z);
            }
        });

});


  //disfunctional code
  $("#name1").on("keyup",function(){

    if($("#name").val().length <2 ){

      return;
    }
    $.getJSON("/cocomapapp/wikidataSearch/" +$("#name").val() + "/"
    ).fail( function() {
      console.log("error in wikidata");
    }).done( function(data) {

        $.each(data,function(i,value){
            theTags.push({
              name : (value.label +" "+ value.description)
            });
            console.log("my values are: "+i+" "+value.label+" "+value.description);

        });

      }
    );
  });


  var relations=[];
  topicsList= JSON.parse(topicsList);
  $.each(topicsList,function(i,value){
    relations.push({
      id: value.pk,
      name: value.fields.name
    });
  });

  //relations = relations.join(",");
  $('#relates_to').selectize({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();
        callback(relations);
      }
  });

  $('#relates_to2').selectize({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();
        callback(relations);
      }
  });

  $('#relates_to3').selectize({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      load: function(query, callback) {
        if (!query.length) return callback();
        callback(relations);
      }
  });

  $('#tags').selectize({
      maxOptions: 6,
      valueField: 'id',
      labelField: 'description',
      searchField: ['description'],
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
                  name : value.label,
                  description: (value.label +" "+ value.description)
                });
                console.log("my values are: "+i+" "+value.label+" "+value.description);
            });
            callback(theTags);
          });

        }
  });

  $('#tags2').selectize({
    maxOptions: 6,
    valueField: 'id',
    labelField: 'description',
    searchField: ['description'],
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
                name : value.label,
                description: (value.label +" "+ value.description)
              });
                console.log("my values are: "+i+" "+value.label+" "+value.description);
            });
            callback(theTags);
        });

      }
  });

  $('#cancel_bt').click(function(){
  		parent.history.back();
  		return false;
  });

});

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
