const main = {
    init: function () {
        const _this = this;

        $('.page-link').on('click', function () {
            _this.movePage($(this).val());
        })

        $('#sortMenu button').on('click', function () {
            _this.sort($(this).val());
        })
    },

    sort : function (sort) {
        const URLSearch = new URLSearchParams(location.search);
        URLSearch.set('sort', sort);
        const newParam = decodeURIComponent(URLSearch.toString());
        window.open(location.pathname + '?' + newParam, '_self');
    },

    movePage : function(page) {
        const URLSearch = new URLSearchParams(location.search);
        URLSearch.set('page', page);
        const newParam = decodeURIComponent(URLSearch.toString());
        window.open(location.pathname + '?' + newParam, '_self');
    }
};

main.init();

