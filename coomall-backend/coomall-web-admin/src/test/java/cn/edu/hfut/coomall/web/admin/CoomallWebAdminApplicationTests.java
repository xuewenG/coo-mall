package cn.edu.hfut.coomall.web.admin;

import cn.edu.hfut.coomall.dao.CustomMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoomallWebAdminApplicationTests {

    @Autowired
    CustomMapper customMapper;

    @Test
    public void customTest() {

    }

}
