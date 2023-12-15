package me.dyaika.marketplace.services;
import me.dyaika.marketplace.entities.Admin;
import me.dyaika.marketplace.repositories.AdminRepository;
import me.dyaika.marketplace.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin saveAdmin(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encodePassword(password));
        return adminRepository.save(admin);
    }
    public Admin authenticate(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);

        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            return admin;
        }

        return null;
    }
    public void removeAdmin(String username){
        adminRepository.deleteById(username);
    }
}

