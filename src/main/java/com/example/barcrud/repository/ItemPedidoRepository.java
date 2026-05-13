package com.example.barcrud.repository;

import com.example.barcrud.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    boolean existsByProdutoId(Long produtoId);
    Optional<ItemPedido> findByPedidoIdAndProdutoId(Long pedidoId, Long produtoId);
}
