package com.dartech.myschola;

import com.dartech.myschola.utils.DataGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class MyScholaApplication implements CommandLineRunner {


    final DataGenerator dataGenerator;

    public MyScholaApplication(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyScholaApplication.class, args);
    }

    @Override
    @Profile("!test")
    public void run(String... args) {
        dataGenerator.generatePermissions();
        dataGenerator.generateRoles();
        dataGenerator.generateSuperAdmin();
    }

}
