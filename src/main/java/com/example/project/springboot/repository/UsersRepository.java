package com.example.project.springboot.repository;

import com.example.project.springboot.dao.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

    public Users findByUserName(String userName);
}
