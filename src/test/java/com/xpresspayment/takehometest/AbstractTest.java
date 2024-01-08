package com.xpresspayment.takehometest;


import java.sql.Timestamp;

import com.xpresspayment.takehometest.common.utils.GlobalUtils;
import com.xpresspayment.takehometest.data.UserRepository;
import com.xpresspayment.takehometest.domain.UserEntity;
import com.xpresspayment.takehometest.services.SignupService;
import com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.impl.XpressPaymentVtuClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes ={Service.class},
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WithMockUser
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = {"classpath:sql/test-data.sql", "classpath:sql/test-schema.sql"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected SignupService signupService;
    protected XpressPaymentVtuClient xpressPaymentVtuClient;

    protected static UserEntity userEntity1;
    protected static UserEntity userEntity2;


}
