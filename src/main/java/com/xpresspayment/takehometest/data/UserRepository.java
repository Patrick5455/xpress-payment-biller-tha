package com.xpresspayment.takehometest.data;

import java.util.Optional;

import javax.transaction.Transactional;

import com.xpresspayment.takehometest.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String username);

    Optional<UserEntity> findByUuid(String uuid);


    void deleteByEmail(String email);

    void deleteByUuid(String uuid);







}
