
$(function(){
   /* Get the topics to show in the global page. the success function
   parses the topics into the vis.js format, when the relevant topics are successfully
   retrieved from the server.
   */
    $.ajax({
        url: 'getHotTopics/-1',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var json_array = data;
            console.log(data)
            // create an array with nodes


            var maxHot = json_array[0].hotness;// the first element is the topic with maximum hotness
            var minHot = json_array[json_array.length-1].hotness;//the last element is the least hot topic
            var range = maxHot- minHot;
            var dict1 = [];

            /*
            The for loop to parse the topic nodes in vis.js
            */
            for(var i = 0; i < json_array.length; i++){
              var topicHot = json_array[i].hotness;//scale the hotness
              var seed = (topicHot-minHot)/range;//find a seed using the hotness


              //generate colors using the seed
              var red = Math.round(seed*255);
              var green = Math.max(Math.round((1-seed)*50),0);
              var blue = Math.round((1-seed)*255);

              //generate vis.js dictionary
              dict1.push({id: json_array[i]['id'] ,value:seed, font:{face:'Luckiest Guy',color:'rgb(255,255,255)'},
              label: json_array[i]['name'].split(" ").join("\n"),color:'rgb('+red+','+green+','+blue+')'});
            }
            console.log(dict1);
            var nodes = new vis.DataSet(dict1);

            // create an array with edges
            var dict2 = [];
            for(var i = 0; i < json_array.length; i++){
              // this code will be replaced ..............

              for(var j = 0; j < json_array[i]['relates_to'].length; j++){

                var topicHot = json_array[i].hotness;

                var relatedTopicId = json_array[i]['relates_to'][j]['topic_to'];

                  //related topic in hotness ını alıyor(json_arrayinin içinden bu topic için search)
                  for(var k = 0; k < json_array.length; k++){
                    if(relatedTopicId == json_array[k].id){
                      //console.log("related topic   "+json_array[k].name);
                      if(json_array[k].hotness > topicHot){
                        topicHot = json_array[k].hotness;
                      }
                      break;
                    }
                  }

                  var seed = (topicHot-minHot)/range;
                  //length of arrow
                  var arrow_length = Math.round((1-seed)*300+200);
                  console.log(seed);
                  //add edges to the dictionary
                  dict2.push({ from: json_array[i].id, to: json_array[i]['relates_to'][j]['topic_to'], arrows:'to',label:json_array[i]['relates_to'][j]['label'],length: arrow_length ,value:seed});
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
                  label:{min:15,max:50},//maximum
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
