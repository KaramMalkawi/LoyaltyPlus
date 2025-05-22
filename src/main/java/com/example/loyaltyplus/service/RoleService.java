package com.example.loyaltyplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loyaltyplus.model.entity.Role;
import com.example.loyaltyplus.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public Role getRoleById(Long id) {
		return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));
	}

	public Role createRole(Role role) {
		Role newRole = new Role();
		newRole.setName(role.getName());
		return roleRepository.save(role);
	}

	public Role updateRole(Long id, Role updatedRole) {
		Role existingRole = roleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Role not found"));
		existingRole.setName(updatedRole.getName());
		return roleRepository.save(existingRole);
	}

	public void deleteRole(Long id) {
		roleRepository.deleteById(id);
	}

}
