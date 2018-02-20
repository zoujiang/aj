!function(e, t, o) {
    "use strict"; !
    function() {
        o("#exampleTableEvents").bootstrapTable({
            url: getProjectName() + "/resources/hplus4.1/js/demo/bootstrap_table_test.json",
            search: !0,
            pagination: !0,
            showRefresh: !0,
            showToggle: !0,
            showColumns: !0,
            iconSize: "outline",
            //toolbar: "#exampleTableEventsToolbar",
            icons: {
                refresh: "glyphicon-repeat",
                toggle: "glyphicon-list-alt",
                columns: "glyphicon-list"
            }
        });
        var e = o("#examplebtTableEventsResult");
        o("#exampleTableEvents").on("all.bs.table",
        function(e, t, o) {
            console.log("Event:", t, ", data:", o)
        }).on("click-row.bs.table",
        function() {
            e.text("Event:click-row.bs.table")
        }).on("dbl-click-row.bs.table",
        function() {
            e.text("Event:dbl-click-row.bs.table")
        }).on("sort.bs.table",
        function() {
            e.text("Event:sort.bs.table")
        }).on("check.bs.table",
        function() {
            e.text("Event:check.bs.table")
        }).on("uncheck.bs.table",
        function() {
            e.text("Event:uncheck.bs.table")
        }).on("check-all.bs.table",
        function() {
            e.text("Event:check-all.bs.table")
        }).on("uncheck-all.bs.table",
        function() {
            e.text("Event:uncheck-all.bs.table")
        }).on("load-success.bs.table",
        function() {
            e.text("Event:load-success.bs.table")
        }).on("load-error.bs.table",
        function() {
            e.text("Event:load-error.bs.table")
        }).on("column-switch.bs.table",
        function() {
            e.text("Event:column-switch.bs.table")
        }).on("page-change.bs.table",
        function() {
            e.text("Event:page-change.bs.table")
        }).on("search.bs.table",
        function() {
            e.text("Event:search.bs.table")
        })
    } ()
} (document, window, jQuery);