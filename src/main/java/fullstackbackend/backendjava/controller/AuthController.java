package fullstackbackend.backendjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fullstackbackend.backendjava.service.UserService;
import fullstackbackend.backendjava.entity.User;
import fullstackbackend.backendjava.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil; // Deklarasi jwtUtil

    // Endpoint untuk registrasi
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        Map<String, String> response = new HashMap<>();

        if (registeredUser != null) {
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response); // Mengembalikan respons JSON
        } else {
            response.put("message", "Registration failed");
            return ResponseEntity.badRequest().body(response); // Mengembalikan respons JSON
        }
    }



    // Endpoint untuk login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        User authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            String token = jwtUtil.generateToken(authenticatedUser.getUsername());
            long exp = jwtUtil.getExpirationTimeInMinutes(); // Ambil waktu expired dalam menit
            Map<String, Object> response = new HashMap<>();
            response.put("exp", exp);
            response.put("token", token);
            return ResponseEntity.ok(response); // Kirim JWT dan waktu expired ke client
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
    }
}
