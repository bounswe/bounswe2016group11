function AddTopic() {
    jQuery.support.cors = true;
    var topic = {
        name: $('#name').val(),
        relates_to: $('#relates_to').val(),
        tags: $('#tags').val(),
        posts: [],
    };
    $.ajax({
        url: 'add',
        type: 'POST',
        data:JSON.stringify(topic),
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            return data;
        },
        error: function (x, y, z) {
            alert(x + '\n' + y + '\n' + z);
        }
    });
}
