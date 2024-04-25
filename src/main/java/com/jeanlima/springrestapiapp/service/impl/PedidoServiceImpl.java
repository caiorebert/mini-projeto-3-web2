package com.jeanlima.springrestapiapp.service.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jeanlima.springrestapiapp.exception.EstoqueEsgotadoException;
import com.jeanlima.springrestapiapp.model.*;
import com.jeanlima.springrestapiapp.repository.*;
import com.jeanlima.springrestapiapp.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import com.jeanlima.springrestapiapp.exception.PedidoNaoEncontradoException;
import com.jeanlima.springrestapiapp.exception.RegraNegocioException;
import com.jeanlima.springrestapiapp.rest.dto.ItemPedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;
import com.jeanlima.springrestapiapp.service.PedidoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    
    private final PedidoRepository repository;
    private final ClienteRepository clientesRepository;
    private final ProdutoRepository produtosRepository;
    private final ItemPedidoRepository itemsPedidoRepository;
    private final EstoqueRepository estoqueRepository;

    @Autowired
    @Qualifier("estoqueServiceImpl")
    EstoqueService estoqueService;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        BigDecimal total = new BigDecimal(0);
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        if (dto.getTotal()!=null) {
            pedido.setTotal(dto.getTotal());
        } else {
            for (ItemPedido itemPedido : itemsPedido) {
                Estoque estoque = estoqueService.getEstoqueByProduto(itemPedido.getProduto());
                try {
                    estoqueService.diminuirQuantidadeEstoque(estoque.getId(), itemPedido.getQuantidade());
                } catch (EstoqueEsgotadoException e) {
                    throw new EstoqueEsgotadoException();
                }
                total = total.add(itemPedido.getProduto().getPreco());
            }
            pedido.setTotal(total);
        }
        return pedido;
    }

    @Override
    public void editar(PedidoDTO dto) {
        Pedido pedido = repository.findPedidoById(dto.getPedido());
        pedido.setItens(null);
        List<ItemPedido> itemPedidoList = converterItems(pedido, dto.getItems());
        pedido.setItens(itemPedidoList);
        for (ItemPedido itemPedido : itemPedidoList) {
            itemPedido.setPedido(pedido);
            itemsPedidoRepository.save(itemPedido);
        }
        pedido.setStatus(dto.getStatus());
        pedido.setCliente(clientesRepository.getReferenceById(dto.getCliente()));
        repository.save(pedido);
    }

    @Override
    public void delete(Integer id) {
        Pedido pedido = repository.getReferenceById(id);
        for (ItemPedido itemPedido : pedido.getItens()) {
            Estoque estoque = estoqueRepository.getReferenceById(itemPedido.getProduto().getEstoque().getId());
            estoqueService.aumentarQuantidadeEstoque(estoque.getId(), itemPedido.getQuantidade());
            estoqueRepository.save(estoque);
        }
        repository.delete(pedido);
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }
    @Override
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
        .findById(id)
        .map( pedido -> {
            pedido.setStatus(statusPedido);
            return repository.save(pedido);
        }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }

    public PedidoDTO pedidoParaDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        List<ItemPedidoDTO> itemPedidoDTOS = new ArrayList<ItemPedidoDTO>();
        BigDecimal total = new BigDecimal(0);
        for (ItemPedido itemPedido : pedido.getItens()) {
            ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setProduto(itemPedido.getProduto().getId());
            total = total.add(itemPedido.getProduto().getPreco());
            itemPedidoDTO.setQuantidade(itemPedido.getQuantidade());
            itemPedidoDTOS.add(itemPedidoDTO);
        }
        pedidoDTO.setTotal(total);
        pedidoDTO.setItems(itemPedidoDTOS);
        return pedidoDTO;
    }
}
