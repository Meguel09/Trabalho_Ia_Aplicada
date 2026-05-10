package com.example.barcrud.service;

import com.example.barcrud.dto.AbrirContaRequest;
import com.example.barcrud.dto.PagamentoRequest;
import com.example.barcrud.exception.BusinessException;
import com.example.barcrud.exception.NotFoundException;
import com.example.barcrud.model.*;
import com.example.barcrud.repository.ContaRepository;
import com.example.barcrud.repository.MesaRepository;
import com.example.barcrud.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContaService {
    private final ContaRepository contaRepository;
    private final MesaRepository mesaRepository;
    private final PagamentoRepository pagamentoRepository;

    public ContaService(ContaRepository contaRepository, MesaRepository mesaRepository, PagamentoRepository pagamentoRepository) {
        this.contaRepository = contaRepository;
        this.mesaRepository = mesaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public Conta abrir(AbrirContaRequest request) {
        Mesa mesa = mesaRepository.findById(request.mesaId())
                .orElseThrow(() -> new NotFoundException("Mesa não encontrada."));

        if (contaRepository.existsByMesaIdAndStatus(mesa.getId(), StatusConta.ABERTA)) {
            throw new BusinessException("Já existe conta aberta para essa mesa.");
        }

        Conta conta = new Conta(
                mesa,
                request.cliente().trim(),
                request.individual() == null || request.individual(),
                request.taxaServico() != null && request.taxaServico()
        );

        mesa.setStatus(StatusMesa.OCUPADA);
        mesaRepository.save(mesa);
        return contaRepository.save(conta);
    }

    public List<Conta> listar() { return contaRepository.findAll(); }

    public Conta buscar(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada."));
    }

    public Conta atualizar(Long id, AbrirContaRequest request) {
        Conta conta = buscar(id);
        if (conta.getStatus() == StatusConta.FECHADA) {
            throw new BusinessException("Não é possível editar conta fechada.");
        }

        if (!conta.getMesa().getId().equals(request.mesaId())) {
            Mesa novaMesa = mesaRepository.findById(request.mesaId())
                    .orElseThrow(() -> new NotFoundException("Nova mesa não encontrada."));

            if (contaRepository.existsByMesaIdAndStatus(novaMesa.getId(), StatusConta.ABERTA)) {
                throw new BusinessException("Já existe conta aberta para a nova mesa informada.");
            }

            Mesa mesaAtual = conta.getMesa();
            mesaAtual.setStatus(StatusMesa.LIVRE);
            novaMesa.setStatus(StatusMesa.OCUPADA);
            mesaRepository.save(mesaAtual);
            mesaRepository.save(novaMesa);
            conta.setMesa(novaMesa);
        }

        conta.setCliente(request.cliente().trim());
        conta.setIndividual(request.individual() == null ? conta.getIndividual() : request.individual());
        conta.setTaxaServico(request.taxaServico() == null ? conta.getTaxaServico() : request.taxaServico());
        return contaRepository.save(conta);
    }

    public Conta fechar(Long id) {
        Conta conta = buscar(id);
        if (conta.getStatus() == StatusConta.FECHADA) {
            throw new BusinessException("Conta já está fechada.");
        }

        boolean semItens = conta.getPedidos().isEmpty() || conta.getPedidos().stream().allMatch(p -> p.getItens().isEmpty());
        if (semItens) {
            throw new BusinessException("Não é possível fechar conta sem itens.");
        }

        conta.setStatus(StatusConta.FECHADA);
        conta.setFechadaEm(LocalDateTime.now());
        conta.getMesa().setStatus(StatusMesa.FECHADA);
        mesaRepository.save(conta.getMesa());
        return contaRepository.save(conta);
    }

    public Pagamento pagar(Long contaId, PagamentoRequest request) {
        Conta conta = buscar(contaId);
        if (conta.getStatus() != StatusConta.FECHADA) {
            throw new BusinessException("Feche a conta antes de registrar pagamento.");
        }
        if (request.valor().compareTo(conta.getTotal()) > 0) {
            throw new BusinessException("O pagamento não pode ser maior que o total da conta.");
        }
        return pagamentoRepository.save(new Pagamento(conta, request.forma().trim(), request.valor()));
    }

    public void excluir(Long id) {
        Conta conta = buscar(id);
        if (conta.getStatus() == StatusConta.ABERTA) {
            throw new BusinessException("Não é possível excluir conta aberta. Feche a conta antes.");
        }
        contaRepository.delete(conta);
    }

    public String imprimir(Long id) {
        Conta conta = buscar(id);
        StringBuilder sb = new StringBuilder();
        sb.append("====== CONTA DO BAR ======\n");
        sb.append("Conta: ").append(conta.getId()).append("\n");
        sb.append("Mesa: ").append(conta.getMesa().getNumero()).append("\n");
        sb.append("Cliente: ").append(conta.getCliente()).append("\n");
        sb.append("Tipo: ").append(conta.getIndividual() ? "Individual" : "Agrupada").append("\n");
        sb.append("Status: ").append(conta.getStatus()).append("\n\n");
        sb.append("Itens:\n");

        conta.getPedidos().forEach(pedido -> pedido.getItens().forEach(item ->
                sb.append("- ")
                  .append(item.getQuantidade()).append("x ")
                  .append(item.getProduto().getNome())
                  .append(" | R$ ").append(item.getPrecoUnitario())
                  .append(" | Total: R$ ").append(item.getTotal())
                  .append("\n")
        ));

        sb.append("\nSubtotal: R$ ").append(conta.getSubtotal());
        sb.append("\nTaxa de serviço: R$ ").append(conta.getValorTaxaServico());
        sb.append("\nTOTAL: R$ ").append(conta.getTotal());
        sb.append("\n==========================");
        return sb.toString();
    }
}
