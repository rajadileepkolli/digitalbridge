package com.digitalbridge.mongodb.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.AuthorityUtils;

import com.digitalbridge.DigitalBridgeApplicationTests;
import com.digitalbridge.domain.User;
import com.digitalbridge.exception.DigitalBridgeException;
import com.digitalbridge.security.SecurityUtils;
import com.digitalbridge.util.KeyGeneratorUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTests extends DigitalBridgeApplicationTests {

	@Test
	public void createUsers() throws DigitalBridgeException {
		User user = new User("appUser", KeyGeneratorUtil.encrypt("appPassword"),
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		User adminUser = new User("appAdmin", KeyGeneratorUtil.encrypt("appPassword"),
				AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
		User superAdmin = new User("admin", KeyGeneratorUtil.encrypt("secret"),
				AuthorityUtils.createAuthorityList("ROLE_SUPERUSER"));
		List<User> userList = new ArrayList<>();
		userList.add(user);
		userList.add(adminUser);
		userList.add(superAdmin);
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		userRepository.deleteAll();
		userRepository.saveAll(userList);
		assertThat(userRepository.count()).isEqualTo(3);
		User user1 = userRepository.findByUserName("admin");
		assertThat(user1.getUserName()).isEqualTo("admin");
		assertThat(user1).isNotNull();
	}

}
