package com.librarie.auth_api;

import com.librarie.auth_api.entities.*;
import com.librarie.auth_api.repository.PermissionRepository;
import com.librarie.auth_api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class AuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApiApplication.class, args);
	}

	@Component
	public class DataInitializer implements CommandLineRunner {

		@Autowired
		private RoleRepository roleRepository;

		@Autowired
		private PermissionRepository permissionRepository;

		@Override
		public void run(String... args) {
			// Créer les permissions
			for (EnumPermission enumPermission : EnumPermission.values()) {
				Permission permission = new Permission();
				permission.setName(enumPermission);
				permissionRepository.save(permission);
			}

			// Récupérer les permissions
			Permission readPermission = permissionRepository.findByName(EnumPermission.READ).orElseThrow();
			Permission createPermission = permissionRepository.findByName(EnumPermission.CREATE).orElseThrow();
			Permission updatePermission = permissionRepository.findByName(EnumPermission.UPDATE).orElseThrow();
			Permission deletePermission = permissionRepository.findByName(EnumPermission.DELETE).orElseThrow();

			// Créer les rôles
			Role userRole = new Role();
			userRole.setName(EnumRole.USER);
			userRole.setPermissions(Set.of(readPermission));

			Role adminRole = new Role();
			adminRole.setName(EnumRole.ADMIN);
			adminRole.setPermissions(Set.of(readPermission, createPermission, updatePermission, deletePermission));

			Role managerRole = new Role();
			managerRole.setName(EnumRole.MANAGER);
			managerRole.setPermissions(Set.of(readPermission, updatePermission));

			roleRepository.saveAll(List.of(userRole, adminRole, managerRole));
		}
	}
}
