$(document).ready(function () {
  $('#relates_to4').select2({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      /*load: function(query, callback) {
        if (!query.length) return callback();
        callback(relations);
      }*/
  });

  $('#relates_to5').select2({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
      /*load: function(query, callback) {
        if (!query.length) return callback();
        callback(relations);
      }*/
  });

  $('#relates_to6').select2({
      maxItems: 1,
      maxOptions: 3,
      valueField: 'id',
      labelField: 'name',
      searchField: ['name'],
      options: [],
      create: false,
    /*  load: function(query, callback) {
        if (!query.length) return callback();
        callback(relations);
      }*/
  });


})
