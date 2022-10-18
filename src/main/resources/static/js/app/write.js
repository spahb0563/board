const main = {
    init: function () {
        const _this = this;
        $('#btn-create').on('click', function () {
            _this.create();
        });

        $(window).on('load', function (){
            _this.select();
        });
    },

    create: function () {
        const data = {
            category: $('#category option:selected').val(),
            title: $('#title').val(),
            content: $('#content').val().replace(/\n/g, "<br/>"),
            usersId: $('#userId').val()
        };

        if(data.category === '게시판을 선택해 주세요.') {
            alert('게시판을 선택해 주세요.');
            $('#category').focus();
            return false;
        }else if (data.title === '' || data.title.trim() === '') {
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
            type : 'POST',
            url : '/api/v1/post',
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            data: JSON.stringify(data)
        }).done(function (id){
            alert('글이 등록되었습니다.');
            window.location.href= '/post/'+id;
        }).fail(function (error){
            alert('등록 실패');
        });
    },

    select : function () {
        const path = this.getPath().toUpperCase();
        if($('#category option[value='+path+']').length != 0) {
            $('#category').val(path).prop("selected", true);
        }else {
            $('#category').val('게시판을 선택해 주세요.').prop('selected', true);
        }
    },

    getPath : function (){
        const pre = document.referrer;
        const path = pre.substring(pre.lastIndexOf('/')+1, pre.length);
        return path;
    }
};

main.init();

