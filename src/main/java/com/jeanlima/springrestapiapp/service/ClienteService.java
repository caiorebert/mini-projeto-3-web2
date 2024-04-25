package com.jeanlima.springrestapiapp.service;

import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.model.Usuario;
import com.jeanlima.springrestapiapp.rest.dto.ClientePedidosDTO;

public interface ClienteService {

    ClientePedidosDTO getClienteInfo(Integer id);

    void editById(Integer id, String nome, String cpf);
}
