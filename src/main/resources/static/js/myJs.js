$('document').ready(function() {

    $('.table.ebtn').on('click', function(event){

        event.preventDefault();
        let href= $(this).attr('href');
        $.get(href,function (user,status){
            $('#userId').val(user.id);
            /*$('#firstName').val(user.firstname);*/
           /* $('#userId').val(user.id);
            $('#userId').val(user.id);*/
        });
       $('#editModal').modal();

    });

});

