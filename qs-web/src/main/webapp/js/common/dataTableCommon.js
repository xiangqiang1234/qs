/**
 * @author Toning
 * @Description: dataTable列表插件相关方法
 * @date 2019/3/21
 */
var dataTableListnumber = [20, 50, -1];
var dataTableListnumbers = [20, 50, "All"];
var dataTableLengthMenu = [dataTableListnumber, dataTableListnumbers];
var dataTable_lang_zh = {
    "sLengthMenu": "每页显示 _MENU_ 条记录",
    "sZeroRecords": "抱歉， 没有找到数据",
    "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
    "sInfoEmpty": "",
    "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
    "sFilter": "过滤",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "前一页",
        "sNext": "后一页",
        "sLast": "尾页"
    }
};

/**
 * 头部全部选择的复选框
 * @param {Object} tableEleId table元素id
 */
function initDatatableMultiCheckbox(tableEleId) {
    var $tr = $("#" + tableEleId + " thead tr");
    var $firstThead = $tr.find("th:eq(0)");
    if (!$firstThead) {
        $firstThead = $tr.find("td:eq(0)");
    }
    $firstThead.html("<input type=\"checkbox\" value=\"all\"/>");
    $firstThead.find("input[type='checkbox']").on("click", "", function () {
        if ($(this).prop("checked")) {
            setAllCheckBox(tableEleId, true);
        } else {
            setAllCheckBox(tableEleId, false);
        }
    });
}

/**
 * 分页查询后 刷新头部全部选择的复选框
 * @param {Object} tableEleId table元素id
 */
function refreshDatatableMultiCheckbox(tableEleId) {
    var $checkbox = $("#" + tableEleId + " thead tr").find("th:eq(0)").children("input[type='checkbox']");
    $checkbox.prop("checked", false);
}

/**
 *设置datatable全部复选框
 * @param {Object} tableEleId table Id
 * @param {Object} $checked 选中状态
 */
function setAllCheckBox(tableEleId, $checked) {
    $("#" + tableEleId + " tbody tr").each(function () {
        var $checkbox = $(this).find("td:eq(0)").children("input[type='checkbox']");
        $checkbox.prop("checked", $checked);
    });
}

/**
 *如果数据为空的处理
 * @param {Object} expr1传入数据
 * @param {Object} expr2 替换数据，默认为[空]
 */
function ifDataNull(expr1, expr2) {
    var defaultData = '[空]';
    if (!isNullOrEmpty(expr2)) {
        defaultData = expr2;
    }
    if (isNullOrEmpty(expr1)) {
        expr1 = defaultData;
    }
    return expr1;
}

/**
 *获取选择datatable的项的id
 * @param {Object} tablename
 * @return ids
 */
function getDataTableSelect(tablename) {
    var ids = [];
    $("#" + tablename + " tbody tr").each(function () {
        var $checkbox = $(this).find("input:checkbox:checked");
        $.each($checkbox, function () {
            ids.push($checkbox.val());
        })
    });
    return ids;
}

/**
 *获取选择datatable的项的id
 * @param {Object} tablename
 * @return ids
 */
function getDataTableSelectByRadio(tablename) {
    var ids = [];
    $("#" + tablename + " tbody tr").each(function () {
        var $checkbox = $(this).find("input:radio:checked");
        $.each($checkbox, function () {
            ids.push($checkbox.val());
        })
    });
    return ids;
}
