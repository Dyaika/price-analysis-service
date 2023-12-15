package me.dyaika.marketplace.repositories;
import me.dyaika.marketplace.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {

    Admin findByUsername(String username);

}
