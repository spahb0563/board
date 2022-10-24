function imageSizeChange(image) {
    let canvas = document.createElement("canvas"),
        max_size = 800,

        width = image.width,
        height = image.height;
    if (width > height) {
        // 가로가 길 경우
        if (width > max_size) {
            height *= max_size / width;
            width = max_size;
        }
    } else {
        // 세로가 길 경우
        if (height > max_size) {
            width *= max_size / height;
            height = max_size;
        }
    }
    canvas.width = width;
    canvas.height = height;
    canvas.getContext("2d").drawImage(image, 0, 0, width, height);
    this.imgUrl = canvas.toDataURL("image/jpeg", 0.5);

    return this.imgUrl;
}

function toFile(imgUrl, file) {
    var arr = imgUrl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], file.name, {type:mime});
}

function receiveFile(file, el) {
    let reader  = new FileReader();
    reader.addEventListener("load", function (e) {
        const image = new Image();
        image.src = e.target.result;
        image.onload = imageEvent => {
            let url = imageSizeChange(image);
            let newFile = toFile(url, file);
            sendFile(newFile, el);
        };
    }, false);

    if (file.name.substring(file.name.lastIndexOf('.')+1, file.name.length) === "gif") {
        sendFile(file, el);
    }else{
        reader.readAsDataURL(file);
    }
}

function sendFile(file, el) {
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
            $(el).summernote('insertImage', url, function($image) {

            });
        }
    });
}