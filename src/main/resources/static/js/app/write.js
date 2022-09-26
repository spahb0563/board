const main = {
    init: function () {
        const _this = this;
        $('#btn-create').on('click', function () {
            _this.create();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        })
    },

    create: function () {
        var data = {
            category: $('#category').val().toUpperCase(),
            title: $('#title').val(),
            content: $('#content').val().replace(/\n/g, "<br/>"),
            usersId: 1
        };

        $.ajax({
            type : 'POST',
            url : '/api/v1/post',
            dateType: 'json',
            contentType: 'application/json; charset=utf8',
            data: JSON.stringify(data)
        }).done(function (){
            alert('글이 등록되었습니다.');
            window.location.href= '/'+data.category.toLowerCase();
        }).fail(function (error){
            alert('등록 실패');
        });
    }
};

main.init();

