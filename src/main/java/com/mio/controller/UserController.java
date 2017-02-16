package com.mio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mio.ecache.MySession;
import com.mio.redis.User;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	@RequestMapping(value = "/test/{sessionId}", method = RequestMethod.GET)
	public MySession testSession(@PathVariable String sessionId) {
		Cache cache = cacheManager.getCache("sessions");
		Element element = cache.get(sessionId);
		MySession o =(MySession) element.getObjectValue();
		return o;
	}
	
	@RequestMapping(value = "/adduser/{userName}", method = RequestMethod.GET)
	public User addUser(@PathVariable String userName){
		User user = new User(userName, 30);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		return redisTemplate.opsForValue().get(userName);
	}
	
	@RequestMapping(value = "/getuser/{userName}", method = RequestMethod.GET)
	public User getUser(@PathVariable String userName){
		return redisTemplate.opsForValue().get(userName);
	}

	@RequestMapping(value = "/deleteuser/{userName}", method = RequestMethod.GET)
	public void deleteUser(@PathVariable String userName){
		redisTemplate.delete(userName);
	}
}
