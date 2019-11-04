package _4u4u_init.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class RootAppConfig {
    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setUser("root");
        ds.setPassword("root");
        try {
            ds.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/4u4u?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei");
        ds.setInitialPoolSize(4);
        ds.setMaxPoolSize(8);
        return ds;
    }
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan(new String[] {
                    "_4u4u.model"
                });
        factory.setHibernateProperties(additionalProperties());
        return factory;
    }
    @Bean(name="transactionManager")
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

         HibernateTransactionManager txManager = new HibernateTransactionManager();
         txManager.setSessionFactory(sessionFactory);
         return txManager;
      }    
    
    private Properties additionalProperties() {
        Properties properties=new Properties();
        properties.put("hibernate.dialect", org.hibernate.dialect.MySQL8Dialect.class);
        properties.put("hibernate.show_sql", Boolean.TRUE);
        properties.put("hibernate.format_sql", Boolean.TRUE);
        properties.put("default_batch_fetch_size", 10);
        properties.put("hibernate.hbm2ddl.auto", "update");

        return properties;
    }
}
