const main = {
    init: function () {
        const _this = this;
        $('#btn-createParent').on('click', function () {
            _this.createParent();
        });

        $(document).on('click','#btn-createChild', function () {
            _this.createChild();
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
            usersId : $('#userId').val()
        };

        if(data.content === '') {
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
            if(result.users == null) return;
            let replyItem = '<div id="parentList'+result.id+'">\n' +
                '          <div id="parentId'+result.id+'" class="border rounded p-3 my-3">\n' +
                '            <div class="d-flex">\n' +
                '              <div>\n' +
                '              <img class="rounded-circle" style="height: 50px; width: 50px" onerror="this.src=\'https://dummyimage.com/50x50/ced4da/6c757d.jpg\'" src="'+result.users.picture+'">\n' +
                '              </div>\n' +
                '              <div class="w-50 ml-3">\n' +
                '                <span class="d-block">'+result.users.nickname+'</span>\n' +
                '                <span class="d-block">'+result.createdAt+'</span>\n' +
                '              </div>\n' +
                '              <div class="row w-50">\n' +
                '                <div class="col text-right">\n' +
                '                  <i class="bi bi-hand-thumbs-up text-primary mx-1" style="font-size: 1.5rem"></i>\n' +
                '                  <span class="align-middle pt-3" style="font-size: 1rem">'+result.likeCount+'</span>\n' +
                '                  <i class="bi bi-hand-thumbs-down text-danger mx-1" style="font-size: 1.5rem"></i>\n' +
                '                  <span class="align-middle pt-3" style="font-size: 1rem">'+result.dislikeCount+'</span>\n' +
                '                </div>\n' +
                '              </div>\n' +
                '            </div>\n' +
                '            <div class="pt-3">\n' +
                '              <p>'+result.content+'</p>\n' +
                '            </div>\n' +
                '            <div class="row">\n' +
                '              <div class="col text-right">\n' +
                '                <button class="btn btn-primary" form="opinion" type="button" onclick="main.createTextField('+result.id+',\''+result.users.picture+'\')">대댓글</button>\n' +
                '              </div>\n' +
                '            </div>\n' +
                '          </div>';
            $('#opinionList').append(replyItem);
            $('#opinionContent').val('');
        }).fail(function (error){
            alert('등록 실패');
        });
    },

    createTextField: function (parentId, userPicture) {
        let replyItem = '<div class="border rounded p-3 my-3 ml-5" id="childOpinionTextField">\n' +
            '          <div class="d-flex">\n' +
            '            <div>\n' +
            '              <img class="rounded-circle" style="height: 50px; width: 50px" onerror="this.src=\'https://dummyimage.com/50x50/ced4da/6c757d.jpg\'" src="'+userPicture+'">\n' +
            '            </div>\n' +
            '            <div class="w-100 ml-3">\n' +
            '              <form id="childOpinion">\n' +
            '                <input type="hidden" id="parentId" value='+parentId+'>\n' +
            '                <textarea class="form-control" id="childOpinionContent"></textarea>\n' +
            '              </form>\n' +
            '            </div>\n' +
            '          </div>\n' +
            '          <div class="row">\n' +
            '            <div class="col text-right mt-3">\n' +
            '              <button class="btn btn-primary" form="childOpinion" type="button" id="btn-createChild">댓글남기기</button>\n' +
            '            </div>\n' +
            '          </div>\n' +
            '        </div>';
        if($('#childOpinionTextField').length === 0) {
            $('#parentId'+ parentId).after(replyItem);
            $('#childOpinionContent').focus();
        }else {
            if(parentId == $('#parentId').val()) {
                $('#childOpinionTextField').remove();
            }else {
                $('#childOpinionTextField').remove();
                $('#parentId'+ parentId).after(replyItem);
                $('#childOpinionContent').focus();
            }
        }
    },

    createChild: function () {

        const data = {
            parentId : $('#parentId').val(),
            content : $('#childOpinionContent').val(),
            postId : $('#postId').val(),
            usersId : $('#userId').val()
        }

        if(data.content === '') {
            alert('댓글을 입력해 주세요.');
            $('#childOpinionContent').focus();
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
            $('#childOpinionTextField').remove();
            let Item = '<div>\n' +
                '            <div class="border rounded p-3 my-3 ml-5">\n' +
                '              <div class="d-flex">\n' +
                '                <div>\n' +
                '                   <img class="rounded-circle" style="height: 50px; width: 50px" onerror="this.src=\'https://dummyimage.com/50x50/ced4da/6c757d.jpg\'" src="'+result.users.picture+'">\n' +
                '                </div>\n' +
                '                <div class="w-50 ml-3">\n' +
                    '                <span class="d-block">'+result.users.nickname+'</span>\n' +
                    '                <span class="d-block">'+result.createdAt+'</span>\n' +
                '                </div>\n' +
                '                <div class="row w-50">\n' +
                    '                <div class="col text-right">\n' +
                    '                  <i class="bi bi-hand-thumbs-up text-primary mx-1" style="font-size: 1.5rem"></i>\n' +
                    '                  <span class="align-middle pt-3" style="font-size: 1rem">'+result.likeCount+'</span>\n' +
                    '                  <i class="bi bi-hand-thumbs-down text-danger mx-1" style="font-size: 1.5rem"></i>\n' +
                    '                  <span class="align-middle pt-3" style="font-size: 1rem">'+result.dislikeCount+'</span>\n' +
                    '                </div>\n' +
                '                </div>\n' +
                '              </div>\n' +
                '              <div class="pt-3">\n' +
                '               <p>'+result.content+'</p>\n' +
                '              </div>\n' +
                '            </div>';
            $('#parentList'+result.parentId).append(Item);
            $('#childOpinionContent').val('');
        }).fail(function (error){
            alert('등록 실패');
        });
    }
};
main.init();

