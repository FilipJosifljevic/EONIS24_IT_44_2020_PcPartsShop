package erp.pcpartsbackend.controllers;

import erp.pcpartsbackend.models.Admin;
import erp.pcpartsbackend.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @GetMapping("admins")
    public ResponseEntity<?> getAlladmins() {
        List<Admin> admins = adminService.getAllAdmins();
        if (admins.isEmpty()) {
            return new ResponseEntity<>(
                    "Admins not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("admins/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable("id") UUID adminId) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin == null) {
            return new ResponseEntity<>(
                    "Admin not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PostMapping("admins")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin) {
        if(adminService.existById(admin.getUserId())){
            return new ResponseEntity<>(
                    "Admin with that id already exists",
                    HttpStatus.CONFLICT);
        }
        Admin savedadmin = adminService.addAdmin(admin);
        return ResponseEntity.status(HttpStatus.OK).body(savedadmin);
    }

    @PutMapping("admins/{id}")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin, @PathVariable("id") UUID adminId) {
        admin.setUserId(adminId);
        if(!adminService.existById(admin.getUserId())){
            return new ResponseEntity<>(
                    "Admin with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        Admin savedadmin = adminService.addAdmin(admin);
        return ResponseEntity.status(HttpStatus.OK).body(savedadmin);
    }
}
