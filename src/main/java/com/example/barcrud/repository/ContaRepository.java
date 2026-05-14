package com.example.barcrud.repository;

import com.example.barcrud.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByMesaId(Long mesaId);
    boolean existsByMesaId(Long mesaId);
}
