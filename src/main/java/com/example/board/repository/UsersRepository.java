package com.example.board.repository;

import com.example.board.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

}
