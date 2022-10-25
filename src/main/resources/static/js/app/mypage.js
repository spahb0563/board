const main = {
    init: function () {
        const _this = this;
        $('#btn-picture').on('click', function () {
            $('#upload').click();
        });

        $('#upload').on('change', function (event){
            _this.sendFile(event.target.files[0]);
        })

        $('#btn-update').on('click', function () {
            _this.update();
        });
    },

    update: function () {
        const data = {
            id: $('#id').val(),
            picture: $('#picture').attr('src'),
            name: $('#name').val(),
            nickname: $('#nickname').val()
        };

        $.ajax({
            type : 'PUT',
            url : '/api/v1/users',
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            data: JSON.stringify(data)
        }).done(function (id){
            alert('프로필이 수정되었습니다.');
            location.reload();
        }).fail(function (error){
            alert('수정 실패');
        });
    },

    sendFile : function (file) {

        var form_data = new FormData();
        form_data.append('file', file);
        $.ajax({
            data : form_data,
            type : "POST",
            url : '/image',
            cache : false,
            contentType : false,
            enctype : 'multipart/form-data',
            processData : false,
            success : function(url) {
               $('#picture').attr('src', url);
            }
        });
    }
};

main.init();

