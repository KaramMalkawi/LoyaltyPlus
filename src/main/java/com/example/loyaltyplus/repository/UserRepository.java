package com.example.loyaltyplus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.loyaltyplus.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u JOIN u.role r WHERE r.name = 'SHOPPER'")
	List<User> findAllShoppers();

	List<User> findByRoleId(Long roleId);
}
