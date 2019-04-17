package com.egeinfo.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.egeinfo.pojo.Users;
import com.egeinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	// 登录验证
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		// System.out.println("username is " + username);
		// 这里应该可以不用再查了
		Users temp = new Users();
		temp.setUserName(userName);
		// SysUser sysUser = sysUserService.findOneByProperties(temp);
		Users user = (Users) userService.selectOne("UserMap.findByProperties", temp);

		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);

		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		// 封装成spring security的user
		User userdetail = new User(user.getUserName(),user.getPasswd(), enables, accountNonExpired,
				credentialsNonExpired, accountNonLocked, grantedAuths);
		return userdetail;
	}

	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(Users user) {
		String userName = user.getUserName();
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		return authSet;
	}
}