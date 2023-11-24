package org.healthylifestyle.user.service.impl;

import java.util.List;

import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.repository.UserRepository;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public int countAllByIdIn(List<Long> ids) {
		return userRepository.countAllByIdIn(ids);
	}

	@Override
	public List<User> findAllByIds(List<Long> ids) {
		return userRepository.findAllByIds(ids);
	}

}
