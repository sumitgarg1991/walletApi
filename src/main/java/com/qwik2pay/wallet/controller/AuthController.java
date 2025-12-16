package com.qwik2pay.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qwik2pay.wallet.entity.LoginRequest;
import com.qwik2pay.wallet.entity.User;
import com.qwik2pay.wallet.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		User saved = userService.register(user);
// send email with link: /verify?token=...
		return ResponseEntity.ok("Registration successful. Verify your email.");
	}

	@GetMapping("/verify")
	public ResponseEntity<?> verify(@RequestParam String token) {
		boolean verified = userService.verifyEmail(token);
		return verified ? ResponseEntity.ok("Email verified") : ResponseEntity.badRequest().body("Invalid token");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req) {
		return userService.login(req.getEmail(), req.getPassword()).map(u -> ResponseEntity.ok("Login success")).orElse(
				ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or email not verified"));
	}
}