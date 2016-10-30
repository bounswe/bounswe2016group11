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


  /*
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



  $('#cancel_bt').click(function(){
  		parent.history.back();
  		return false;
  	});





  /*
  console.log("here");
    $('input#tags').typeahead({
        name: 'typeahead',
        remote:'topic/add',
        limit : 10
    });
    */
    // console.log("here");
    // var cars = ['Audi', 'BMW', 'Bugatti', 'Ferrari', 'Ford', 'Lamborghini', 'Mercedes Benz', 'Porsche', 'Rolls-Royce', 'Volkswagen'];
    //
    //     // Constructing the suggestion engine
    //     var cars = new Bloodhound({
    //         datumTokenizer: Bloodhound.tokenizers.whitespace,
    //         queryTokenizer: Bloodhound.tokenizers.whitespace,
    //         local: cars
    //     });
    //
    //     // Initializing the typeahead
    //     $('.typeahead').typeahead({
    //         hint: true,
    //         highlight: true, /* Enable substring highlighting */
    //         minLength: 1 /* Specify minimum characters required for showing result */
    //     },
    //     {
    //         name: 'cars',
    //         source: cars
    //     });
    //
    //
    //
    //   $("#tags").on("keydown",function(event){
    //
    //     console.log("geldi");
    //     if(event.which == 13)
    //       console.log($("#tags").val());
    //     // console.log($("#tags").val());
    //     // $.get("add",{"search": $("#tags").val()}).done(function(data){
    //     //   console.log("data come: "+ data);
    //     // });
    //   });
});
