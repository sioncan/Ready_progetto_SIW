package com.museo.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.museo.siw.spring.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);
}