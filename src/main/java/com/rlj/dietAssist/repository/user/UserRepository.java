package com.rlj.dietAssist.repository.user;

import com.rlj.dietAssist.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

}
