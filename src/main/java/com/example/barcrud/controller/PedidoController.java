package com.example.barcrud.controller;

import com.example.barcrud.dto.AbrirContaRequest;
import com.example.barcrud.dto.ItemPedidoRequest;
import com.example.barcrud.dto.PedidoRequest;
import com.example.barcrud.model.Conta;
import com.example.barcrud.model.ItemPedido;
import com.example.barcrud.model.Mesa;
import com.example.barcrud.model.Pedido;
import com.example.barcrud.model.Produto;
import com.example.barcrud.model.StatusConta;
import com.example.barcrud.model.StatusMesa;
import com.example.barcrud.service.ContaService;
import com.example.barcrud.service.MesaService;
import com.example.barcrud.service.PedidoService;
import com.example.barcrud.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final ContaService contaService;
    private final MesaService mesaService;
    private final ProdutoService produtoService;

    public PedidoController(PedidoService pedidoService, ContaService contaService,
                            MesaService mesaService, ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.contaService = contaService;
        this.mesaService = mesaService;
        this.produtoService = produtoService;
    }

    @GetMapping
    public String tela(Model model) {
        List<Pedido> pedidos = pedidoService.listar().stream()
                .sorted(Comparator.comparing(Pedido::getCriadoEm).reversed())
                .toList();
        List<Conta> contasAbertas = contaService.listar().stream()
                .filter(conta -> conta.getStatus() == StatusConta.ABERTA)
                .toList();
        List<Mesa> mesasDisponiveis = mesaService.listar().stream()
                .filter(mesa -> mesa.getStatus() != StatusMesa.FECHADA)
                .toList();
        List<Produto> produtosAtivos = produtoService.listar().stream()
                .filter(Produto::getAtivo)
                .toList();

        model.addAttribute("pedidos", pedidos);
        model.addAttribute("contasAbertas", contasAbertas);
        model.addAttribute("mesasDisponiveis", mesasDisponiveis);
        model.addAttribute("produtosAtivos", produtosAtivos);
        return "pedidos";
    }

    @PostMapping("/abrir")
    public String abrirPedido(@RequestParam Long mesaId,
                              @RequestParam String cliente,
                              @RequestParam(required = false) Boolean individual,
                              @RequestParam(required = false) Boolean taxaServico,
                              @RequestParam Long produtoId,
                              @RequestParam Integer quantidade,
                              RedirectAttributes redirectAttributes) {
        try {
            Conta conta = contaService.abrir(new AbrirContaRequest(
                    mesaId,
                    cliente,
                    individual != null && individual,
                    taxaServico != null && taxaServico
            ));
            Pedido pedido = pedidoService.criar(new PedidoRequest(conta.getId()));
            pedidoService.adicionarItem(pedido.getId(), new ItemPedidoRequest(produtoId, quantidade));
            redirectAttributes.addFlashAttribute("successMessage", "Pedido aberto com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pedidos";
    }

    @PostMapping("/contas/{contaId}/novo")
    public String criarPedidoParaConta(@PathVariable Long contaId, RedirectAttributes redirectAttributes) {
        try {
            pedidoService.criar(new PedidoRequest(contaId));
            redirectAttributes.addFlashAttribute("successMessage", "Novo pedido criado para a conta.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pedidos";
    }

    @PostMapping("/{id}/itens")
    public String adicionarItemTela(@PathVariable Long id,
                                    @RequestParam Long produtoId,
                                    @RequestParam Integer quantidade,
                                    RedirectAttributes redirectAttributes) {
        try {
            pedidoService.adicionarItem(id, new ItemPedidoRequest(produtoId, quantidade));
            redirectAttributes.addFlashAttribute("successMessage", "Item adicionado ao pedido.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pedidos";
    }

    @PostMapping("/itens/{itemId}/remover")
    public String removerItemTela(@PathVariable Long itemId, RedirectAttributes redirectAttributes) {
        try {
            pedidoService.removerItem(itemId);
            redirectAttributes.addFlashAttribute("successMessage", "Item removido do pedido.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pedidos";
    }

    @PostMapping("/contas/{contaId}/fechar")
    public String fecharContaTela(@PathVariable Long contaId, RedirectAttributes redirectAttributes) {
        try {
            contaService.fechar(contaId);
            redirectAttributes.addFlashAttribute("successMessage", "Conta fechada com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pedidos";
    }

    @ResponseBody
    @PostMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pedido criar(@RequestBody @Valid PedidoRequest request) { return pedidoService.criar(request); }

    @ResponseBody
    @GetMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pedido> listar() { return pedidoService.listar(); }

    @ResponseBody
    @GetMapping(value = "/api/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Pedido buscar(@PathVariable Long id) { return pedidoService.buscar(id); }

    @ResponseBody
    @PutMapping(value = "/api/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pedido atualizar(@PathVariable Long id, @RequestBody @Valid PedidoRequest request) {
        return pedidoService.atualizar(id, request);
    }

    @ResponseBody
    @DeleteMapping("/api/{id}")
    public void excluir(@PathVariable Long id) { pedidoService.excluir(id); }

    @ResponseBody
    @PostMapping(value = "/api/{id}/itens", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ItemPedido adicionarItem(@PathVariable Long id, @RequestBody @Valid ItemPedidoRequest request) {
        return pedidoService.adicionarItem(id, request);
    }

    @ResponseBody
    @DeleteMapping("/api/itens/{itemId}")
    public void removerItem(@PathVariable Long itemId) { pedidoService.removerItem(itemId); }
}
