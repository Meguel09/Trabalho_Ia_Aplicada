package com.example.barcrud.repository;

import com.example.barcrud.model.Conta;
import com.example.barcrud.model.StatusConta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    boolean existsByMesaIdAndStatus(Long mesaId, StatusConta status);
    List<Conta> findByMesaId(Long mesaId);
}