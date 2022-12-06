package com.spring.dictionary.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dictionary.actors.Role;
import com.spring.dictionary.actors.User;
import com.spring.dictionary.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final UserService userService;


    @Operation(
            tags={"User management operations"},
            summary = "GET request to retrieve all registered users",
            responses = {@ApiResponse(responseCode = "200", content = @Content( examples = {@ExampleObject(name = "Example", value =
                    "[\n" +
                            "    {\n" +
                            "        \"id\": 3,\n" +
                            "        \"firstName\": \"John\",\n" +
                            "        \"lastName\": \"Smith\",\n" +
                            "        \"username\": \"john\",\n" +
                            "        \"password\": \"$2a$10$fzJ7.AIatpwbummB7L8NxO7tWmeJmfahIif91qqeCsqcPWlFSoCUq\",\n" +
                            "        \"roles\": [\n" +
                            "            {\n" +
                            "                \"id\": 1,\n" +
                            "                \"name\": \"ROLE_ADMIN\"\n" +
                            "            }\n" +
                            "        ]\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": 4,\n" +
                            "        \"firstName\": \"Maggie\",\n" +
                            "        \"lastName\": \"Stone\",\n" +
                            "        \"username\": \"magforever\",\n" +
                            "        \"password\": \"$2a$10$hUqz.16egeP8baOXRVcy/OwbdhzNcodZiTZCPjFyR.hLlIlecAV66\",\n" +
                            "        \"roles\": [\n" +
                            "            {\n" +
                            "                \"id\": 2,\n" +
                            "                \"name\": \"ROLE_USER\"\n" +
                            "            }\n" +
                            "        ]\n" +
                            "    }]")},
                    mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
         return ResponseEntity.ok().body(userService.getAllUsers());
     }

     @Operation(
             tags={"User management operations"},
             summary = "GET request to retrieve a user info by a specified username",
             description = "Uses path variable that contains a username",
             responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class),
                     mediaType = MediaType.APPLICATION_JSON_VALUE,
                     examples = {@ExampleObject(name = "Examle", value = "{\n" +
                             "        \"id\": 4,\n" +
                             "        \"firstName\": \"Maggie\",\n" +
                             "        \"lastName\": \"Stone\",\n" +
                             "        \"username\": \"magforever\",\n" +
                             "        \"password\": \"$2a$10$hUqz.16egeP8baOXRVcy/OwbdhzNcodZiTZCPjFyR.hLlIlecAV66\",\n" +
                             "        \"roles\": [\n" +
                             "            {\n" +
                             "                \"id\": 2,\n" +
                             "                \"name\": \"ROLE_USER\"\n" +
                             "            }\n" +
                             "        ]\n" +
                             "    }")}))},
             security = {@SecurityRequirement(name = "BearerJWT")}
     )
    @GetMapping("/{username}")
    public ResponseEntity<User> saveRole(@PathVariable("username") String username) {
        URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
        return ResponseEntity.created(uri).body(userService.getUser(username));
    }

    @Operation(
            tags={"User management operations"},
            summary = "POST request to create a new user role",
            description = "Uses request body with structure of the Role class.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = Role.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(name = "Example", value = "{ \n" +
                            "    \"id\":null,\n" +
                            "    \"name\": \"ROLE_ANONYMOUS\"\n" +
                            "}")})),
            responses = {@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = User.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(name = "Examle", value = "{\n" +
                            "    \"id\": 14,\n" +
                            "    \"name\": \"ROLE_ANONYMOUS\"\n" +
                            "}")}))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }



    @Operation(
            tags={"User management operations"},
            summary = "PUT request to add a role to a user with a particular username",
            description = "Uses two path variables to define the user and a role that will be added to the user' roles.\nA single role can have three values:" +
                    " ROLE_ADMIN, ROLE_USER, ROLE_ANONYMOUS.",
            parameters = {@Parameter(name = "username", required = true, example = "foxigun"), @Parameter(name = "roleName", required = true, example = "ROLE_USER")},
            responses = {@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = User.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(name = "Examle", value = "{\n" +
                            "    \"id\": 13,\n" +
                            "    \"firstName\": \"Megan\",\n" +
                            "    \"lastName\": \"Fox\",\n" +
                            "    \"username\": \"foxigun\",\n" +
                            "    \"password\": \"$2a$10$z7z6MaZMyGOcUBR.VtPJ.OSW3Ts.X9VTZHyiCpZqBgne07GyzDhAS\",\n" +
                            "    \"roles\": [\n" +
                            "        {\n" +
                            "            \"id\": 2,\n" +
                            "            \"name\": \"ROLE_USER\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}")}))},
            security = {@SecurityRequirement(name = "BearerJWT")}

    )
    @PutMapping("/add/role/{username}/{roleName}")
    public ResponseEntity<User> saveRole(@PathVariable("username") String username, @PathVariable("roleName") String roleName) {
        URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add/role").toUriString());
        userService.addRoleToUser(username, roleName);
        return ResponseEntity.created(uri).body(userService.getUser(username));
    }


    @Operation(
            tags={"User management operations"},
            summary = "POST request to create a new user",
            description = "Uses request body with structure of the User class.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = User.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(name = "Example", value = "{ \n" +
                            "    \"id\":null,\n" +
                            "    \"firstName\":\"Megan\",    \n" +
                            "    \"lastName\":\"Fox\",\n" +
                            "    \"username\":\"foxigun\",\n" +
                            "    \"password\":\"1111\",\n" +
                            "    \"roles\": []\n" +
                            "}")})),
            responses = {@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = User.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(name = "Examle", value = "{\n" +
                            "    \"id\": 13,\n" +
                            "    \"firstName\": \"Megan\",\n" +
                            "    \"lastName\": \"Fox\",\n" +
                            "    \"username\": \"foxigun\",\n" +
                            "    \"password\": \"$2a$10$z7z6MaZMyGOcUBR.VtPJ.OSW3Ts.X9VTZHyiCpZqBgne07GyzDhAS\",\n" +
                            "    \"roles\": []\n" +
                            "}")}))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }


    @Operation(
            tags={"User management operations"},
            summary = "DELETE request to delete a user with a particular username",
            description = "Uses path variable to define which user will be deleted",
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @DeleteMapping("/user/delete/{username}")
    public void deleteUser(HttpServletResponse response,  @PathVariable("username") String username) {
        Map<String, String> res = new HashMap<>();
        try{
            userService.deleteUserByUsername(username);
            response.setStatus(ACCEPTED.value());
            res.put("status", "200");
            res.put("message", "Successfully deleted user " + username);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), res);
        }
        catch (Exception e) {
            response.setStatus(NOT_FOUND.value());
            res.put("status", "404");
            res.put("message", "User " + username + " not found");
            response.setContentType("application/json");
            try {
                new ObjectMapper().writeValue(response.getOutputStream(), res);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Operation(
            tags={"User management operations"},
            summary = "GET request to refresh user's access token using Refresh-token",
            description = "Request must contain a header \"Authorization\" containing Refresh-token starting with \"Bearer \"",
            parameters = {@Parameter(name = "Authorization", in = ParameterIn.HEADER)},
            responses = {@ApiResponse(responseCode = "202", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String refreshToken = authorizationHeader.substring("Bearer ".length());
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 180 * 60 * 1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }
            catch (Exception e){
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid token");
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @Operation(
            tags={"User Pagination and Filtering"},
            summary = "GET request to get a list of users with a particular size and page number",
            description = "Path variable offset means a number of page starting from 0, pageSize means number of elements.",
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Page.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/users/pagination/{offset}/{pageSize}")
    public Page<User> findUsersWithPagination(@PathVariable("offset") Integer offset, @PathVariable("pageSize") Integer pageSize) {
        return userService.findUsersWithPagination(offset,pageSize);
    }

    @Operation(
            tags={"User Pagination and Filtering"},
            summary = "GET request to get a filtered list of users by a particular field.",
            description = "Path variable fieled means a property a filtering will be done by.",
            responses = {@ApiResponse(responseCode = "200", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/users/filter/{field}")
    public List<User> findUsersWithFiltering(@PathVariable String field) {
        return userService.findUsersWithFiltering(field);
    }

    @Operation(
            tags={"User Pagination and Filtering"},
            summary = "GET request to get a filtered list of users by a particular field with pagination.",
            description = "Path variable offset means a number of page starting from 0, pageSize means number of elements, field means a property a filtering will be done by.",
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Page.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/users/pagination/filter/{offset}/{pageSize}/{field}")
    public Page<User> findUsersWithPaginationAndFiltering(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String field) {
        return userService.findUsersWithPaginationAndFiltering(offset, pageSize, field);
    }

}

