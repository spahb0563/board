const main = {
    init: function () {
        const _this = this;

        $('.page-link').on('click', function () {
            _this.movePage($(this).val());
        })
    },

    movePage : function(page) {
        const URLSearch = new URLSearchParams(location.search);
        URLSearch.set('page', page);
        const newParam = decodeURIComponent(URLSearch.toString());
        window.open(location.pathname + '?' + newParam, '_self');
    }
};

main.init();

