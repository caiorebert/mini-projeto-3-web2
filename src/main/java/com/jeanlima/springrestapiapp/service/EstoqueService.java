package com.jeanlima.springrestapiapp.service;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;

import java.util.List;

public interface EstoqueService {

    void salvar(EstoqueDTO e, Produto p);

    public List<Estoque> getAllEstoque();

    public Estoque getEstoqueById(Integer id);

    public Estoque getEstoqueByProduto(Produto produto);

    EstoqueDTO getEstoqueByNomeProdutoDTO(String descricao);

    Estoque getEstoqueByNomeProduto(String descricao);

    void aumentarQuantidadeEstoque(Integer id, int quantidade);

    void diminuirQuantidadeEstoque(Integer id, Integer quantidade);
}
