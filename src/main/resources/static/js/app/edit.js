const main = {
    init: function () {
        const _this = this;
        $('#btn-update').on('click', function () {
            _this.update();
        });
    },

    update: function () {
        const data = {
            id: $('#postId').val(),
            title: $('#title').val(),
            content: $('#content').val().replace(/\n/g, "<br/>")
        };

        if(data.title === '' || data.title.trim() === '') {
            alert('제목을 입력해주세요.');
            $('#title').focus();
            return false;
        }else if(data.title.length > 50) {
            alert('제목은 50자까지 입력 가능합니다.');
            $('#title').focus();
            return false;
        }else if(data.content === '' || data.content.trim() === '') {
            alert('내용을 입력해주세요.');
            $('#content').focus();
            return false;
        }

        $.ajax({
            type : 'PUT',
            url : '/api/v1/post',
            dateType: 'json',
            contentType: 'application/json; charset=utf8',
            data: JSON.stringify(data)
        }).done(function (id){
            alert('글이 수정되었습니다.');
            window.location.href= '/post/'+id;
        }).fail(function (error){
            alert('수정 실패');
        });
    }
};

main.init();

