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
            var dict1 = [];

            for(var i = 0; i < json_array.length; i++){
              //console.log({id: i+1, label: json_array[i]['name'] })
              dict1.push({id: json_array[i]['pk'], label: json_array[i]['name'] });
            }
            var nodes = new vis.DataSet(dict1);


            // create an array with edges
            var dict2 = [];
            for(var i = 0; i < json_array.length; i++){
              // this code will be replaced ..............
              for(var j = 0; j < json_array[i]['relates_to'].length; j++){
                  //console.log(json_array[i]['relates_to'])
                $.ajax({
                    url: 'relationRetrieve/'+json_array[i]['relates_to'][j],
                    type: 'GET',
                    dataType: 'json',
                    contentType: "application/json;charset=utf-8",
                    success: function (data) {
                        //console.log(i, data['topic_from'], data['topic_to']);
                        //console.log(json_array[i]['relates_to'][j])
                        dict2.push({from: data['topic_from'], to: data['topic_to'], arrows:'to'  });

                    },
                    error: function (x, y, z) {
                        alert(x + '\n' + y + '\n' + z);
                    },
                    async: false
                });
                //console.log({from: json_array[i]['relates_to'][j], to: i, arrows:'from'  })
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
        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }});
  //var json_string = '[{    "topicId":"1",    "topicName":"Am I Trump?",    "relateTo":[      {"relationId":"14", "topicId":"3", "grade":"10","relationName":"buildTheWall"},      {"relationId":"3", "topicId":"2", "grade":"10","relationName":"is_sarcastic_than"}    ]  },  {    "topicId":"2",    "topicName":"Kitty Videos",    "relateTo":[      {"relationId":"5", "topicId":"3", "grade":"10","relationName":"will_not_exist"}    ]  },  {    "topicId":"3",    "topicName":"In the Future...",    "relateTo":[    ]  }]';

});
