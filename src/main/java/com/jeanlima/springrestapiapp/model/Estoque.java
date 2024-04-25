package com.jeanlima.springrestapiapp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "estoque")
public class Estoque {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Integer quantidade;

    @OneToOne
    private Produto produto;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
