$(document).ready(function () {
  $('#relates_to4').selectize({
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

  $('#relates_to5').selectize({
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

  $('#relates_to6').selectize({
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
