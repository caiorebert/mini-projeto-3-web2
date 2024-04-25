package com.jeanlima.springrestapiapp.repository;

import com.jeanlima.springrestapiapp.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanlima.springrestapiapp.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProdutoRepository extends JpaRepository<Produto,Integer>{
    @Query(value = "select p from Produto p where p.descricao like %:nome% ")
    Produto encontrarProdutoPorNome(@Param("nome") String nome);

}
