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
        });

        $('#btn-like').on('click', function () {
            _this.likePost();
        });

        $('#btn-dislike').on('click', function () {
            _this.dislikePost();
        });
    },

    likeOpinion : function (id) {
        $.ajax({
            type : 'PUT',
            url : '/api/v1/opinion/'+id+'/like',
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            async: false,
        }).done(function (result){
            $('#opinionLike'+id).html(result);
        }).fail(function (error){
            alert('좋아요 실패');
        });
    },


    disLikeOpinion : function (id) {
        $.ajax({
            type : 'PUT',
            url : '/api/v1/opinion/'+id+'/dislike',
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            async: false,
        }).done(function (result){
            $('#opinionDislike'+id).html(result);
        }).fail(function (error){
            alert('좋아요 실패');
        });
    },


    likePost: function () {
        const id = $('#postId').val();
        $.ajax({
            type : 'PUT',
            url : '/api/v1/post/'+id+'/like',
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            async: false,
        }).done(function (result){
            $('#postLike').html(result);
        }).fail(function (error){
            alert('좋아요 실패');
        });
    },

    dislikePost: function () {
        const id = $('#postId').val();

        $.ajax({
            type : 'PUT',
            url : '/api/v1/post/'+id+'/dislike',
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            async: false,
        }).done(function (result){
            $('#postDislike').html(result);
        }).fail(function (error){
            alert('싫어요 실패');
        });
    },

    createParent: function () {

        const data = {
            content : $('#opinionContent').val().replace(/\n/g, "<br/>"),
            postId : $('#postId').val(),
            usersId : userId
        };

        if(data.content === '' || data.content.trim() === '') {
            alert('댓글을 입력해 주세요.');
            $('#childOpinionContent').focus();
            return false;
        }

        $.ajax({
            type : 'POST',
            url : '/api/v1/opinion',
            dataType: 'json',
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
                '                  <button class="border-0" style="background-color: transparent;" onclick="main.likeOpinion('+result.id+')"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-up text-primary" viewBox="0 0 16 16">\n' +
                '                      <path d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z"/>\n' +
                '                    </svg></button>\n' +
                '                  <span class="align-middle pt-3" id="opinionLike'+result.id+'">'+result.likeCount+'</span>\n' +
                '                  <button class="border-0" style="background-color: transparent;" onclick="main.disLikeOpinion('+result.id+')"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-down text-danger" viewBox="0 0 16 16">\n' +
                '                      <path d="M8.864 15.674c-.956.24-1.843-.484-1.908-1.42-.072-1.05-.23-2.015-.428-2.59-.125-.36-.479-1.012-1.04-1.638-.557-.624-1.282-1.179-2.131-1.41C2.685 8.432 2 7.85 2 7V3c0-.845.682-1.464 1.448-1.546 1.07-.113 1.564-.415 2.068-.723l.048-.029c.272-.166.578-.349.97-.484C6.931.08 7.395 0 8 0h3.5c.937 0 1.599.478 1.934 1.064.164.287.254.607.254.913 0 .152-.023.312-.077.464.201.262.38.577.488.9.11.33.172.762.004 1.15.069.13.12.268.159.403.077.27.113.567.113.856 0 .289-.036.586-.113.856-.035.12-.08.244-.138.363.394.571.418 1.2.234 1.733-.206.592-.682 1.1-1.2 1.272-.847.283-1.803.276-2.516.211a9.877 9.877 0 0 1-.443-.05 9.364 9.364 0 0 1-.062 4.51c-.138.508-.55.848-1.012.964l-.261.065zM11.5 1H8c-.51 0-.863.068-1.14.163-.281.097-.506.229-.776.393l-.04.025c-.555.338-1.198.73-2.49.868-.333.035-.554.29-.554.55V7c0 .255.226.543.62.65 1.095.3 1.977.997 2.614 1.709.635.71 1.064 1.475 1.238 1.977.243.7.407 1.768.482 2.85.025.362.36.595.667.518l.262-.065c.16-.04.258-.144.288-.255a8.34 8.34 0 0 0-.145-4.726.5.5 0 0 1 .595-.643h.003l.014.004.058.013a8.912 8.912 0 0 0 1.036.157c.663.06 1.457.054 2.11-.163.175-.059.45-.301.57-.651.107-.308.087-.67-.266-1.021L12.793 7l.353-.354c.043-.042.105-.14.154-.315.048-.167.075-.37.075-.581 0-.211-.027-.414-.075-.581-.05-.174-.111-.273-.154-.315l-.353-.354.353-.354c.047-.047.109-.176.005-.488a2.224 2.224 0 0 0-.505-.804l-.353-.354.353-.354c.006-.005.041-.05.041-.17a.866.866 0 0 0-.121-.415C12.4 1.272 12.063 1 11.5 1z"/>\n' +
                '                    </svg></button>\n' +
                '                  <span class="align-middle pt-3" id="opinionDislike'+result.id+'">'+result.dislikeCount+'</span>\n' +
                '                       <button class="border-0 float-right ml-3" data-toggle="dropdown" style="background-color: transparent;">\n' +
                '                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">\n' +
                '                          <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>\n' +
                '                          <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>\n' +
                '                        </svg>\n' +
                '                      </button>\n' +
                '                      <div class="dropdown-menu dropdown-menu-right">\n' +
                '                        <button class="dropdown-item" type="button" onclick="main.createUpdateField('+result.id+', true)">수정하기</button>\n' +
                '                        <button class="dropdown-item" type="button" onclick="main.deleteOpinion('+result.id+', true)">삭제하기</button>\n' +
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
            usersId : userId
        }

        if(data.content === '' || data.content.trim() === '') {
            alert('댓글을 입력해 주세요.');
            $('#childOpinionContent').focus();
            return false;
        }

        $.ajax({
            type : 'POST',
            url : '/api/v1/opinion',
            dataType: 'json',
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
                '                  <button class="border-0" style="background-color: transparent;" onclick="main.likeOpinion('+result.id+')"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-up text-primary" viewBox="0 0 16 16">\n' +
                '                      <path d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z"/>\n' +
                '                    </svg></button>\n' +
                '                  <span class="align-middle pt-3" id="opinionLike'+result.id+'">'+result.likeCount+'</span>\n' +
                '                  <button class="border-0" style="background-color: transparent;" onclick="main.disLikeOpinion('+result.id+')"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-down text-danger" viewBox="0 0 16 16">\n' +
                '                      <path d="M8.864 15.674c-.956.24-1.843-.484-1.908-1.42-.072-1.05-.23-2.015-.428-2.59-.125-.36-.479-1.012-1.04-1.638-.557-.624-1.282-1.179-2.131-1.41C2.685 8.432 2 7.85 2 7V3c0-.845.682-1.464 1.448-1.546 1.07-.113 1.564-.415 2.068-.723l.048-.029c.272-.166.578-.349.97-.484C6.931.08 7.395 0 8 0h3.5c.937 0 1.599.478 1.934 1.064.164.287.254.607.254.913 0 .152-.023.312-.077.464.201.262.38.577.488.9.11.33.172.762.004 1.15.069.13.12.268.159.403.077.27.113.567.113.856 0 .289-.036.586-.113.856-.035.12-.08.244-.138.363.394.571.418 1.2.234 1.733-.206.592-.682 1.1-1.2 1.272-.847.283-1.803.276-2.516.211a9.877 9.877 0 0 1-.443-.05 9.364 9.364 0 0 1-.062 4.51c-.138.508-.55.848-1.012.964l-.261.065zM11.5 1H8c-.51 0-.863.068-1.14.163-.281.097-.506.229-.776.393l-.04.025c-.555.338-1.198.73-2.49.868-.333.035-.554.29-.554.55V7c0 .255.226.543.62.65 1.095.3 1.977.997 2.614 1.709.635.71 1.064 1.475 1.238 1.977.243.7.407 1.768.482 2.85.025.362.36.595.667.518l.262-.065c.16-.04.258-.144.288-.255a8.34 8.34 0 0 0-.145-4.726.5.5 0 0 1 .595-.643h.003l.014.004.058.013a8.912 8.912 0 0 0 1.036.157c.663.06 1.457.054 2.11-.163.175-.059.45-.301.57-.651.107-.308.087-.67-.266-1.021L12.793 7l.353-.354c.043-.042.105-.14.154-.315.048-.167.075-.37.075-.581 0-.211-.027-.414-.075-.581-.05-.174-.111-.273-.154-.315l-.353-.354.353-.354c.047-.047.109-.176.005-.488a2.224 2.224 0 0 0-.505-.804l-.353-.354.353-.354c.006-.005.041-.05.041-.17a.866.866 0 0 0-.121-.415C12.4 1.272 12.063 1 11.5 1z"/>\n' +
                '                    </svg></button>\n' +
                '                  <span class="align-middle pt-3" id="opinionDislike'+result.id+'">'+result.dislikeCount+'</span>\n' +
                    '                       <button class="border-0 float-right ml-3" data-toggle="dropdown" style="background-color: transparent;">\n' +
                    '                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">\n' +
                    '                          <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>\n' +
                    '                          <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>\n' +
                    '                        </svg>\n' +
                    '                      </button>\n' +
                    '                      <div class="dropdown-menu dropdown-menu-right">\n' +
                    '                        <button class="dropdown-item" type="button" onclick="main.createUpdateField('+result.id+', false)">수정하기</button>\n' +
                    '                        <button class="dropdown-item" type="button" onclick="main.deleteOpinion('+result.id+', false)">삭제하기</button>\n' +
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
            dataType: 'json',
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
            dataType: 'json',
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
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            data: JSON.stringify(data)
        }).done(function (id){
            alert('댓글이 수정되었습니다.');
            $('#'+id).find('form').parent().remove();
            $('#'+id).children().last().remove();
            $('#'+id).find('p').html(data.content);
            $('#'+id).children(':hidden').show();
        }).fail(function (error){
            alert('수정 실패');
        });
    },

    updateCancel : function (id) {
            $('#'+id).find('form').parent().remove();
            $('#'+id).children().last().remove();
            $('#'+id).children(':hidden').show();
    }, // 취소버튼 누르면 숨겨진 요소 보여주고 추가되었던 수정창 삭제
};
main.init();

