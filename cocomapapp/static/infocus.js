/*
  the data of the relation with id=pk is loaded.
*/
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

   /*
      the list of relationships to in focus topic from other topics is loaded.
      The width and length of the arrows is arranged according to the hotness values of the topics
   */
    $.ajax({
        url: '/cocomapapp/relationList?topic_id='+searchId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var json_array = data;
            console.log(data)
            // create an array with nodes
            var dict1 = [];

            for(var i = 0; i < json_array.length; i++){

              if(json_array[i]['topic_from']['id']!=searchId || i==0)
              {
                  console.log("from: "+json_array[i]['topic_from']['name']);

                  var seed =0.3;
                  if(json_array[i]['topic_from']['id']==searchId) seed=0.99;
                  console.log(seed);
                  var red = Math.round(seed*255);
                  var green = Math.max(Math.round((1-seed)*50),0);
                  var blue = Math.round((1-seed)*255);
                  console.log("red: "+ red+"    , blue:"+blue);
                  dict1.push({id: json_array[i]['topic_from']['id'], size:(20*seed)+20 , font:{size:(25*seed)+10 ,face:'Luckiest Guy',color:'rgb(255,255,255)'},
                  label: json_array[i]['topic_from']['name'].split(" ").join("\n"),color:'rgb('+red+','+green+','+blue+')' , shape:'circle' });
              }

              if(json_array[i]['topic_to']['id']!=searchId || i==0)
              {
                  console.log("from: "+json_array[i]['topic_to']['name']);

                  var seed = 0.3;
                  if(json_array[i]['topic_to']['id']==searchId) seed=0.99;
                  console.log(seed);
                  var red = Math.round(seed*255);
                  var green = Math.max(Math.round((1-seed)*50),0);
                  var blue = Math.round((1-seed)*255);
                  console.log("red: "+ red+"    , blue:"+blue);
                  dict1.push({id: json_array[i]['topic_to']['id'], size:(20*seed)+20 , font:{size:(25*seed)+10 ,face:'Luckiest Guy',color:'rgb(255,255,255)'},
                  label: json_array[i]['topic_to']['name'].split(" ").join("\n"),color:'rgb('+red+','+green+','+blue+')' , shape:'circle' });
              }

            }
            if(json_array.length==0){

                $.ajax({
                    url:'/cocomapapp/topicRetrieve/'+searchId,
                    type: 'GET',
                    dataType: 'json',
                    success: function (data2) {
                        var json_array2 = data2;
                        console.log(data2);
                        var seed = Math.random();
                        seed=0.99;
                        var red = Math.round(seed*255);
                        var green = Math.max(Math.round((1-seed)*50),0);
                        var blue = Math.round((1-seed)*255);
                        console.log("red: "+ red+"    , blue:"+blue);
                        dict1.push({id: searchId, size:(20*seed)+20 , font:{size:(25*seed)+10 ,face:'Luckiest Guy',color:'rgb(255,255,255)'},
                        label: json_array2['name'].split(" ").join("\n"),color:'rgb('+red+','+green+','+blue+')' , shape:'circle' });
                        console.log("searchName:"+dict1[0]['label']);
                        var nodes = new vis.DataSet(dict1);
                        var edges = new vis.DataSet(dict2);


                        // create a network
                        var container = document.getElementById('globalNetwork');
                        var data = {
                          nodes: nodes,
                          edges: edges
                        };
                        var options = {autoResize: true,
                          height: '100%',
                          width: '100%'
                        };
                        var network = new vis.Network(container, data, options);

                        network.on("click", function (params) {
                            params.event = "[original event]";

                            var clickedNode = params['nodes'][0];
                            if(clickedNode != undefined){
                                var clickedTopic =nodes.get(clickedNode);
                                window.location.href = ("/cocomapapp/topics/"+clickedTopic["id"]);
                                //when clicked to a topic node, the user is directed
                                //to that topics view page
                            }
                        });

                    }
                });
            }
            else{
                console.log("print:"+dict1[0]['label']);
                var nodes = new vis.DataSet(dict1);
                // create an array with edges
                var dict2 = [];
                seed = 0.99;
                for(var i = 0; i < json_array.length; i++){
                      //length of arrow

                      var arrow_length = Math.round((1-seed)*100+450);

                      dict2.push({ from: json_array[i]['topic_from']['id'], to: json_array[i]['topic_to']['id'], arrows:'to',label:json_array[i]['label'],length: arrow_length });
                }
                var edges = new vis.DataSet(dict2);


                // create a network
                var container = document.getElementById('globalNetwork');
                var data = {
                  nodes: nodes,
                  edges: edges
                };
                var options = {autoResize: true,
                  height: '100%',
                  width: '100%'
                };
                var network = new vis.Network(container, data, options);

                network.on("click", function (params) {
                    params.event = "[original event]";

                    var clickedNode = params['nodes'][0];
                    if(clickedNode != undefined){
                        var clickedTopic =nodes.get(clickedNode);
                        if(clickedTopic["id"]==searchId)
                            window.location.href = ("/cocomapapp/topics/"+clickedTopic["id"]);
                        else
                            window.location.href = ("/cocomapapp/infocus/"+clickedTopic["id"]);
                        //when clicked to a topic node, the user is directed
                        //to that topics view page
                    }
                });
            }



        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }});
  //var json_string = '[{    "topicId":"1",    "topicName":"Am I Trump?",    "relateTo":[      {"relationId":"14", "topicId":"3", "grade":"10","relationName":"buildTheWall"},      {"relationId":"3", "topicId":"2", "grade":"10","relationName":"is_sarcastic_than"}    ]  },  {    "topicId":"2",    "topicName":"Kitty Videos",    "relateTo":[      {"relationId":"5", "topicId":"3", "grade":"10","relationName":"will_not_exist"}    ]  },  {    "topicId":"3",    "topicName":"In the Future...",    "relateTo":[    ]  }]';

});
