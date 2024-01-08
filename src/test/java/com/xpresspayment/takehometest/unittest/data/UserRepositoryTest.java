package com.xpresspayment.takehometest.unittest.data;

import java.sql.Timestamp;

import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.utils.GlobalUtils;
import com.xpresspayment.takehometest.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
class UserRepositoryTest extends AbstractTest {

    @BeforeAll
    static void beforeAll() {
        log.info("before all");

        userEntity1 = UserEntity.builder()
                .firstname("John")
                .lastname("Doe")
                .email("johndoe@gmail.com")
                .uuid(GlobalUtils.generateUuid())
                .password("password")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();

        userEntity2 = UserEntity.builder()
                .firstname("Jane")
                .lastname("Doe")
                .uuid(GlobalUtils.generateUuid())
                .email("janedoe@gmail.com")
                .password("password")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    void testUserRepository(){
        assertNotNull(userRepository, "user repository should not be null");
    }

    @Test
    @DisplayName("test user repository is empty")
    void testUserRepositoryIsEmpty(){
        assertTrue(userRepository.findAll().isEmpty(), "user repository should be empty");
    }

    @Test
    @DisplayName("test user repository save")
    void testUserRepositorySave(){
         userEntity1 = userRepository.save(userEntity1);
         userEntity2 = userRepository.save(userEntity2);

        assertNotNull(userEntity1, "user entity should not be null");
        assertNotNull(userEntity2, "user entity should not be null");
    }


    @Test
    @DisplayName("test user repository find by email")
    void testUserRepositoryFindByEmail(){
        userEntity1 = userRepository.save(userEntity1);
        UserEntity userEntity = userRepository.findByEmail(userEntity1.getEmail()).orElse(null);
        assertNotNull(userEntity, "user entity should not be null");
    }


    @Test
    @DisplayName("test user repository find by id")
    void testUserRepositoryFindById(){
        userEntity1 = userRepository.save(userEntity1);
        UserEntity userEntity = userRepository.findById(userEntity1.getId()).orElse(null);
        assertNotNull(userEntity, "user entity should not be null");
    }

    @Test
    @DisplayName("test user repository find all")
    void testUserRepositoryFindAll(){
        userEntity1 = userRepository.save(userEntity1);
        userEntity2 = userRepository.save(userEntity2);
        assertFalse(userRepository.findAll().isEmpty(), "user entity should not be empty");
    }

    @Test
    @DisplayName("test user repository delete by id")
    void testUserRepositoryDeleteById(){

        userEntity1 = userRepository.save(userEntity1);
        userRepository.deleteById(userEntity1.getId());
        assertTrue(userRepository.findAll().isEmpty(), "user entity should be empty");
    }

    @Test
    @DisplayName("test user repository delete by email")
    void testUserRepositoryDeleteByEmail(){

        userEntity1 = userRepository.save(userEntity1);
        userRepository.deleteByEmail(userEntity1.getEmail());
        assertTrue(userRepository.findAll().isEmpty(), "user entity should be empty");
    }

    @Test
    @DisplayName("test user repository delete by email")
    void testUserRepositoryDeleteByUuid(){

        userEntity1 = userRepository.save(userEntity1);
        userRepository.deleteByUuid(userEntity1.getUuid());
        assertTrue(userRepository.findAll().isEmpty(), "user entity should be empty");
    }



    @Test
    @DisplayName("test user repository has remaining user")
    void testUserRepositoryHasRemainingUser(){

        userEntity1 = userRepository.save(userEntity1);
        userEntity2 = userRepository.save(userEntity2);
        userRepository.deleteByUuid(userEntity1.getUuid());
        assertEquals(1, userRepository.findAll().size(), "only one user should be in DB");
    }


}
