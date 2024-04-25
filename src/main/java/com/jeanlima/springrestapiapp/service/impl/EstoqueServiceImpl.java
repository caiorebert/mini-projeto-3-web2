package com.jeanlima.springrestapiapp.service.impl;

import com.jeanlima.springrestapiapp.exception.EstoqueEsgotadoException;
import com.jeanlima.springrestapiapp.exception.ProdutoNaoEncontradoException;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.repository.EstoqueRepository;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.service.EstoqueService;
import com.jeanlima.springrestapiapp.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Override
    public void salvar(EstoqueDTO e, Produto p) {
        Estoque estoque = new Estoque();
        estoque.setProduto(p);
        estoque.setQuantidade(e.getQuantidade());
        estoqueRepository.save(estoque);
    }

    @Override
    public List<Estoque> getAllEstoque() {
        return estoqueRepository.findAll();
    }

    @Override
    public Estoque getEstoqueById(Integer id) {
        return estoqueRepository.getReferenceById(id);
    }

    @Override
    public Estoque getEstoqueByProduto(Produto produto) {
        return estoqueRepository.encontrarEstoquePorProduto(produto.getId());
    }

    @Override
    public EstoqueDTO getEstoqueByNomeProdutoDTO(String descricao) {
        Estoque estoque = estoqueRepository.encontrarEstoquePorNomeProduto(descricao);
        if (estoque != null) {
            EstoqueDTO estoqueDTO = new EstoqueDTO();
            estoqueDTO.setNomeProduto(estoque.getProduto().getDescricao());
            estoqueDTO.setQuantidade(estoque.getQuantidade());
            return estoqueDTO;
        } else {
            throw new ProdutoNaoEncontradoException();
        }
    }

    @Override
    public Estoque getEstoqueByNomeProduto(String descricao) {
        return estoqueRepository.encontrarEstoquePorNomeProduto(descricao);
    }

    @Override
    public void aumentarQuantidadeEstoque(Integer id, int quantidade) {
        Estoque estoque = estoqueRepository.getReferenceById(id);
        if (estoque.getQuantidade() > 0) {
            estoque.setQuantidade(estoque.getQuantidade() + 1);
        } else {
            estoque.setQuantidade(1);
        }
        estoqueRepository.save(estoque);
    }

    @Override
    public void diminuirQuantidadeEstoque(Integer id, Integer quantidade) {
        Estoque estoque = estoqueRepository.getReferenceById(id);
        if (estoque.getQuantidade() > 0) {
            estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        } else {
            throw new EstoqueEsgotadoException();
        }
    }
}
