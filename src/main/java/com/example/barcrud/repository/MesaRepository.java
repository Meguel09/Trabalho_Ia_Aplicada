package com.example.barcrud.repository;

import com.example.barcrud.model.Mesa;
import com.example.barcrud.model.StatusMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByNumero(Integer numero);
    List<Mesa> findByStatus(StatusMesa status);
}