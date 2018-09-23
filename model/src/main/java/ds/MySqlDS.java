package ds;

import org.apache.tomcat.jdbc.pool.DataSource;




public class MySqlDS {

    public DataSource getDataSource() {

        DataSource dataSource = new DataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/contact_list?allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        dataSource.setMaxIdle(5);
        dataSource.setMinIdle(2);
        return dataSource;
    }
}