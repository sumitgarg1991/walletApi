package com.qwik2pay.wallet.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.qwik2pay.wallet.entity.User;
import com.qwik2pay.wallet.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User register(User user) {
		user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
		user.setEmailVerified("N");
		user.setVerificationToken(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	public boolean verifyEmail(String token) {
		Optional<User> userOpt = userRepository.findByVerificationToken(token);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setEmailVerified("Y");
			user.setVerificationToken(null);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public Optional<User> login(String email, String password) {
		Optional<User> userOpt = userRepository.findByEmail(email);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if ("Y".equals(user.getEmailVerified()) && passwordEncoder.matches(password, user.getPasswordHash())) {
				return userOpt;
			}
		}
		return Optional.empty();
	}
}