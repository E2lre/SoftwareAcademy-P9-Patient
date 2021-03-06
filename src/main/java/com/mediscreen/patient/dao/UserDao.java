package com.mediscreen.patient.dao;



import com.mediscreen.patient.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername (String userName);
    boolean existsByUsername(String username);
}
