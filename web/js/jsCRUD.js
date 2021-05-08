var form1 = $("#form1");
var formUpdate = $("#formUpdate");
var previewImg = "https://blackmantkd.com/wp-content/uploads/2017/04/default-image-620x600.jpg";
$(document).ready(function () {
    $("#btnCreate").click(function () {
        $("#modalCreate").modal('show');
    });

    $("#btnEdit").click(function () {
        $("#modalUpdate").modal('show');
    });

    $('#modalUpdate, #modalCreate').on('hidden.bs.modal', function () {
        $("#image_file_upd, #userid, #userid_upd, #trxid, #trxid_upd, #image_file").val("");
        $("#previewImgUpd, #previewImg").attr("src", previewImg);
    });

    $("#btnUpdate").click(function () {
        var fileControl = document.getElementById('image_file_upd');
        var fileName = $('#image_file_upd').val().split('-').pop(); // Nama File
        var pecah = fileName.split(".");
        var tipe = pecah[pecah.length - 1];
        if (!$("#userid_upd").val()) {
            swal("Form Validation", "Please input USER ID", "info");
            return false;
        } else if (!$("#trxid_upd").val()) {
            swal("Form Validation", "Please input TRX ID", "info");
            return false;
        } else if (fileName.length > 0) {
            if (tipe !== "jpg" && tipe !== "JPG" && tipe !== "png" && tipe !== "jpeg") {
                swal({
                    title: "Warning!",
                    text: "Valid type just image format ",
                    type: "warning",
                    confirmButtonColor: "#1ab394",
                    closeOnConfirm: true
                });
                return false;
            } else if (fileControl.files[0].size > 2097152) {
                swal({
                    title: "Warning!",
                    text: "Max size 2MB",
                    type: "warning",
                    confirmButtonColor: "#1ab394",
                    closeOnConfirm: true
                });
                return false;
            }
        }
        konfirmasiUpdate();

    });

    $.ajax({
        url: '/CRUDClient/Client/ListData',
        type: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        success: function (data, status) {
            destroyDT("restable");
            $(".sweet-alert button").removeAttr('disabled');
            $(".sweet-alert button.cancel").removeAttr('disabled');
            $('.sweet-alert button').css('cursor', 'pointer');
            if (data["status"] === 0) {
                var listData = data["listData"];
                for (var i = 0; i < listData.length; i++) {
                    var NO = listData[i].NO;
                    var USER_ID = listData[i].USER_ID;
                    var TRX_ID = listData[i].TRX_ID;
                    var IMAGE = listData[i].IMAGE;
                    var prep = "<tr>";
                    prep = prep +
                            "<td>" + NO + "</td>" +
                            "<td>" + USER_ID + "</td>" +
                            "<td>" + TRX_ID + "</td>" +
                            "<td><img src='../../CRUDClient/images/" + IMAGE + "\' style='width: 100px'></img></td>" +
                            "<td><a href='#' onClick='getDataByNO(\"" + String(NO) + "\")' class='btn btn-info' style='margin-right: 20px;'><i class='fa fa-edit'></i>&nbsp;Edit</a><a href='#' onClick='konfirmasiDelete(\"" + String(NO) + "\")' class = 'btn btn-danger'><i class='fa fa-trash'></i>&nbsp;Delete</a>" +
                            "</tr>";
                    $("#restable_content").append(prep);
                }
                try {
                    $('#restable').DataTable({
                        "autoWidth": false,
                        "info": true,
                        "JQueryUI": true,
                        "ordering": false,
                        "paging": true,
                        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
                        "scrollCollapse": true,
                        "dom": 'Blfrtip'
                    });
                } catch (err) {
                    swal("Warning!", err, "error");
                }
            } else {
                swal("Warning!", data["message"], "error");
            }

        },
        error: function (xhr, status, e) {
            swal("Warning!", "500 - Internal Server Error. " + status, "error");
        }
    });


    $("#btnSave").click(function () {
        var fileControl = document.getElementById('image_file');
        var fileName = $('#image_file').val().split('-').pop(); // Nama File
        var pecah = fileName.split(".");
        var tipe = pecah[pecah.length - 1];
        if (!$("#userid").val()) {
            swal("Form Validation", "Please input USER ID", "info");
            return false;
        } else if (!$("#trxid").val()) {
            swal("Form Validation", "Please input TRX ID", "info");
            return false;
        } else if (!$("#image_file").val()) {
            swal("Form Validation", "Please choose image", "info");
            return false;
        } else if (tipe !== "jpg" && tipe !== "JPG" && tipe !== "png") {
            swal({
                title: "",
                text: "Valid type just image format ",
                type: "warning",
                confirmButtonColor: "#1ab394",
                closeOnConfirm: true
            });
            return false;
        } else if (fileControl.files[0].size > 2097152) {
            swal({
                title: "",
                text: "Max size 2MB",
                type: "warning",
                confirmButtonColor: "#1ab394",
                closeOnConfirm: true
            });
            return false;
        } else {
            konfirmasiCreate();
        }
    });

});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#previewImg, #previewImgUpd')
                    .attr('src', e.target.result)
                    .width(200)
                    .height(150);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function konfirmasiCreate() {
    swal({
        title: 'Confirmation',
        text: "Are you sure?",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes'
    }).then((result) => {
        if (result) {
            CreateProcess();
        }
    });
}

