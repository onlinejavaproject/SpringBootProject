package com.example.SpringBoot.RepositaryService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SpringBoot.Entities.User;

public interface UserRepositary extends JpaRepository<User, Integer> {

	@Query("from User as u where u.email =:e")
	public User getUserByEmail(@Param("e") String email);

	@Query("from User as u where u.email =:e and u.password =:p")
	public User getByEmailAndPassword(@Param("e") String email, @Param("p") String password);

	@Query("from User as u where u.name =:name")
	public User getUserByName(@Param("name") String name);
}