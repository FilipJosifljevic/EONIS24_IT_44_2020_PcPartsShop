package erp.pcpartsbackend.services;

import erp.pcpartsbackend.models.Admin;
import erp.pcpartsbackend.repositories.AdminRepository;
import erp.pcpartsbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    private UserRepository userRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}