function konfirmasiDelete(no) {
    swal({
        title: "Confirmation",
        html: "<center><p>Are you sure?</p></center>",
        type: "info",
        showCancelButton: true,
        confirmButtonColor: "#1ab394",
        cancelButtonColor: "ff0000",
        confirmButtonText: "Yes",
        cancelButtonText: "No",
        closeOnConfirm: true,
        closeOnCancel: true
    }).then((result) => {
        if (result) {
            DeleteProcess(no);
        }
    });

}

function konfirmasiUpdate() {
    swal({
        title: "Confirmation",
        html: "<center><p>Are you sure?</p></center>",
        type: "info",
        showCancelButton: true,
        confirmButtonColor: "#1ab394",
        cancelButtonColor: "ff0000",
        confirmButtonText: "Yes",
        cancelButtonText: "No",
        closeOnConfirm: true,
        closeOnCancel: true
    }).then((result) => {
        if (result) {
            UpdateProcess();
        }
    });

}

function DeleteProcess(no) {
    var jsonData = {"NO": no};
    $.ajax({
        url: '/CRUDClient/Client/DeleteData',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(jsonData),
        success: function (data, status, xhr) {
            var json = data;
            switch (json.statusDelete) {
                case '0':
                {
                    swal({
                        title: "Success",
                        text: json.message,
                        type: "success",
                        confirmButtonColor: "#1ab394",
                        closeOnConfirm: true
                    });
                    drawDataTable("success");
                    break;

                }
                case '1':
                {
                    swal({
                        title: "Failed",
                        text: json.message,
                        type: "error",
                        confirmButtonColor: "#1ab394",
                        closeOnConfirm: true
                    });
                    break;
                }
                default:
                {
                    break;
                }
            }
        },
        error: function (xhr, status, e) {
            swal({
                title: "Warning!",
                text: "500 - Internal Server Error. " + status,
                type: "error",
                confirmButtonColor: "#1ab394",
                closeOnConfirm: true
            });
        }
    });
}

function fillDTRestable(data) {
    destroyDT("restable");
    $(".sweet-alert button").removeAttr('disabled');
    $(".sweet-alert button.cancel").removeAttr('disabled');
    $('.sweet-alert button').css('cursor', 'pointer');
    if (data["status"] === 0) {
        var listData = data["listData"];
        for (var i = 0; i < listData.length; i++) {
            var NO = listData[i].NO;
            var USER_ID = listData[i].USER_ID;
            var TRX_ID = listData[i].TRX_ID;
            var IMAGE = listData[i].IMAGE;
            var prep = "<tr>";
            prep = prep +
                    "<td>" + NO + "</td>" +
                    "<td>" + USER_ID + "</td>" +
                    "<td>" + TRX_ID + "</td>" +
                    "<td><img src='../../CRUDClient/images/" + IMAGE + "\' style='width: 100px'></img></td>" +
                    "<td><a href='#' onClick='getDataByNO(\"" + String(NO) + "\")' class='btn btn-info' style='margin-right: 20px;'><i class='fa fa-edit'></i>&nbsp;Edit</a><a href='#' onClick='konfirmasiDelete(\"" + String(NO) + "\")' class = 'btn btn-danger'><i class='fa fa-trash'></i>&nbsp;Delete</a>" +
                    "</tr>";
            $("#restable_content").append(prep);
        }
        try {
            $('#restable').DataTable({
                "autoWidth": false,
                "info": true,
                "JQueryUI": true,
                "ordering": false,
                "paging": true,
                "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
                "scrollCollapse": true,
                "dom": 'Blfrtip'
            });
        } catch (err) {
            swal("Warning!", err, "error");
        }
    } else {
        swal("Warning!", data["message"], "error");
    }
}

