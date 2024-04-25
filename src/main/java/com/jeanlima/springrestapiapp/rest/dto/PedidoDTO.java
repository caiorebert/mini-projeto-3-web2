package com.jeanlima.springrestapiapp.rest.dto;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/*
 * {
    "cliente" : 1,
    "total" : 100,
    "items" : [
        {
            "produto": 1,
            "quantidade": 10
        }
        
    ]
}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer cliente;
    private Integer pedido;
    private StatusPedido status;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;
}
