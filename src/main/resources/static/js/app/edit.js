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
            content: $('#content').val()
        };

        var tmp = data.content.replace(/<(\/?)p>/gi,"");//p태그 제거
        tmp = tmp.replace(/(<br>)|(<br\/>)/gi,"");//br태그 제거
        tmp = tmp.replace(/\s/gi,"");//공백제거
        tmp = tmp.replace(/&nbsp;/gi,"");//공백제거

        if(data.title === '' || data.title.trim() === '') {
            alert('제목을 입력해주세요.');
            $('#title').focus();
            return false;
        }else if(data.title.length > 50) {
            alert('제목은 50자까지 입력 가능합니다.');
            $('#title').focus();
            return false;
        }else if(data.content === $('#content').val()) {
            alert('내용을 수정해주세요.');
            return false;
        }else if(tmp === '') {
            alert('내용을 입력해주세요.');
            $('#content').summernote({focus: true});
            return false;
        }

        $.ajax({
            type : 'PUT',
            url : '/api/v1/post',
            dataType: 'json',
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

