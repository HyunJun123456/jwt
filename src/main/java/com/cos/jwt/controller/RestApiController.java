package com.cos.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;
// @CrossOrigin
@RestController
public class RestApiController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/home")
	public String home() {
		return "<h1>home</h1>";
	}

	@PostMapping("/token")
	public String token() {
		return "<h1>token</h1>";
	}

	@PostMapping("/join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()) );
		user.setRoles("ROLE_USER"); // ROLE_USER로 설정
		userRepository.save(user);
		return "회원가입완료";
	}

	// user, manager, admin 권한만 접근 가능
	@GetMapping("/api/v1/user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String user(Authentication authentication) {
		System.out.println("/api/v1/user");
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication : "+principal.getUsername());
		return "user";
	}
	// manager, admin 권한만 접근 가능
	@GetMapping("/api/v1/manager")
	public String manager() {
		return "manager";
	}
	// admin 권한만 접근 가능
	@GetMapping("/api/v1/admin")
	public String admin() {
		return "admin";
	}


}
