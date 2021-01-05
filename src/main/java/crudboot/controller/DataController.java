package crudboot.controller;

import crudboot.model.Role;
import crudboot.model.User;
import crudboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.List;

@Controller
public class DataController {

     final private UserService userService;

     @Autowired
     public DataController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/admin/data/users/{id}")
    public String getUserData(@PathVariable String id) {
        if (id.equalsIgnoreCase("all")) {
            return getAllUsersJson();
        } else {
            JsonArrayBuilder jsonRolesArray = Json.createArrayBuilder();

            User user = userService.getUserById(Long.parseLong(id));

            for (Role role : user.getRoles()) {
                JsonObjectBuilder jsonRolesData = Json.createObjectBuilder()
                        .add("id", role.getId())
                        .add("role", role.getRole());
                jsonRolesArray.add(jsonRolesData);
            }

            String json = Json.createObjectBuilder()
                    .add("id", user.getId())
                    .add("firstName", user.getFirstName())
                    .add("lastName", user.getLastName())
                    .add("age", user.getAge())
                    .add("email", user.getEmail())
                    .add("roles", jsonRolesArray)
                    .build()
                    .toString();

            return json;
        }
    }

    @ResponseBody
    @GetMapping("/admin/data/allusers")
    public String getUsersData() {
        return getAllUsersJson();
    }

    private String getAllUsersJson() {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        List<User> usersList = userService.getUsersList();

        for (User user : usersList) {
            JsonArrayBuilder jsonRolesArray = Json.createArrayBuilder();

            for (Role role : user.getRoles()) {
                JsonObjectBuilder jsonRolesData = Json.createObjectBuilder()
                        .add("id", role.getId())
                        .add("role", role.getRole());
                jsonRolesArray.add(jsonRolesData);
            }

            JsonObject jsonUserData = Json.createObjectBuilder()
                    .add("id", user.getId())
                    .add("firstName", user.getFirstName())
                    .add("lastName", user.getLastName())
                    .add("age", user.getAge())
                    .add("email", user.getEmail())
                    .add("roles", jsonRolesArray)
                    .build();

            jsonArray.add(jsonUserData);
        }

        return jsonArray.build().toString();
    }
}
