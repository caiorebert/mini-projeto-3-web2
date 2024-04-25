package com.jeanlima.springrestapiapp.service;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;

import java.util.Optional;


public interface ProdutoService {

    Produto getProdutoByNome(String nome);

    void salvar(Produto p);

    void delete(Produto p);

    void edit(Integer id, Produto p);

    Produto getById(Integer id);
}
