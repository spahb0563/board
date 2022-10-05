const main = {
    init: function () {
        const _this = this;
        $('#btn-createParent').on('click', function () {
            _this.createParent();
        });

        $(document).on('click','#btn-createChild', function () {
            _this.createChild();
        });

        $('#btn-deletePost').on('click', function () {
            _this.deletePost();
        })
    },

    createParent: function () {
        const data = {
            content : $('#opinionContent').val().replace(/\n/g, "<br/>"),
            postId : $('#postId').val(),
            usersId : $('#userId').val()
        };

        if(data.content === '' || data.content.trim() === '') {
            alert('댓글을 입력해 주세요.');
            $('#childOpinionContent').focus();
            return false;
        }

        $.ajax({
            type : 'POST',
            url : '/api/v1/opinion',
            dateType: 'json',
            contentType: 'application/json; charset=utf8',
            async: false,
            data: JSON.stringify(data)
        }).done(function (result){
            let replyItem = '<div id="parentList'+result.id+'">\n' +
                '          <div id="'+result.id+'" class="border rounded p-3 my-3">\n' +
                '            <div class="d-flex">\n' +
                '              <div>\n' +
                '              <img class="rounded-circle" style="height: 50px; width: 50px" onerror="this.src=\'/images/anonymous.png\'" src="'+result.users.picture+'">\n' +
                '              </div>\n' +
                '              <div class="w-50 ml-3">\n' +
                '                <span class="d-block">'+result.users.nickname+'</span>\n' +
                '                <span class="d-block">'+result.createdAt+'</span>\n' +
                '              </div>\n' +
                '              <div class="row w-50">\n' +
                '                <div class="col text-right">\n' +
                '                  <i class="bi bi-hand-thumbs-up text-primary mx-1"></i>\n' +
                '                  <span class="align-middle pt-3">'+result.likeCount+'</span>\n' +
                '                  <i class="bi bi-hand-thumbs-down text-danger mx-1"></i>\n' +
                '                  <span class="align-middle pt-3">'+result.dislikeCount+'</span>\n' +
                '                       <button class="border-0 float-right ml-3" data-toggle="dropdown" style="background-color: transparent;">\n' +
                '                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">\n' +
                '                          <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>\n' +
                '                          <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>\n' +
                '                        </svg>\n' +
                '                      </button>\n' +
                '                      <div class="dropdown-menu dropdown-menu-right">\n' +
                '                        <a class="dropdown-item" type="button" onclick="main.createUpdateField('+result.id+ ', true)">수정하기</a>\n' +
                '                        <button class="dropdown-item" type="button" onclick="main.deleteOpinion('+result.id+ ', true)">삭제하기</button>\n' +
                '                      </div>' +
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
            '              <img class="rounded-circle" style="height: 50px; width: 50px" onerror="this.src=\'/images/anonymous.png\'" src="'+userPicture+'">\n' +
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
            $('#'+parentId).after(replyItem);
            $('#childOpinionContent').focus();
        }else {
            if(parentId == $('#parentId').val()) {
                $('#childOpinionTextField').remove();
            }else {
                $('#childOpinionTextField').remove();
                $('#'+parentId).after(replyItem);
                $('#childOpinionContent').focus();
            }
        }
    },

    createChild: function () {

        const data = {
            parentId : $('#parentId').val(),
            content : $('#childOpinionContent').val().replace(/\n/g, "<br/>"),
            postId : $('#postId').val(),
            usersId : $('#userId').val()
        }

        if(data.content === '' || data.content.trim() === '') {
            alert('댓글을 입력해 주세요.');
            $('#childOpinionContent').focus();
            return false;
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
            let replyItem =
                '            <div id="'+result.id+'" class="border rounded p-3 my-3 ml-5">\n' +
                '              <div class="d-flex">\n' +
                '                <div>\n' +
                '                   <img class="rounded-circle" style="height: 50px; width: 50px" onerror="this.src=\'/images/anonymous.png\'" src="'+result.users.picture+'">\n' +
                '                </div>\n' +
                '                <div class="w-50 ml-3">\n' +
                    '                <span class="d-block">'+result.users.nickname+'</span>\n' +
                    '                <span class="d-block">'+result.createdAt+'</span>\n' +
                '                </div>\n' +
                '                <div class="row w-50">\n' +
                    '                <div class="col text-right">\n' +
                    '                  <i class="bi bi-hand-thumbs-up text-primary mx-1" "></i>\n' +
                    '                  <span class="align-middle pt-3">'+result.likeCount+'</span>\n' +
                    '                  <i class="bi bi-hand-thumbs-down text-danger mx-1" "></i>\n' +
                    '                  <span class="align-middle pt-3">'+result.dislikeCount+'</span>\n' +
                    '                       <button class="border-0 float-right ml-3" data-toggle="dropdown" style="background-color: transparent;">\n' +
                    '                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">\n' +
                    '                          <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>\n' +
                    '                          <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>\n' +
                    '                        </svg>\n' +
                    '                      </button>\n' +
                    '                      <div class="dropdown-menu dropdown-menu-right">\n' +
                    '                        <a class="dropdown-item" type="button" onclick="main.createUpdateField('+result.id+ ', false)">수정하기</a>\n' +
                    '                        <button class="dropdown-item" type="button" onclick="main.deleteOpinion('+result.id+ ', false)">삭제하기</button>\n' +
                    '                      </div>' +
                    '                </div>\n' +
                '                </div>\n' +
                '              </div>\n' +
                '              <div class="pt-3">\n' +
                '               <p>'+result.content+'</p>\n' +
                '              </div>';
            $('#parentList'+result.parentId).append(replyItem);
            $('#childOpinionContent').val('');
        }).fail(function (error){
            alert('등록 실패');
        });
    },

    deletePost: function () {
        const id = $('#postId').val();

        if(!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        $.ajax({
            type : 'DELETE',
            url : '/api/v1/post/'+id,
            dateType: 'json',
            contentType: 'application/json; charset=utf8',
        }).done(function (id){
            alert('글이 삭제되었습니다.');
            window.location.href= '/';
        }).fail(function (error){
            alert('삭제 실패');
        });
    },

    deleteOpinion: function(id, isParent) {
        if(!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        $.ajax({
            type : 'DELETE',
            url : '/api/v1/opinion/'+id,
            dateType: 'json',
            contentType: 'application/json; charset=utf8',
        }).done(function (id){
            alert('댓글이 삭제되었습니다.');
            if(isParent) { // 부모 댓글 삭제
                if($('#parentList'+id).children().length > 1) { // 부모 댓글에 자식 댓글이 하나라도 있으면 댓글 리스트는 유지하고 부모댓글만 삭제처리
                    $('#'+id).html('');
                    $('#'+id).html('<span>삭제된 댓글입니다.</span>');
                }else {
                    $('#parentList'+id).remove(); // 자식 댓글이 없으면 댓글 리스트 삭제
                }
            }else { // 자식 댓글 삭제
                if($('#'+id).parent().children().length === 2) { // 자식 댓글이 하나 일 때
                    if($('#'+id).parent().children().first().html() == '<span>삭제된 댓글입니다.</span>') { // 부모 댓글이 삭제된 상태일 때
                        $('#'+id).parent().remove(); // 리스트를 전부 삭제
                    }else { // 부모 댓글이 삭제되지 않았을 때
                        $('#'+id).remove(); //자식 댓글만 삭제
                    }
                }else { //자식 댓글이 하나 이상일 때
                    $('#'+id).remove();
                }
            }
        }).fail(function (error){
            alert('삭제 실패');
        });
    },

    createUpdateField : function (id, isParent) {

        if($('#'+id).find('textarea').length === 1) {
            return;
        } // 수정창 중복생성 방지

        const content = $('#'+id).find('p').text();

        const replyItem = '<div>' +
            '               <form class="mt-3">\n' +
            '                 <textarea class="form-control">'+content+'</textarea>\n' +
            '               </form>' +
            '              </div>' +
            '              <div class="row mt-3">\n' +
            '                <div class="col text-right">\n' +
            '                  <button class="btn btn-outline-primary" type="button" onclick="main.updateCancel('+id+')">&nbsp; 취소 &nbsp;</button>' +
            '                  <button class="btn btn-primary" type="button" onclick="main.updateOpinion('+id+')">&nbsp; 수정 &nbsp;</button>\n' +
            '                </div>\n' +
            '              </div>';

        $('#'+id).find('p').parent().hide(); // 텍스트 감추기
        if(isParent) {
            $('#'+id).children().last().hide(); // 대댓글 버튼 감추기
        }

        $('#'+id).append(replyItem); // 수정창 추가
    },

    updateOpinion : function (id) {

        const data = {
            content : $('#'+id).find('textarea').val(),
            id : id
        };

        if($('#'+id).find('p').html() === data.content) {
            $('#'+id).find('textarea').focus();
            alert("댓글을 수정해주세요.");
            return;
        }// 수정 안했을 시 수정하도록

        $.ajax({
            type : 'PUT',
            url : '/api/v1/opinion/',
            dateType: 'json',
            contentType: 'application/json; charset=utf8',
            data: JSON.stringify(data)
        }).done(function (id){
            alert('댓글이 수정되었습니다.');
            $('#'+id).find('form').parent().remove();
            $('#'+id).children().last().remove();
            $('#'+id).children(':hidden').show();
        }).fail(function (error){
            alert('수정 실패');
        });
    },

    updateCancel : function (id) {
            $('#'+id).find('form').parent().remove();
            $('#'+id).children().last().remove();
            $('#'+id).children(':hidden').show();
    } // 취소버튼 누르면 숨겨진 요소 보여주고 추가되었던 수정창 삭제
};
main.init();