function CreateProcess() {
    var fileControl = document.getElementById('image_file');
    var formData = new FormData();
    formData.append('user_id', $("#userid").val());
    formData.append('trx_id', $("#trxid").val());
    formData.append('namafile', fileControl.files[0]);

    $.ajax({
        url: '/CRUDClient/Client/CreateData',
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        type: 'POST',
        success: function (data) {
            switch (data.statusCreate) {
                case '0':
                {

                    swal({
                        title: "Success",
                        text: data.message,
                        type: "success",
                        confirmButtonColor: "#1ab394",
                        closeOnConfirm: true
                    });
                    drawDataTable("success");
                    $('#previewImg').attr("src", previewImg);
                    $("#modalCreate").modal('hide');
                    form1[0].reset();
                    break;
                }
                case '1':
                {
                    swal({
                        title: "Failed",
                        text: data.message,
                        type: "error",
                        confirmButtonColor: "#1ab394",
                        closeOnConfirm: true
                    });
                    form1[0].reset();
                    $("#modalCreate").modal('hide');
                    return false;
                }
                default:
                {
                    break;
                }
            }
        },
        error: function (msg) {
            swal("Error", "Something went wrong!", "error");
        }

    });
}

function drawDataTable(status, message) {
    if (String(status) === "success") {
        $.ajax({
            url: '/CRUDClient/Client/ListData',
            type: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            success: function (data, status) {
                destroyDT("restable");
                $(".sweet-alert button").removeAttr('disabled');
                $(".sweet-alert button.cancel").removeAttr('disabled');
                $('.sweet-alert button').css('cursor', 'pointer');
                if (data["status"] === 0) {
                    fillDTRestable(data);
                } else {
                    swal("Warning!", data["message"], "error");
                }

            },
            error: function (xhr, status, e) {
                swal("Warning!", "500 - Internal Server Error. " + status, "error");
            }
        });
    } else {
        swal("Warning!", "500 - Internal Server Error. " + message, "error");
    }
}
var currno;
function getDataByNO(no) {
    var jsonData = {"NO": no};
    $.ajax({
        url: '/CRUDClient/Client/ReadData',
        type: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        data: jsonData,
        success: function (data, status) {
            $(".sweet-alert button").removeAttr('disabled');
            $(".sweet-alert button.cancel").removeAttr('disabled');
            $('.sweet-alert button').css('cursor', 'pointer');
            if (data["status"] === 0) {
                try {
                    var currdata = data["current_data"];
                    showUpdateModal(currdata);
                } catch (err) {
                    swal("Warning!", err, "error");
                }
            } else {
                swal("Warning!", data["message"], "error");
            }

        },
        error: function (xhr, status, e) {
            swal("Warning!", "500 - Internal Server Error. " + status, "error");
        }
    });
}

function showUpdateModal(currdata) {
    setCurrentData(currdata);
    $("#modalUpdate").modal("show");
}

function setCurrentData(currdata) {
    $("#currno").val(currdata.NO);
    $("#userid_upd").val(currdata.USER_ID);
    $("#trxid_upd").val(currdata.TRX_ID);
}



function UpdateProcess() {
    var fileControl = document.getElementById('image_file_upd');
    var fileName = $('#image_file_upd').val().split('-').pop();
    var formData = new FormData();
    formData.append('no', $("#currno").val());
    formData.append('user_id', $("#userid_upd").val());
    formData.append('trx_id', $("#trxid_upd").val());
    var urlCon;
    if (fileName) {
        formData.append('namafile', fileControl.files[0]);
        urlCon = '/CRUDClient/Client/UpdateDataWithImage';
    } else {
        urlCon = '/CRUDClient/Client/UpdateData';
    }
    $.ajax({
        url: urlCon,
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        type: 'POST',
        success: function (data) {
            switch (data.statusUpd) {
                case '0':
                {
                    swal({
                        title: "Success",
                        text: data.message,
                        type: "success",
                        confirmButtonColor: "#1ab394",
                        closeOnConfirm: true
                    });
                    drawDataTable("success");
                    $("#modalUpdate").modal('hide');
                    formUpdate[0].reset();
                    $("#previewImgUpd").attr("src", previewImg);
                    break;
                }
                case '1':
                {
                    swal({
                        title: "Failed",
                        text: data.message,
                        type: "error",
                        confirmButtonColor: "#1ab394",
                        closeOnConfirm: true
                    });
                    formUpdate[0].reset();
                    $("#modalUpdate").modal('hide');
                    return false;
                }
                default:
                {
                    break;
                }
            }
        },
        error: function (msg) {
            swal("Error", "Something went wrong!", "error");
        }

    });
    $("#modalUpdate").modal('hide');
}

function destroyDT(nameId) {
    nameSelector = "#" + nameId;
    if ($.fn.DataTable.isDataTable(nameSelector)) {
        $(nameSelector).DataTable().clear().destroy();
    }
}