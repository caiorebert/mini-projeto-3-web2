package com.jeanlima.springrestapiapp.service;

import com.jeanlima.springrestapiapp.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    Usuario getUsuarioById(Integer id);
}
