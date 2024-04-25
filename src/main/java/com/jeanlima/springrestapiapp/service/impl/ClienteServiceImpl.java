package com.jeanlima.springrestapiapp.service.impl;

import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.model.Usuario;
import com.jeanlima.springrestapiapp.repository.ClienteRepository;
import com.jeanlima.springrestapiapp.rest.dto.ClientePedidosDTO;
import com.jeanlima.springrestapiapp.rest.dto.ItemPedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;
import com.jeanlima.springrestapiapp.service.ClienteService;
import com.jeanlima.springrestapiapp.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    @Qualifier("pedidoServiceImpl")
    PedidoService pedidoService;

    @Override
    public ClientePedidosDTO getClienteInfo(Integer id) {
        Cliente cliente = clienteRepository.getReferenceById(id);
        ClientePedidosDTO clientePedidosDTO = new ClientePedidosDTO();
        clientePedidosDTO.setNomeCliente(cliente.getNome());
        clientePedidosDTO.setCpfCliente(cliente.getCpf());
        Set<Pedido> pedidos = cliente.getPedidos();
        List<PedidoDTO> pedidoDTOS = new ArrayList<PedidoDTO>();
        for (Pedido p : pedidos) {
                pedidoDTOS.add(pedidoService.pedidoParaDTO(p));
        }
        clientePedidosDTO.setPedidosCliente(pedidoDTOS);
        return clientePedidosDTO;
    }

    @Override
    public void editById(Integer id, String nome, String cpf) {
        Cliente cliente = clienteRepository.getReferenceById(id);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        clienteRepository.save(cliente);
    }
}
