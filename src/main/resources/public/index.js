api = "http://localhost:8080/api/movie/";

function getMovies(){

    $('#error').empty();

    $.getJSON(api + "all", function(data){
        var output = '';

        $.each(data, function(index, value){
            output += '<li data-animal-prey-id="' + value.id + '"><a onclick="toggleMovieWatched(' + value.id + ');">';

            if(value.watched == true){
                output += 'WATCHED - ';
            }

            output += ''+ value.title +'</a><a onclick="deleteMovie(' + value.id + ');">Delete</a></li>';
        });
        $('#listview').empty().append(output).listview('refresh');
    });
}

function saveMovie(){

    $('#error').empty();

    var title = $('#movieTitle').val();

    if(title == null || title == ""){
        $('#error').empty().append("Title cannot be empty!");
        throw "NoTitle";
    }

    $.ajax({
        type: 'POST',
        url: api,
        data: JSON.stringify({
            title : title
        }),
        success: function(data) {
            getMovies();
         },
        contentType: "application/json",
        dataType: 'json'
    });


    $('[data-role=dialog]').dialog("close");
}

function cancelMovies(){
     $('[data-role=dialog]').dialog("close");
}

function deleteMovie(id){

    $.ajax({
        type: 'DELETE',
        url: api + "delete/" + id,
        success: function(data) { getMovies(); }
    });
}

function toggleMovieWatched(id){

    $.ajax({
        type: 'PUT',
        url: api + "watched/" + id,
        success: function(data) { getMovies(); }
    });
}

function openDialog(){
    $('#error').empty();
    $('#movieTitle').val("");
}
