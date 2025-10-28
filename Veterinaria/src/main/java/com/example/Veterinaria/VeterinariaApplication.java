package com.example.Veterinaria;

import org.springframework.boot.SpringApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VeterinariaApplication {

    public static void main(String[] args) {
        cargarEnv();
        SpringApplication.run(VeterinariaApplication.class, args);

    }

    public static void cargarEnv() {

        Dotenv dotenv = Dotenv.load();
        System.setProperty("BD_URL", dotenv.get("BD_URL"));
        System.setProperty("USER_NAME", dotenv.get("USER_NAME"));
        System.setProperty("PASSWORD", dotenv.get("PASSWORD"));

    }
}


