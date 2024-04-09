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

    public Admin getAdminById(UUID adminId) {
        return adminRepository.findByUserId(adminId);
    }

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    public boolean existById(UUID adminId){
        return getAdminById(adminId) != null;
    }
}
