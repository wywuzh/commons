/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 扩展
* jeasyui.extensions.datagrid.columnToggle.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2016-01-27
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.menu.js
*   3、jeasyui.extensions.datagrid.getColumnInfo.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.datagrid.extensions");


    function isMultiLineHeaders(target) {
        var state = $.data(target, "datagrid"),
            opts = state.options;
        if (opts.columns && opts.columns.length > 1 && opts.columns[0].length && opts.columns[1].length) {
            return true;
        }
        if (opts.frozenColumns && opts.frozenColumns.length > 1 && opts.frozenColumns[0].length && opts.frozenColumns[1].length) {
            return true;
        }
        return false;
    }

    var getColumnData = function (target, field, withFormatter) {
        var t = $(target), rows = t.datagrid("getRows");
        if (withFormatter === false) {
            return $.array.map(rows, function (val) { return val[field]; });
        } else {
            var colsOptions = t.datagrid("getColumnOptions", "true"), theColOpts = $.array.first(colsOptions, function (item) { return item.field == field; });
            if(!theColOpts){
                return [];
            }
            return $.isFunction(theColOpts.formatter) ? $.array.map(rows, function (row) { return theColOpts.formatter.call(target, row[field], row, t.datagrid("getRowIndex", row)); }) : $.array.map(rows, function (row) { return row[field]; });
        }
    };

    var getDistinctColumnData = function (target, field, withFormatter) {
        var t = $(target), data = getColumnData(target, field, withFormatter);
        return $.array.distinct(data);
    };

    var getRowDom = function (target, index) {
        if (!$.isNumeric(index) || index < 0) { return $(); }
        var t = $(target), panel = t.datagrid("getPanel");
        return panel.find("div.datagrid-view div.datagrid-body table tr.datagrid-row[datagrid-row-index=" + index + "]");
    };

    function findRow(target, param, t, opts, rows) {
        t = t && t.length ? t : $(target);
        opts = opts ? opts : $.data(target, "datagrid").options;
        rows = rows && rows.length ? rows : t.datagrid("getRows");
        var type = $.type(param);
        if (type == "function") {
            return $.array.first(rows, function (row, index) {
                return param.call(target, row, index, rows);
            });
        } else if (type == "object") {
            return opts.idField && (opts.idField in param)
                ? findRowById(param[opts.idField])
                : $.array.first(rows, function (val) { return val == param; });
        } else {
            return findRowById(param);
        }
        function findRowById(id) {
            return $.array.first(rows, function (row) {
                return row[opts.idField] == id;
            });
        }
    }

    function findRows(target, param) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options,
            rows = t.datagrid("getRows");
        if ($.isFunction(param)) {
            return $.array.filter(rows, function (val, index) {
                return param.call(target, val, index, rows);
            });
        } else if ($.array.likeArrayNotString(param)) {
            var array = $.array.map(param, function (val) {
                return findRow(target, val, t, opts, rows);
            });
            return $.array.filter(array, function (val) {
                return val != null && val != undefined;
            });
        } else {
            return [findRow(target, param, t, opts, rows)];
        }
    }

    //  param: idField | row | function (row, index, rows)
    function showRow(target, param) {
        var state = $.data(target, "datagrid"),
            t = $(target),
            opts = state.options,
            rows = t.datagrid("getRows");

        var row = findRow(target, param),
            index = t.datagrid("getRowIndex", row);
        if (index > -1) {
            $.array.remove(state.hiddenRows, row);
            getRowDom(target, index).removeClass("datagrid-row-hidden");
        }
    }

    //  param: idField | row | function (row, index, rows)
    function hideRow(target, param) {
        var state = $.data(target, "datagrid"),
            t = $(target),
            opts = state.options,
            rows = t.datagrid("getRows");

        var row = findRow(target, param),
            index = t.datagrid("getRowIndex", row);
        if (index > -1) {
            $.array.attach(state.hiddenRows, row);
            t.datagrid("unselectRow", index).datagrid("uncheckRow", index);
            getRowDom(target, index).addClass("datagrid-row-hidden");
        }
    }

    //  param: function (row, index, rows) | array (idField | row | function (row, index, rows)) | boolean (false)
    function hideRows(target, param) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;
        if (param == true) {
            var rows = t.datagrid("getRows");
            $.array.clear(state.hiddenRows);
            $.array.copy(state.hiddenRows, rows);
            $.each(rows, function (i, n) {
                var rowIndex = t.datagrid("getRowIndex", n);
                if (rowIndex > -1) {
                    getRowDom(target, rowIndex).addClass("datagrid-row-hidden");
                }
            });
        } else {
            var array = findRows(target, param);
            if (array.length) {
                $.each(array, function (i, n) {
                    hideRow(target, n);
                });
            }
        }
    }

    //  param: function (index, row, rows) | array (idField | row | function (index, row, rows)) | boolean (true)
    function showRows(target, param) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;
        if (param == true) {
            var rows = t.datagrid("getRows");
            $.array.clear(state.hiddenRows);
            $.each(rows, function (i, n) {
                var rowIndex = t.datagrid("getRowIndex", n);
                if (rowIndex > -1) {
                    getRowDom(target, rowIndex).removeClass("datagrid-row-hidden");
                }
            });
        } else {
            var array = findRows(target, param);
            if (array.length) {
                $.each(array, function (i, n) {
                    showRow(target, n);
                });
            }
        }
    }



    function getHeaderMenuItems(t, opts, e, field) {
        var menuItems = [],
            defaultMenuItems = [],
            args = [t[0], field];

        $.array.merge(defaultMenuItems, defaultMenuItems.length ? "-" : [], defaultMenus.sortMenus);
        $.array.merge(defaultMenuItems, defaultMenuItems.length ? "-" : [], defaultMenus.fieldToggleMenus);
        $.array.merge(defaultMenuItems, defaultMenuItems.length ? "-" : [], defaultMenus.rowToggleMenus);

        if ($.array.likeArrayNotString(opts.headerContextMenu)) {
            $.array.merge(menuItems, menuItems.length ? "-" : [], opts.headerContextMenu);
        }
        if (defaultMenuItems.length) {
            $.array.merge(menuItems, menuItems.length ? "-" : [], defaultMenuItems);
        }
        return $.easyui.parseMenuItems(menuItems, args);
    }

    function initHeaderCellClickMenu(t, opts, tdDom) {
        var td = $(tdDom),
            cell = td.find("div.datagrid-cell"),
            arrow = $("<span class='s-btn-downarrow datagrid-header-cell-arrow'>&nbsp;</span>").prependTo(cell).click(function (e) {
                $.easyui.hideAllMenu();
                var s = $(this),
                    offset = s.offset(),
                    height = s.outerHeight(),
                    field = s.closest("td[field]").attr("field"),
                    menuItems = getHeaderMenuItems(t, opts, e, field),
                    mm = $.easyui.showMenu({
                        items: menuItems,
                        left: offset.left,
                        top: offset.top + height
                    }),
                    mopts = mm.menu("options"),
                    onHide = mopts.onHide;
                arrow.hidden = false;
                mopts.onHide = function () {
                    arrow.hidden = true;
                    arrow.removeClass("datagrid-header-cell-arrow-show");
                    onHide.apply(this, arguments);
                };
                e.stopPropagation();
            });
        td.unbind(".arrow-hover").bind({
            "mouseenter.arrow-hover": function () {
                arrow.addClass("datagrid-header-cell-arrow-show");
            },
            "mouseleave.arrow-hover": function () {
                if (arrow.hidden == null || arrow.hidden == undefined || arrow.hidden) {
                    arrow.removeClass("datagrid-header-cell-arrow-show");
                }
            }
        });
    }

    function getHeaderFields(t) {
        var tds = t.datagrid("getPanel").find("div.datagrid-view div.datagrid-header table.datagrid-htable tr.datagrid-header-row td[field]");
        return $.array.filter(tds, function (item) {
            var td = $(item),
                colspan = td.attr("colspan"),
                field = td.attr("field"),
                copts = t.datagrid("getColumnOption", field);
            return (!colspan || colspan == "1") && !td.is(".datagrid-cell-rownumber") && copts && !copts.checkbox ? true : false;
        });
    }

    var defaultMenus = {};

    // 排序菜单
    defaultMenus.sortMenus = [
        {
            text: "升序", iconCls: "icon-standard-hmenu-asc",
            disabled: function (e, menuItem, menu, target, field) {
                var t = $(target),
                    copts = t.datagrid("getColumnOption", field);
                return copts.sortable && (copts.colspan == null || copts.colspan == undefined || copts.colspan == 1) ? false : true;
            },
            handler: function (e, menuItem, menu, target, field) {
                return $(target).datagrid("sort", { sortName: field, sortOrder: "asc" });
            }
        },
        {
            text: "降序", iconCls: "icon-standard-hmenu-desc",
            disabled: function (e, menuItem, menu, target, field) {
                var t = $(target),
                    copts = t.datagrid("getColumnOption", field);
                return copts.sortable && (copts.colspan == null || copts.colspan == undefined || copts.colspan == 1) ? false : true;
            },
            handler: function (e, menuItem, menu, target, field) {
                return $(target).datagrid("sort", {
                    sortName: field, sortOrder: "desc"
                });
            }
        }
    ];

    // 列显隐菜单
    defaultMenus.fieldToggleMenus = [
        {
            text: "显示/隐藏列", iconCls: "icon-standard-application-view-columns",
            disabled: false,
            children: function (e, menuItem, menu, target, field) {
                var t = $(target),
                    mm = $(menu),
                    menuItems = [],
                    topMenus = getFieldTopMenus(t, mm),
                    fieldMenus = getFieldToggleMenus(t, mm);
                if (topMenus.length) {
                    $.array.merge(menuItems, menuItems.length ? "-" : [], topMenus);
                }
                if (fieldMenus.length) {
                    $.array.merge(menuItems, menuItems.length ? "-" : [], fieldMenus);
                }
                return menuItems;
            }
        }
    ];
    // 列显隐菜单中的置顶菜单
    function getFieldTopMenus(t, mm) {
        var target = t[0],
            state = $.data(target, "datagrid"),
            cols = $.array.filter(t.datagrid("getColumnOptions", false), function (col) {
                return col.field ? true : false;
            }),
            originalCols = state.originalColumnOptions;
        return [
            {
                text: "显示全部列", hideOnClick: false,
                iconCls: function () {
                    var len = cols.length,
                        count = $.array.sum(cols, function (col) {
                            return col.hidden ? 0 : 1;
                        });
                    return count >= len ? "tree-checkbox1" : (count == 0 ? "tree-checkbox0" : "tree-checkbox2");
                },
                handler: function () {
                    $.each(cols, function (i, col) { t.datagrid("showColumn", col.field); });
                    $(this).parent().children(".menu-item:not(:eq(1))").each(function () {
                        var item = mm.menu("getItem", this);
                        if (item.disabled) { return; }
                        mm.menu("setIcon", { target: this, iconCls: "tree-checkbox1" })
                            .menu("enableItem", this);
                    });
                }
            },
            {
                text: "还原默认", iconCls: "icon-standard-application-view-tile", hideOnClick: false,
                handler: function () {
                    $.each(originalCols, function (i, col) {
                        t.datagrid(col.hidden ? "hideColumn" : "showColumn", col.field);
                    });
                    var mp = $(this).parent();
                    mp.children("div.menu-item:gt(1)").each(function () {
                        var title = $(this).text(),
                            col = $.array.first(originalCols, function (val) {
                                return val.title == title;
                            });
                        var item = mm.menu("getItem", this);
                        if (item.disabled) { return; }
                        if (col) {
                            mm.menu("setIcon", {
                                target: this,
                                iconCls: col.hidden ? "tree-checkbox0" : "tree-checkbox1"
                            });
                        }
                        mm.menu("enableItem", this);
                    });
                    mp.children("div.menu-item:first").each(function () {
                        var len = cols.length,
                            count = $.array.sum(cols, function (col) {
                                return col.hidden ? 0 : 1;
                            });
                        mm.menu("setIcon", {
                            target: this,
                            iconCls: count >= len ? "tree-checkbox1" : (count == 0 ? "tree-checkbox0" : "tree-checkbox2")
                        });
                    });
                }
            }
        ];
    }
    // 列显隐菜单中的显隐菜单
    function getFieldToggleMenus(t, mm) {
        var target = t[0],
            cols = $.array.filter(t.datagrid("getColumnOptions", false), function (col) {
                return col.field ? true : false;
            });
        return $.array.map(cols, function (col) {
            return {
                text: col.title || col.field,
                iconCls: col.hidden ? "tree-checkbox0" : "tree-checkbox1",
                hideOnClick: false,
                disabled: !col.hidable ? true : false,
                handler: function () {
                    var mi = $(this),
                        mp = mi.parent(),
                        mip = mp.find(".menu-item:gt(1)"),
                        mis = mip.find(".tree-checkbox1"),
                        copts = t.datagrid("getColumnOption", col.field);
                    if (!col.hidable) {
                        return;
                    }
                    t.datagrid(copts.hidden ? "showColumn" : "hideColumn", col.field);
                    mm.menu("setIcon", {
                        target: this,
                        iconCls: copts.hidden ? "tree-checkbox0" : "tree-checkbox1"
                    });
                    var length = cols.length,
                        count = $.array.sum(cols, function (col) {
                            return col.hidden ? 0 : 1;
                        });
                    mm.menu("setIcon", {
                        target: mi.parent().children("div.menu-item:first"),
                        iconCls: count >= length ? "tree-checkbox1" : (count == 0 ? "tree-checkbox0" : "tree-checkbox2")
                    });

                    var mms = $.array.filter(mip, function () {
                        return $(".tree-checkbox1", this).length ? true : false;
                    });

                    // 为了确保“至少有一个列显示”，当判定显示的列仅剩1个时禁用对该列的隐藏菜单
                    // 但因此对菜单项的隐藏，要与 列不可隐藏 导致的隐藏进行区分
                    // 找到所有 不可隐藏 的列信息，并按照菜单项的文本信息格式（<span>项名</span>）组成数组
                    var hidableColTitles = $.array.map($.array.filter(cols, function (itemCol) { return !itemCol.hidable; }), function (itemCol) { return "<span>" + t.datagrid("getColumnOption", itemCol.field).title + "</span>"; });
                    $.array.forEach(mms, function (crt) {
                        var item = mm.menu('getItem', crt);
                        if (!$.array.contains(hidableColTitles, item.text)) {
                            mm.menu(mms.length > 1 ? "enableItem" : "disableItem", crt);
                        }
                    });
                }
            };
        });
    }

    // 行显隐菜单
    defaultMenus.rowToggleMenus = [
        {
            text: "过滤/显示", iconCls: "icon-standard-application-view-list",
            disabled: function (e, menuItem, menu, target, field) {
                var t = $(target),
                    copts = t.datagrid("getColumnOption", field);
                return copts.filterable && (copts.colspan == null || copts.colspan == undefined || copts.colspan == 1) ? false : true;
            },
            children: function (e, menuItem, menu, target, field) {
                var t = $(target),
                    rows = t.datagrid("getRows"),
                    colOpts = t.datagrid("getColumnOption", field);
                fieldValues = getDistinctColumnData(target, field, !colOpts.ignoreFormatterWhenFilter),
                    mm = $(menu),
                    menuItems = [],
                    topMenus = getRowTopMenus(t, mm, rows, fieldValues),
                    rowMenus = getRowToggleMenus(t, field, mm, rows, fieldValues, e);
                if (topMenus.length) {
                    $.array.merge(menuItems, menuItems.length ? "-" : [], topMenus);
                }
                if (rowMenus.length) {
                    $.array.merge(menuItems, menuItems.length ? "-" : [], rowMenus);
                }
                return menuItems;
            }
        }
    ];
    // 行显隐菜单中的置顶菜单
    function getRowTopMenus(t, mm, rows, fieldValues) {
        var target = t[0],
            state = $.data(target, "datagrid");
        return [
            {
                text: "全部", hideOnClick: false,
                iconCls: function () {
                    return !state.hiddenRows.length ? "tree-checkbox1" : (state.hiddenRows.length >= rows.length ? "tree-checkbox0" : "tree-checkbox2");
                },
                handler: function () {
                    if (state.hiddenRows.length) {
                        showRows(target, true);
                    } else {
                        hideRows(target, true);
                    }
                    $(this).parent().children("div.menu-item[hideOnClick=false]").each(function () {
                        mm.menu("setIcon", {
                            target: this,
                            iconCls: state.hiddenRows.length ? "tree-checkbox0" : "tree-checkbox1"
                        });
                    });
                }
            }
        ];
    }
    // 行显隐菜单中的显隐菜单
    function getRowToggleMenus(t, field, mm, rows, fieldValues, e) {
        var target = t[0],
            state = $.data(target, "datagrid"),
            opts = state.options,
            hasMore = fieldValues.length > opts.headerFilterMenuLength,
            array = hasMore ? $.array.left(fieldValues, opts.headerFilterMenuLength) : fieldValues,
            menuItems = $.array.map(array, function (val) {
                var hrows = $.array.filter(rows, function (row) { return row[field] == val; });
                return {
                    text: val, hideOnClick: false,
                    iconCls: function () {
                        var hcells = $.array.filter(state.hiddenRows, function (row) { return row[field] == val; });
                        return !hcells.length
                            ? "tree-checkbox1"
                            : (hcells.length >= hrows.length ? "tree-checkbox0" : "tree-checkbox2");
                    },
                    handler: function () {
                        var hcells = $.array.filter(state.hiddenRows, function (row) { return row[field] == val; });
                        hcells.length ? showRows(target, hrows) : hideRows(target, hrows);
                        mm.menu("setIcon", {
                            target: this,
                            iconCls: hcells.length ? "tree-checkbox1" : "tree-checkbox0"
                        });
                        $(this).parent().children("div.menu-item:first").each(function () {
                            mm.menu("setIcon", {
                                target: this,
                                iconCls: (!state.hiddenRows.length)
                                    ? "tree-checkbox1"
                                    : (state.hiddenRows.length >= rows.length ? "tree-checkbox0" : "tree-checkbox2")
                            });
                        });
                    }
                };
            });
        if (hasMore) {
            $.array.merge(menuItems, menuItems.length ? "-" : [], {
                text: "处理更多(共" + fieldValues.length + "项)...",
                iconCls: "icon-standard-application-view-detail",
                handler: function () {
                    showFilterDialog(target, field, rows, fieldValues);
                }
            });
        }
        return menuItems;
    }
    // 完整的列数据过滤窗口
    function showFilterDialog(target, field, rows, fieldValues) {
        if (!field) {
            return;
        }
        var t = $(target);
        rows = rows && rows.length ? rows : t.datagrid("getRows");
        fieldValues = fieldValues && fieldValues.length ? fieldValues : getDistinctColumnData(target, field, true);

        var state = $.data(target, "datagrid"),
            copts = t.datagrid("getColumnOption", field),
            title = copts.title || copts.field;

        var d = $("<div />").appendTo($("body")).dialog({
            title: "过滤/显示",
            iconCls: "icon-standard-application-view-detail",
            height: 300,
            width: 220,
            modal: true,
            resizable: true,
            toolbar: [
                    {
                        text: "全部选择", iconCls: "icon-standard-accept",
                        handler: function () {
                            showRows(target, true);
                            d.dialog("body").find(":checkbox").each(function () { this.checked = true; });
                        }
                    },
                    {
                        text: "全部不选", iconCls: "icon-standard-cancel",
                        handler: function () {
                            hideRows(target, true);
                            d.dialog("body").find(":checkbox").each(function () { this.checked = false; });
                        }
                    }
            ]
        }),
        body = d.dialog("body"),
        l = $("<div>"
                + "<div class=\"datagrid-filters-dialog-layout-north\" data-options=\"region: 'north', split: false, border: false\" >列：" + title + "，共" + fieldValues.length + "项</div>"
                + "<div class=\"datagrid-filters-dialog-layout-center\" data-options=\"region: 'center', border: false\" ></div>"
                + "</div>").appendTo(body).layout({
                    fit: true
                }),
        b = l.layout("panel", "center"),
        ul = $("<ul></ul>").appendTo(b);

        $.each(fieldValues, function (i, text) {
            var id = "item_ck_" + $.util.guid("N"),
                checked = !$.array.some(state.hiddenRows, function (val) {
                    return val[field] == text;
                }),
                li = $("<li></li>").appendTo(ul),
                ck = $("<input />").attr({
                    type: "checkbox",
                    id: id,
                    checked: checked
                }).appendTo(li),
                label = $("<label></label>").attr("for", id).text(text).appendTo(li);
            ck.click(function () {
                var hrows = $.array.filter(rows, function (row) { return row[field] == text; }),
                    hcells = $.array.filter(state.hiddenRows, function (row) { return row[field] == text; });
                hcells.length ? showRows(target, hrows) : hideRows(target, hrows);
            });
        });
    }




    // 初始化列 options 扩展属性
    // 缓存有 title 值的非冻结列 options
    function initExtendColumnOptions(t, opts) {
        var target = t[0],
            state = $.data(target, "datagrid"),
            cols = t.datagrid("getColumnOptions", "all");
        $.each(cols, function (i, col) {
            $.union(col, $.fn.datagrid.extensions.columnOptions);
        });
        var columnOptions = t.datagrid("getColumnOptions", false);
        state.columnOptions = $.array.filter(columnOptions, function (col) {
            return col.title ? true : false;
        });
        state.originalColumnOptions = $.array.map(state.columnOptions, function (col) {
            return $.extend({}, col);
        });
        state.multiLineHeaders = isMultiLineHeaders(target);
    }

    // 初始化表头过滤数据
    function initHeaderFiltersData(t, opts) {
        var target = t[0],
            state = $.data(target, "datagrid");
        state.hiddenRows = [];
    }

    // 隐藏所有 menu 面板
    function initDisposeEvent(t, opts) {
        t.datagrid("getPanel").panel("body").undelegate(".datagrid-extensions").delegate("tr.datagrid-header-row, tr.datagrid-row", "click.datagrid-extensions", function () {
            $.easyui.hideAllMenu();
        });
    }

    // 初始化表头右键菜单
    function initHeaderContextMenu(t, opts) {
        t.datagrid("getPanel").panel("body").delegate("tr.datagrid-header-row>td[field]", "contextmenu.datagrid-extensions", function (e) {
            var td = $(this),
                field = td.attr("field");
            if (!field) {
                return;
            }
            if (opts.enableHeaderContextMenu) {
                e.preventDefault();
                var menuItems = getHeaderMenuItems(t, opts, e, field);
                $.easyui.showMenu({ items: menuItems, left: e.pageX, top: e.pageY, event: e });
            }
        });
    }

    // 初始化表头点击菜单
    function initHeaderClickMenu(t, opts) {
        if (opts.enableHeaderClickMenu) {
            $.each(getHeaderFields(t), function (index, item) {
                initHeaderCellClickMenu(t, opts, item);
            });
        }
    }


    function initializeExtensions(target) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;

        initExtendColumnOptions(t, opts);
        initHeaderFiltersData(t, opts);
        initDisposeEvent(t, opts);
        initHeaderContextMenu(t, opts);
        initHeaderClickMenu(t, opts);
    }


    var _datagrid = $.fn.datagrid;
    $.fn.datagrid = function (options, param) {
        if (typeof options == "string") {
            return _datagrid.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var jq = $(this),
                isInited = $.data(this, "datagrid") ? true : false,
                opts = isInited ? options : $.extend({},
                        $.fn.datagrid.parseOptions(this),
                        $.parser.parseOptions(this, [
                            {
                                headerFilterMenuLength: "number",
                            },
                            {
                                enableHeaderContextMenu: "boolean",
                                enableHeaderClickMenu: "boolean",
                                autoHighlightColumn: "boolean"
                            }
                        ]), options);
            _datagrid.call(jq, opts, param);
            if (!isInited) {
                initializeExtensions(this);
            }
        });
    };
    $.union($.fn.datagrid, _datagrid);


    var columnOptions = {

        // 表示该列是否可隐藏，其值可以是 Boolean 类型；
        // 默认为 true。
        // 该属性用于在“列显隐”时判定
        hidable: true,

        // 表示该列是否可过滤，其值可以是 Boolean 类型；
        // 默认为 true。
        // 该属性用于在“列过滤”时判定
        filterable: true,

        // 表示该列在 filterable 为 true 显示列数据时是否忽略 formatter 的存在，其值可以是 Boolean 类型；
        // 默认为 false
        // 该属性用于在“列过滤显示数据时”时判定
        ignoreFormatterWhenFilter: false
    };
    if ($.fn.datagrid.extensions.columnOptions) {
        $.extend($.fn.datagrid.extensions.columnOptions, columnOptions);
    }
    else {
        $.fn.datagrid.extensions.columnOptions = columnOptions;
    }


    var defaults = {

        //  扩展 easyui-datagrid 的自定义属性，该属性表示是否启用 easyui-datagrid 的表头右键菜单；
        //  Boolean 类型值，默认为 true。
        enableHeaderContextMenu: true,

        //  扩展 easyui-datagrid 的自定义属性，该属性表示是否启用 easyui-datagrid 的表头列点击按钮菜单；
        //  Boolean 类型值，默认为 true。 
        enableHeaderClickMenu: true,

        //  扩展 easyui-datagrid 的自定义属性，该属性表示表列头右键菜单，为一个 Array 对象；数组中的每一个元素都具有如下属性:
        //      id:         表示菜单项的 id；
        //      text:       表示菜单项的显示文本；
        //      iconCls:    表示菜单项的左侧显示图标；
        //      disabled:   表示菜单项是否被禁用(禁用的菜单项点击无效)；
        //      hideOnClick:    表示该菜单项点击后整个右键菜单是否立即自动隐藏；
        //      bold:           Boolean 类型值，默认为 false；表示该菜单项是否字体加粗；
        //      style:          JSON-Object 类型值，默认为 null；表示要附加到该菜单项的样式；
        //      handler:    表示菜单项的点击事件，该事件函数格式为 function(e, item, menu, grid, field)，其中 this 指向菜单项本身
        //  备注：具体格式参考 easyui-datagrid 的 toolbar 属性为 Array 对象类型的格式；
        headerContextMenu: null,

        //  扩展 easyui-datagrid 的自定义属性，该属性表示表列头过滤菜单中显示的数据行数的个数，超过该个数的其他数据行将不显示；
        //  Number 类型值，默认为 10 。
        headerFilterMenuLength: 10
    };

    var methods = {


    };

    if ($.fn.datagrid.extensions.defaults) {
        $.extend($.fn.datagrid.extensions.defaults, defaults);
    } else {
        $.fn.datagrid.extensions.defaults = defaults;
    }

    if ($.fn.datagrid.extensions.methods) {
        $.extend($.fn.datagrid.extensions.methods, methods);
    } else {
        $.fn.datagrid.extensions.methods = methods;
    }

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);