$(document).ready(function () {
  console.log("addrelation.js in içinde");
  /*var data1 = [];
  $.ajax({
    url : "/cocomapapp/topicList/",
    async:true,
    dataType:"json"
  }).fail( function() {
    console.log("error");
  }).done( function(data) {
    $.each(data,function(i,value){
      console.log(value.name);
      data1.push({"name": value.name});
    });
  });
}*/
function formatRepo(item){

  var markup = "<option>"+item.name+"</option>";
  return markup;
}
function formatRepoSelection(item){
  return item.name;
}

  $('#relates_to4').select2({
    placeholder: "Name of the Topic",
    width: "200px",
    ajax: {
      url: '/cocomapapp/topicList/',
      processResults: function (data, params) {
        params.page = params.page || 1;
       var items =[];
       $.each(data,function(i,value){
         console.log(items);
         items.push({id:value.id, name: value.name});

       });
       return {
         results: items,
         pagination: {
             more: (params.page * 30) < data.total_count
         }
       };
     },
     cache: true
    },


    escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
    minimumInputLength: 1,
    templateResult: formatRepo, // omitted for brevity, see the source of this page
    templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
  });

  $('#relates_to5').select2({
    placeholder: "Name of the Topic",
    allowClear: true,
    width: "200px",
    ajax: {
      url: '/cocomapapp/topicList/',
      processResults: function (data, params) {
        params.page = params.page || 1;
       var items =[];
       $.each(data,function(i,value){
         console.log(items);
         items.push({id:value.id, name: value.name});

       });
       return {
         results: items,
         pagination: {
             more: (params.page * 30) < data.total_count
         }
       };
     },
     cache: true
    },


    escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
    minimumInputLength: 1,
    templateResult: formatRepo, // omitted for brevity, see the source of this page
    templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
  });

  $('#relates_to6').select2({
    placeholder: "Name of the Topic",
    width: "200px",
  });

})
