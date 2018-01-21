/**
 * Created by hdfg159 on 17-7-31.
 */


// Common
function pageFormSubmit() {
    $('#submit_page').val($('#input_page').val() - 1);
    return true;
}

function selectAllCheck(name, isCheck) {
    var allCheckBoxes = $("input[name='" + name + "']:checkbox");
    if (isCheck === "true") {
        allCheckBoxes.prop("checked", true);
    } else {
        allCheckBoxes.prop("checked", false);
    }
}

function reserveAllCheck(name) {
    $("input[name='" + name + "']:checkbox").each(function () {
        $(this).prop("checked", !$(this).prop("checked"));
    });
}

function timeStampToString(time) {
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1;
    var date = datetime.getDate();
    var hour = datetime.getHours();
    var minute = datetime.getMinutes();
    var second = datetime.getSeconds();
    var mseconds = datetime.getMilliseconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}