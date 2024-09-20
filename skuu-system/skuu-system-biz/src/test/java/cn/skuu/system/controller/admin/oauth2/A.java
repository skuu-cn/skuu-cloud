package cn.skuu.system.controller.admin.oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TODO
 *
 * @author dcx
 * @since 2024-06-13 16:34
 **/
@SpringBootTest
public class A {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void a() throws SQLException, ClassNotFoundException {
        StopWatch stopWatch = new StopWatch();
        for (int i = 0; i < 5; i++) {
            stopWatch.start(i + "");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String name = "root";
            String password = "12345";
            String url = "jdbc:mysql://127.0.0.1:3306/test";
            Connection conn = DriverManager.getConnection(url, name, password);
            conn.close();
            stopWatch.stop();
        }
        System.out.println(stopWatch.prettyPrint());
    }

    public static void main(String[] args) {
//        ConvertHelper.toPdf(ofdPath, pdfPath);
        String a = null;

    }
}
