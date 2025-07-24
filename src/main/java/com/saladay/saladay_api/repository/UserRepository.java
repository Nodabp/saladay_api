package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
}
