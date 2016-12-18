function GetArrow(pk) {
    jQuery.support.cors = true;
    $.ajax({
        url: 'relationRetrieve/'+pk,
        type: 'GET',
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            return data['relates_to'];
        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }
    });
}

$(function(){
    $.ajax({
        url: 'topicList',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var json_array = data;
            console.log(data)
            // create an array with nodes
            var range = json_array[json_array.length-1].hotness-json_array[0].hotness;

            var minHot = json_array[0].hotness;
            var maxHot = json_array[json_array.length-1].hotness;

            var dict1 = [];

            for(var i = 0; i < json_array.length; i++){
              //console.log({id: i+1, label: json_array[i]['name'] })
              //Math.random should be replaced
              //Future FIX
              /*var seed = Math.random();

              console.log(seed);*/

              var topicHot = json_array[i].hotness;
              var seed = (topicHot-minHot)/range;
              console.log("seed: "+seed);

              var red = Math.round(seed*255);
              var green = Math.max(Math.round((1-seed)*50),0);
              var blue = Math.round((1-seed)*255);
              console.log("red: "+ red+"    , blue:"+blue);
              dict1.push({id: json_array[i]['id'] ,value:seed, font:{face:'Luckiest Guy',color:'rgb(255,255,255)'},
              label: json_array[i]['name'],color:'rgb('+red+','+green+','+blue+')'});
            }
            //.split(" ").join("\n")
            var nodes = new vis.DataSet(dict1);

            //////
            var range = json_array[json_array.length-1].hotness-json_array[0].hotness;

            var minHot = json_array[0].hotness;
            //var maxHot = json_array[json_array.length-1].hotness;
            // create an array with edges
            var dict2 = [];
            for(var i = 0; i < json_array.length; i++){
              // this code will be replaced ..............
              var maxHot = json_array[i].hotness;
              console.log("topicc "+json_array[i].name);
              for(var j = 0; j < json_array[i]['relates_to'].length; j++){

                var topicHot = json_array[i].hotness;
                var seed = (topicHot-minHot)/range;

                //console.log(json_array[i]['relates_to']);  //relation
                relatedTopicId = json_array[i]['relates_to'][j]['topic_to'];

                if(relatedTopicId == json_array[i].id)
                  relatedTopicId = json_array[i]['relates_to'][j]['topic_from'];


                  //related topic in hotness ını alıyor(json_arrayinin içinden bu topic için search)
                  for(var k = 0; k < json_array.length; k++){
                    if(relatedTopicId == json_array[k].id){
                      //console.log("related topic   "+json_array[k].name);
                      if(json_array[k] > maxHot){
                        maxHot = json_array[k];
                      }
                    }
                  }

                  seed = (maxHot-minHot)/range;
                  //length of arrow
                  console.log(seed);
                  var arrow_length = Math.round((1-seed)*100+500);
                  console.log(arrow_length);


                  dict2.push({ from: json_array[i]['relates_to'][j]['topic_from'], to: json_array[i]['relates_to'][j]['topic_to'], arrows:'to',label:json_array[i]['relates_to'][j]['label'],length: arrow_length });
                  //dict2.push({from: json_array[i]['relates_to'][j]['topic_from'], to: json_array[i]['relates_to'][j]['topic_to'], arrows:'to',label:json_array[i]['relates_to'][j]['label']  });

              }
            }
            var edges = new vis.DataSet(dict2);


            // create a network
            var container = document.getElementById('globalNetwork');
            var data = {
              nodes: nodes,
              edges: edges
            };
            var options = {
              height: '100%',
              width: '100%',
              nodes:{
                shape:"circle",
                scaling:{
                  label:{min:10,max:60},
                  min:0,
                  max:1
                }
              }
            };
            var network = new vis.Network(container, data, options);

            network.on("click", function (params) {
                params.event = "[original event]";

                var clickedNode = params['nodes'][0];
                if(clickedNode != undefined){
                    var clickedTopic =nodes.get(clickedNode);
                    window.location.href = ("/cocomapapp/infocus/"+clickedTopic["id"]);
                    //when clicked to a topic node, the user is directed
                    //to that topics view page
                }
            });
        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }});
  //var json_string = '[{    "topicId":"1",    "topicName":"Am I Trump?",    "relateTo":[      {"relationId":"14", "topicId":"3", "grade":"10","relationName":"buildTheWall"},      {"relationId":"3", "topicId":"2", "grade":"10","relationName":"is_sarcastic_than"}    ]  },  {    "topicId":"2",    "topicName":"Kitty Videos",    "relateTo":[      {"relationId":"5", "topicId":"3", "grade":"10","relationName":"will_not_exist"}    ]  },  {    "topicId":"3",    "topicName":"In the Future...",    "relateTo":[    ]  }]';

});
