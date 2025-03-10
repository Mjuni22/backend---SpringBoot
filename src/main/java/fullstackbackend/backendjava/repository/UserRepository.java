package fullstackbackend.backendjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fullstackbackend.backendjava.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

