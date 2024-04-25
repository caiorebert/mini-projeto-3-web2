package com.jeanlima.springrestapiapp.rest.controllers;

import com.jeanlima.springrestapiapp.exception.EstoqueEsgotadoException;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.rest.dto.NomeProdutoDTO;
import com.jeanlima.springrestapiapp.service.EstoqueService;
import com.jeanlima.springrestapiapp.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/estoque")
@RestController
public class EstoqueController {

    @Autowired
    @Qualifier("estoqueServiceImpl")
    EstoqueService estoqueService;

    @Autowired
    ProdutoService produtoService;

    @GetMapping
    public List<Estoque> read(){
        return estoqueService.getAllEstoque();
    }

    @PostMapping
    public void save(@RequestBody EstoqueDTO estoqueDTO){
        Produto produto = produtoService.getProdutoByNome(estoqueDTO.getNomeProduto());
        estoqueService.salvar(estoqueDTO, produto);
    }

    @PostMapping("/pesquisar")
    public EstoqueDTO getEstoqueByNomeProduto(@RequestBody NomeProdutoDTO nomeProduto){
        return estoqueService.getEstoqueByNomeProdutoDTO(nomeProduto.getNomeProduto());
    }

    @PatchMapping("/{id}/{operacao}")
    public String diminuirQuantidadeEstoque(@PathVariable Integer id, @PathVariable Integer operacao) {
        try {
            if (operacao==1) {
                estoqueService.aumentarQuantidadeEstoque(id, 1);
                return "Produto adicionado ao estoque";
            } else {
                estoqueService.diminuirQuantidadeEstoque(id, 1);
                return "Estoque diminuido";
            }
        } catch (EstoqueEsgotadoException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/{id_estoque}")
    public Estoque getEstoqueByProdutoId(@PathVariable int id_estoque){
        return estoqueService.getEstoqueById(id_estoque);
    }
}
