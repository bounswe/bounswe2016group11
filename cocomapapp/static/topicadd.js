$(document).ready(function(){
  /*
  console.log("here");
    $('input#tags').typeahead({
        name: 'typeahead',
        remote:'topic/add',
        limit : 10
    });
    */
    console.log("here");
  $("#tags").on("keydown",function(){

    console.log($("#tags").val());
    $.get("add",{"search": $("#tags").val()}).done(function(data){
      console.log("data come: "+ data);
    });
  });
});
