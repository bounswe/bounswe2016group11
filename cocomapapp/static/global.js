$(function(){
  console.log(context);
  //var json_string = '[{    "topicId":"1",    "topicName":"Am I Trump?",    "relateTo":[      {"relationId":"14", "topicId":"3", "grade":"10","relationName":"buildTheWall"},      {"relationId":"3", "topicId":"2", "grade":"10","relationName":"is_sarcastic_than"}    ]  },  {    "topicId":"2",    "topicName":"Kitty Videos",    "relateTo":[      {"relationId":"5", "topicId":"3", "grade":"10","relationName":"will_not_exist"}    ]  },  {    "topicId":"3",    "topicName":"In the Future...",    "relateTo":[    ]  }]';
  var json_string = context;
  var json_array = JSON.parse(json_string);
  // create an array with nodes
  var dict1 = [];

  for(var i = 0; i < json_array.length; i++){
    dict1.push({id: json_array[i]['pk'], label: json_array[i]['fields']['name'] });
  }
  var nodes = new vis.DataSet(dict1);


  // create an array with edges
  var dict2 = [];
  for(var i = 0; i < json_array.length; i++){
    // this code will be replaced ..............
    for(var j = 0; j < json_array[i]['fields']['relates_to'].length; j++){
      dict2.push({from: json_array[i]['fields']['relates_to'][j], to: json_array[i]['pk'], arrows:'from'  });
    }
  }
  var edges = new vis.DataSet(dict2);


  // create a network
  var container = document.getElementById('globalNetwork');
  var data = {
    nodes: nodes,
    edges: edges
  };
  var options = {};
  var network = new vis.Network(container, data, options);

  network.on("click", function (params) {
      params.event = "[original event]";

      var clickedNode = params['nodes'][0];
      if(clickedNode != undefined){
          var clickedTopic =nodes.get(clickedNode);
          window.location.href = ("topics/"+clickedTopic["id"]);
          //when clicked to a topic node, the user is directed
          //to that topics view page
      }
  });
});
