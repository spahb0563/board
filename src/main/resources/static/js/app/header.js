const header = {
    init: function () {
        const _this = this;

        $(window).on('load', function () {
            _this.check();
        });

        $('#notification1, #notification2').on('click',function (e) {
            _this.readNotificationList();
            $('#notificationDot1, #notificationDot2').css("visibility", "hidden");
            $('#notification1, #notification2').off('click');
        });

        $(document).on('click', '.dropdownContainer .dropdown-menu', function (e) {
            e.stopPropagation();
        });
    },

    readNotificationList : function () {
        $.ajax({
            type : 'GET',
            url : '/api/v1/notification/'+userId,
            dataType: 'json',
            contentType: 'application/json; charset=utf8'
        }).done(function (result){
            for(let i=0; i<result.length; i++) {
                let reply = '' +
                    '<div class="d-flex bg-light notification'+result[i].id+'">' +
                    '   <div class="w-auto">' +
                    '   <a class="h-3 btn btn-light btn-block my-1" href="/post/'+result[i].postId+' #'+result[i].targetId+'">' +
                    '       <div class="d-flex">' +
                    '           <img class="rounded-circle mr-1" style="width: 30px; height: 30px" onerror="this.src=\'/images/anonymous.png\'" src="'+result[i].sender.picture+'">' +
                    '           <div class="w-50 text-left">' +
                    '               <span class="mx-1">'+result[i].sender.nickname+'</span>' +
                    '           </div>' +
                    '           <div class="w-50">' +
                    '               <div class="col text-right">' +
                    '                   <span>'+result[i].createdAt+'</span>' +
                    '               </div>' +
                    '           </div>' +
                    '       </div>' +
                    '       <div>' +
                    '           <p style="word-wrap:break-word;" class="text-left">'+result[i].message+'</p>' +
                    '       </div>' +
                    '   </a>' +
                    '   </div>' +
                    '   <div class="align-self-center">' +
                    '       <button class="border-0" style="background-color: transparent" onclick="header.deleteNotification('+result[i].id+')">' +
                    '           <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">' +
                    '               <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>' +
                    '               <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>' +
                    '           </svg>' +
                    '       </button>' +
                    '   </div>' +
                    '</div>';
                $('#notificationList1, #notificationList2').append(reply);
            }
        });
    },

    check : function () {
        $.ajax({
            type : 'GET',
            url : '/api/v1/notification/'+userId+'/new',
            dataType: 'json',
            contentType: 'application/json; charset=utf8'
        }).done(function (result){
            if(result) {
                $('#notificationDot1, #notificationDot2').css("visibility", "visible");
            }
        });
    },

    deleteAllNotification : function () {


        if(!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        $.ajax({
            type : 'DELETE',
            url : '/api/v1/notification/'+userId,
            dataType : 'json',
            contentType : 'application/json; charset=utf8'
        }).done(function (result){
            alert('삭제 성공');
            $('#notificationList1').find('div.d-flex').remove();
            $('#notificationList2').find('div.d-flex').remove();
        }).fail(function (error){
            alert('삭제 실패');
        });
    },

    deleteNotification : function (id) {
        if(!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        $.ajax({
            type : 'DELETE',
            url : '/api/v1/notification/'+userId+"/"+id,
            dataType : 'json',
            contentType : 'application/json; charset=utf8'
        }).done(function (result){
            alert('삭제 성공');
            $('#notificationList1').find('div.notification'+id).remove();
            $('#notificationList2').find('div.notification'+id).remove();
        }).fail(function (error){
            alert('삭제 실패');
        });
    }
};

header.init();

