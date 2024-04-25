package com.jeanlima.springrestapiapp.repository;

import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque,Integer>{

    @Query(value = "select * from estoque e where e.produto_id = :id_produto", nativeQuery = true)
    Estoque encontrarEstoquePorProduto(Integer id_produto);

    @Query(value = "select e from Estoque e inner join e.produto p where p.descricao LIKE %:descricao%")
    Estoque encontrarEstoquePorNomeProduto(String descricao);

}
