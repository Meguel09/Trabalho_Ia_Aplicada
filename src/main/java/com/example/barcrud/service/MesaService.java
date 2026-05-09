package com.example.barcrud.service;

import com.example.barcrud.dto.MesaRequest;
import com.example.barcrud.exception.BusinessException;
import com.example.barcrud.exception.NotFoundException;
import com.example.barcrud.model.Mesa;
import com.example.barcrud.model.StatusMesa;
import com.example.barcrud.repository.MesaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MesaService {
    private final MesaRepository repository;

    public MesaService(MesaRepository repository) {
        this.repository = repository;
    }

    public Mesa criar(MesaRequest request) {
        repository.findByNumero(request.numero()).ifPresent(m -> {
            throw new BusinessException("Já existe uma mesa com esse número.");
        });
        return repository.save(new Mesa(request.numero()));
    }

    public List<Mesa> listar() { return repository.findAll(); }

    public List<Mesa> listarPorStatus(StatusMesa status) { return repository.findByStatus(status); }

    public Mesa buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Mesa não encontrada."));
    }

    public Mesa atualizar(Long id, MesaRequest request) {
        Mesa mesa = buscar(id);
        repository.findByNumero(request.numero()).ifPresent(m -> {
            if (!m.getId().equals(id)) throw new BusinessException("Número de mesa já cadastrado.");
        });
        mesa.setNumero(request.numero());
        return repository.save(mesa);
    }

    public void excluir(Long id) {
        Mesa mesa = buscar(id);
        repository.delete(mesa);
    }
}