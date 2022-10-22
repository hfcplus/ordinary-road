package com.monologue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ShiroThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroThymeleafApplication.class, args);

        Map<String, Object> map = new HashMap<>();
        map.put("1", "asdf");
        map.put("2", "hvg");
        map.put("3", "ju90");
        map.put("4", "klj");

//        List<IncompatibleClassChangeError>
    }

}
