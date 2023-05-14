package com.example.main.repository.log;

import com.example.main.entity.log.Login2Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Login2RoleRepository extends JpaRepository<Login2Role,String> {
}