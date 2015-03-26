package com.cyberway.core.acegi.service;

import java.util.List;

import org.acegisecurity.userdetails.User;

import com.cyberway.core.acegi.resource.Resource;


/**
 * 为缓存提供 User 和 Resource 实例
 * @author cac
 *
 */
public interface AuthenticationService {
	
	public List<User> getUsers();
	
	public List<Resource> getResources();
}
