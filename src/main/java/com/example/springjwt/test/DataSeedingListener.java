package com.example.springjwt.test;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.springjwt.entities.Role;
import com.example.springjwt.entities.TestObj;
import com.example.springjwt.entities.User;
import com.example.springjwt.repository.RoleRepository;
import com.example.springjwt.repository.UserRepository;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
//    	entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
//    	entityManager.createNativeQuery("ALTER TABLE role AUTO_INCREMENT = 1").executeUpdate();
//    	if ((roleRepository.findByName("ROLE_ADMIN") != null) || (roleRepository.findByName("ROLE_ADMIN") != null)) {
//    		roleRepository.deleteAll();
//    	}
//    	 if ((userRepository.findByUsername("admin@gmail.com") != null)||(userRepository.findByUsername("member@gmail.com") != null)) {
//    		 userRepository.deleteAll();
//    	 }
    	if(roleRepository.findAll() != null) {
    		roleRepository.deleteAllInBatch();
    	}
    	if(userRepository.findAll() != null) {
    		userRepository.deleteAllInBatch();
    	}
    	

        // Roles
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        if (roleRepository.findByName("ROLE_USER") == null) {
            roleRepository.save(new Role("ROLE_USER"));
        }

        // Admin account
        if (userRepository.findByUsername("admin@gmail.com") == null) {
            User admin = new User();
            admin.setUsername("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
            roles.add(roleRepository.findByName("ROLE_USER"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // Member account
        if (userRepository.findByUsername("member@gmail.com") == null) {
            User user = new User();
            user.setUsername("member@gmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_USER"));
            user.setRoles(roles);
            userRepository.save(user);
        }
		ApplicationContext context = new ClassPathXmlApplicationContext(
                "beanTest.xml");
         
        // tạo đối tượng objA
        TestObj objA = (TestObj) context.getBean("TestObj");
        //objA.setName("Toi la test day ");
        objA.test();
//        System.out.println(objA.getName());
    }

}
