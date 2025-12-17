package com.qwik2pay.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qwik2pay.wallet.entity.SignupRequest;
import com.qwik2pay.wallet.service.OtpService;
import com.qwik2pay.wallet.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class OtpController {

	@Autowired
	private OtpService otpService;
	@Autowired
	private UserService userService;

	@PostMapping("/send-otp")
	public void sendOtp(@RequestBody SignupRequest req) {
		otpService.generateOtp(req.getEmail(), req.getMobile());
	}

	@PostMapping("/verify-otp")
	public void verifyOtp(@RequestBody SignupRequest req) {
		if (otpService.validateOtp(req.getEmail(), req.getOtp())) {
			// userService.register(req);
		}
	}
}