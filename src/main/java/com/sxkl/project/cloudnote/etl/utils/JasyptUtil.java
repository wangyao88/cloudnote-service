package com.sxkl.project.cloudnote.etl.utils;


import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JasyptUtil {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        System.out.println("root   " + stringEncryptor.encrypt("root"));
        System.out.println(stringEncryptor.encrypt("5ff8ff5f-c0fe-4cf0-8d4f-a56d956e9284"));
    }
}
