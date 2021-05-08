<div class="modal fade" id="modalUpdate" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Update this Form</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="formUpdate">
                    <input id="currno" type="text" class="form-control" hidden>
                    <div class="mb-3">
                        <label class="form-label">USER ID</label>
                        <input type="text" class="form-control" id="userid_upd">
                    </div>
                    <div class="mb-3">
                        <label  class="form-label">TRX ID</label>
                        <input type="text" class="form-control" id="trxid_upd">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Default file input example</label>
                        <input accept="image/*" class="form-control" type="file" id="image_file_upd" onchange="readURL(this);">
                        <p style="margin-top: 10px">Image Preview</p>
                        <img id="previewImgUpd" src="https://blackmantkd.com/wp-content/uploads/2017/04/default-image-620x600.jpg" style="width: 150px;"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="btnUpdate" type="button" class="btn btn-primary"><i class="fa fa-save"></i> &nbsp; Update</button>
                <button type="button" class="btn btn-warning" data-bs-dismiss="modal"><i class="fa fa-times"></i> &nbsp; Close</button>
            </div>
        </div>
    </div>
</div>