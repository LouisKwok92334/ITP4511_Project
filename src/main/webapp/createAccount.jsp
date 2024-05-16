<%-- 
    Document   : createAccount
    Created on : 2024年5月16日, 下午11:46:14
    Author     : boscochuen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <jsp:include page="header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Management</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h1>Account Management</h1>
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Phone Number</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="accountTableBody">
                    <%-- Account rows will be populated by AJAX --%>
                </tbody>
            </table>
        </div>

        <!-- Edit Modal -->
        <div class="modal" id="editModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Account</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="editForm">
                            <input type="hidden" id="editUserId" name="user_id">
                            <div class="form-group">
                                <label for="editUsername">Username</label>
                                <input type="text" class="form-control" id="editUsername" name="username" required>
                            </div>
                            <div class="form-group">
                                <label for="editFirstName">First Name</label>
                                <input type="text" class="form-control" id="editFirstName" name="first_name">
                            </div>
                            <div class="form-group">
                                <label for="editLastName">Last Name</label>
                                <input type="text" class="form-control" id="editLastName" name="last_name">
                            </div>
                            <div class="form-group">
                                <label for="editEmail">Email</label>
                                <input type="email" class="form-control" id="editEmail" name="email">
                            </div>
                            <div class="form-group">
                                <label for="editPhoneNumber">Phone Number</label>
                                <input type="text" class="form-control" id="editPhoneNumber" name="phone_number">
                            </div>
                            <div class="form-group">
                                <label for="editRole">Role</label>
                                <select class="form-control" id="editRole" name="role" required>
                                    <option value="user">User</option>
                                    <option value="staff">Staff</option>
                                    <option value="technician">Technician</option>
                                    <option value="admin">Admin</option>
                                    <option value="courier">Courier</option>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="saveChangesButton">Save changes</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function() {
                // Fetch and display accounts
                $.ajax({
                    url: 'AccountServlet?action=list',
                    method: 'GET',
                    dataType: 'json',
                    success: function(data) {
                        var accountTableBody = $('#accountTableBody');
                        accountTableBody.empty();
                        $.each(data, function(index, account) {
                            accountTableBody.append(
                                '<tr>' +
                                '<td>' + account.user_id + '</td>' +
                                '<td>' + account.username + '</td>' +
                                '<td>' + account.first_name + '</td>' +
                                '<td>' + account.last_name + '</td>' +
                                '<td>' + account.email + '</td>' +
                                '<td>' + account.phone_number + '</td>' +
                                '<td>' + account.role + '</td>' +
                                '<td><button class="btn btn-primary editButton" data-id="' + account.user_id + '">Edit</button></td>' +
                                '</tr>'
                            );
                        });
                    }
                });

                // Handle edit button click
                $(document).on('click', '.editButton', function() {
                    var userId = $(this).data('id');
                    $.ajax({
                        url: 'AccountServlet?action=get&user_id=' + userId,
                        method: 'GET',
                        dataType: 'json',
                        success: function(account) {
                            $('#editUserId').val(account.user_id);
                            $('#editUsername').val(account.username);
                            $('#editFirstName').val(account.first_name);
                            $('#editLastName').val(account.last_name);
                            $('#editEmail').val(account.email);
                            $('#editPhoneNumber').val(account.phone_number);
                            $('#editRole').val(account.role);
                            $('#editModal').modal('show');
                        }
                    });
                });

                // Handle save changes button click
                $('#saveChangesButton').click(function() {
                    $.ajax({
                        url: 'AccountServlet?action=update',
                        method: 'POST',
                        data: $('#editForm').serialize(),
                        success: function(response) {
                            $('#editModal').modal('hide');
                            location.reload(); // Reload the page to reflect the changes
                        }
                    });
                });
            });
        </script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>
