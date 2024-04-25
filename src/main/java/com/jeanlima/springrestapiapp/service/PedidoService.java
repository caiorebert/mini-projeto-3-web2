package com.jeanlima.springrestapiapp.service;

import java.util.Optional;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;



public interface PedidoService {
    Pedido salvar( PedidoDTO dto );
    void editar( PedidoDTO dto );
    void delete(Integer id);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
    PedidoDTO pedidoParaDTO(Pedido pedido);

}
