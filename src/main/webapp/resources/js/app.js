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
    if (month < 10) {
        month = '0' + month;
    }
    var date = datetime.getDate();
    if (date < 10) {
        date = '0' + date;
    }
    var hour = datetime.getHours();
    if (hour < 10) {
        hour = '0' + hour;
    }
    var minute = datetime.getMinutes();
    if (minute < 10) {
        minute = '0' + minute;
    }
    var second = datetime.getSeconds();
    if (second < 10) {
        second = '0' + second;
    }
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}

function getObjectURL(file) {
    return window.URL.createObjectURL(file);
}

function tranferStringLine(content) {
    var str = content;
    str = str.replace(/\r\n/g, '<br/>');
    str = str.replace(/\n/g, '<br/>');
    return str;
}

$(document).ready(function () {
    $('.convert-emoji').each(function () {
        var original = $(this).html();
        var converted = emojione.toImage(original);
        $(this).html(converted);
    });
});

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};