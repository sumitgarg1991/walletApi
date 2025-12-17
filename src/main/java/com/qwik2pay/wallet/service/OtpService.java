package com.qwik2pay.wallet.service;

import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qwik2pay.wallet.entity.UserOtp;
import com.qwik2pay.wallet.repository.UserOtpRepository;

@Service
public class OtpService {

	@Autowired
	private UserOtpRepository repo;

	public void generateOtp(String email, String mobile) {
		UserOtp otp = new UserOtp();
		otp.setEmail(email);
		otp.setMobile(mobile);
		otp.setOtp(String.valueOf(new Random().nextInt(900000) + 100000));
		otp.setExpiry(LocalDateTime.now().plusMinutes(5));
		repo.save(otp);

// Send Email + SMS here
	}

	public boolean validateOtp(String email, String otp) {
		return repo.findValidOtp(email, otp, LocalDateTime.now()).isPresent();
	}
}