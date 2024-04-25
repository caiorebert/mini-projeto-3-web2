package com.jeanlima.springrestapiapp;

import com.jeanlima.springrestapiapp.model.Usuario;
import com.jeanlima.springrestapiapp.repository.UsuarioRepository;
import com.jeanlima.springrestapiapp.service.UsuarioService;
import com.jeanlima.springrestapiapp.service.impl.UsuarioServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringRestApiAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringRestApiAppApplication.class, args);
	}

}
