package com.qwik2pay.wallet.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.qwik2pay.wallet.entity.UserOtp;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {

	@Query("SELECT u FROM UserOtp u WHERE u.email = :email AND u.otp = :otp AND u.expiry > :now")
	Optional<UserOtp> findValidOtp(@Param("email") String email, @Param("otp") String otp, @Param("now") LocalDateTime now);

	Optional<UserOtp> findTopByEmailOrderByExpiryDesc(String email);

	void deleteByEmail(String email);
}