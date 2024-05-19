package com.test.board.auth;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.board.entity.Member;
import com.test.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return memberRepository.findByUserId(username)
			.map(user -> User.builder()
				.username(user.getUserId())
				.password(user.getPassword())
				.credentialsExpired(
					Duration.between(user.getCreatedAt(), LocalDateTime.now()).toDays() >= 90
				)
				.build())
			.orElseThrow(() -> new UsernameNotFoundException(username));
	}
}
