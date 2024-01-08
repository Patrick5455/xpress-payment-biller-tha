

package com.xpresspayment.takehometest.common.configs.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class DataSourceConfig {


//    @Bean
//    @Qualifier("mysqlDatasource")
//    public DataSource dataSource() {
//        return connectionPoolConfig().initializeDSFromConfig();
//    }
//
//    @Bean
//    @Qualifier("mysqlConnectionPoolConfig")
//    public IdbConnectionPoolConfig connectionPoolConfig(){
//        return new MysqlDbConnectionPoolConfig(dbProperties(), dbUrl());
//    }

    @Profile({"!unittest", "!i-test"})
    @ConfigurationProperties(prefix = "db")
    @Bean
    public DBProperties dbProperties () {
        return DBProperties.builder().build();
    }

    @Bean(name = "dbUrl")
    public  String dbUrl(){
        String dbProperties = "false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        DBProperties db = dbProperties();
        return String.format(
                "jdbc:%s://%s:%d/%s?useSSL=%s",
                db.getDriver(), db.getHost(), db.getPort(), db.getName(), dbProperties);}

}
