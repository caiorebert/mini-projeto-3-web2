package com.jeanlima.springrestapiapp.service.impl;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import com.jeanlima.springrestapiapp.exception.PedidoNaoEncontradoException;
import com.jeanlima.springrestapiapp.exception.ProdutoNaoEncontradoException;
import com.jeanlima.springrestapiapp.exception.RegraNegocioException;
import com.jeanlima.springrestapiapp.model.*;
import com.jeanlima.springrestapiapp.repository.ClienteRepository;
import com.jeanlima.springrestapiapp.repository.ItemPedidoRepository;
import com.jeanlima.springrestapiapp.repository.PedidoRepository;
import com.jeanlima.springrestapiapp.repository.ProdutoRepository;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.rest.dto.ItemPedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;
import com.jeanlima.springrestapiapp.service.EstoqueService;
import com.jeanlima.springrestapiapp.service.PedidoService;
import com.jeanlima.springrestapiapp.service.ProdutoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    @Qualifier("estoqueServiceImpl")
    EstoqueService estoqueService;

    @Override
    public Produto getProdutoByNome(String nome) {
        return produtoRepository.encontrarProdutoPorNome(nome);
    }

    @Override
    public void salvar(Produto p) {
        produtoRepository.save(p);
    }

    @Override
    public void delete(Produto p) {
        produtoRepository.delete(p);
    }

    @Override
    public void edit(Integer id, Produto p) {
        if (produtoRepository.getReferenceById(id).getId()==null) {
            throw new ProdutoNaoEncontradoException();
        } else {
            p.setId(id);
            produtoRepository.save(p);
        }
    }

    @Override
    public Produto getById(Integer id) {
        return produtoRepository.getReferenceById(id);
    }


}
