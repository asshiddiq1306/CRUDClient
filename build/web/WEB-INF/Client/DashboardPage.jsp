<%-- 
    Document   : CRUDClient
    Created on : May 07, 2021, 1:11:36 PM
    Author     : Asshiddiq
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://use.fontawesome.com/e6a0c38be0.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.6.9/sweetalert2.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap5.min.css"></link>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">

        <!-- Bootstrap CSS -->

        <title>Dashboard!</title>
    </head>
    <body class="mx-auto" style="margin-top:50px">
        <div class="container body-content">
            <h1 class="text-center mb-3">CRUD MVC</h1>

            <div class="card border-primary mb-3" style="max-width: 100rem;">
                <div class="card-header">List of User</div>
                <button id="btnCreate" type="button" class="btn btn-primary" style="max-width: 10rem; margin-top: 30px; margin-left: 20px">
                    <i class="fa fa-plus"></i>&nbsp; Add New Data
                </button>
                <div class="card-body text-primary">
                    <table id="restable" class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th scope="col">No</th>
                                <th scope="col">USER ID</th>
                                <th scope="col">TRX ID</th>
                                <th scope="col">IMAGE</th>
                                <th scope="col">ACTION</th>
                            </tr>
                        </thead>
                        <tbody id="restable_content">   

                        </tbody>

                    </table>
                </div>
            </div>
            <footer>
                <p>CRUD Spring MVC Asshiddiq</p>
            </footer>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.6.9/sweetalert2.min.js"></script>
        <script type="text/javascript" language="javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap5.min.js"></script>
        <script src="../../CRUDClient/js/jsCRUD.js" type="text/javascript"></script>
    </body>
</html>
<%@ include file = "ModalCreate.jsp" %>
<%@ include file = "ModalUpdate.jsp" %>



