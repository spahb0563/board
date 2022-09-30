const main = {
    init: function () {
        const _this = this;
        $('#btn-createParent').on('click', function () {
            _this.createParent();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        })
    },

    createParent: function () {
        const data = {
            content : $('#opinionContent').val(),
            postId : $('#postId').val(),
            usersId : 1
        };

        if(data.content == '') {
            alert('댓글을 입력해 주세요.');
            $('#opinionContent').focus();
            return;
        }

        $.ajax({
            type : 'POST',
            url : '/api/v1/opinion',
            dateType: 'json',
            contentType: 'application/json; charset=utf8',
            async: false,
            data: JSON.stringify(data)
        }).done(function (result){
            let replyItem = '<div><div class="border rounded p-3 my-3"><div><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg"></i>';
            replyItem += '<span>'+result.users.nickname+'</span></div>';
            replyItem += '<div><p>'+result.content+'</p></div><div class="row"><div class="col text-right">';
            replyItem += '<button class="btn btn-primary" form="opinion" type="button">대댓글</button></div></div></div>';
            replyItem += '<div></div></div>';
            $('#opinionList').append(replyItem);
            $('#opinionContent').val('');
        }).fail(function (error){
            alert('등록 실패');
        });
    },

    createTextField: function (parentId) {
        let replyItem = '<div><div class="border rounded p-3 my-3"><div><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg"></i>';
        $('#parentId'+parentId).after(replyItem)
    },

    createChild: function () {
        const data = {
            parentId : $().val(),
            content : $('#opinionContent').val(),
            postId : $('#postId').val(),
            usersId : 1
        }
    }
};
main.init();

