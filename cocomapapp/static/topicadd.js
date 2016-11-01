$(document).ready(function(){



  var citynames = new Bloodhound({

    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    prefetch: {
      url: '/static/tags.json',
      filter: function(list) {
        return $.map(list, function(cityname) {
          return { name: cityname }; });
      }
    }
  });
  citynames.initialize();

  $('#tags').tagsinput({
    typeaheadjs: {
      name: 'citynames',
      displayKey: 'name',
      valueKey: 'name',
      source: citynames.ttAdapter()
    }
  });

  //
  $("#publish").click(function(){
      topic_name = $("#topicName").val();
      tags_name = $("#tags").val();
      relationships = $("#relationships").val();
      isChecked = $("#checkbox:checked").length;
      post = "";

      console.log(isChecked);
      if(isChecked == 1){
          post = $("#post").val();
      }
      console.log(post);
      var obj = { 'topic_name' : topic_name, 'tags_name' : tags_name, 'relationships' : relationships,
        'isChecked' : isChecked, 'post' : post };
      console.log(JSON.stringify(obj));
      window.location.href = "secondTopic.html";
  });
  $('#cancel_bt').click(function(){
  		parent.history.back();
  		return false;
  });

  $('.nextInput').keydown(function (e) {
     if (e.which === 13) {
         var index = $('.nextInput').index(this) + 1;
         $('.nextInput').eq(index).focus();
     }
 });
  /* //Example Get
  $("#tags2").on("keydown",function(event){
       console.log("geldi");
       if(event.which == 13)
         console.log($("#tags").val());
         console.log($("#tags").val());
         $.get("add",{"search": $("#tags").val()}).done(function(data){
         console.log("data come: "+ data);
        });
  });
  */
});
