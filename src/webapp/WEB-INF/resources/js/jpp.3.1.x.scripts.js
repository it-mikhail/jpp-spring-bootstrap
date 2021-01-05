function showUsersTable() {
  $("#users_table").show();
  $("#new_user_form").hide();
  $("#nav_tab_users_table").addClass("active");
  $("#nav_tab_new_user_form").removeClass("active");
}

function showNewUserForm() {
  $("#users_table").hide();
  $("#new_user_form").show();
  $("#nav_tab_users_table").removeClass("active");
  $("#nav_tab_new_user_form").addClass("active");
}

function fillForm(action, id) {
  $.getJSON("/admin/data/users/" + id, function(data) {
    $("#" + action + "_id").val(data.id);
    $("#" + action + "_first_name").val(data.firstName);
    $("#" + action + "_last_name").val(data.lastName);
    $("#" + action + "_age").val(data.age);
    $("#" + action + "_email").val(data.email);

    $.each(data.roles, function(key, val) {
      // $("select#" + action + "_roles option[value="+val.id+"]").attr("selected","selected");
      $("select#" + action + "_roles option[value="+val.id+"]").prop("selected", true);
    });
  
  });
}

function clearForm(action) {
  $("select option").prop("selected", false);
}
