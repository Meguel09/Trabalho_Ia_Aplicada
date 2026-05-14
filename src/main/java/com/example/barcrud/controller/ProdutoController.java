package com.example.barcrud.controller;

import com.example.barcrud.dto.ProdutoRequest;
import com.example.barcrud.model.Produto;
import com.example.barcrud.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import java.util.Locale;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) { this.service = service; }

    @GetMapping
    public String tela(@RequestParam(required = false) String q,
                       @RequestParam(defaultValue = "todos") String status,
                       @RequestParam(defaultValue = "id-asc") String ordem,
                       Model model) {
        List<Produto> produtos = filtrarProdutos(q, status, ordem);
        List<String> categorias = service.listarCategorias();

        model.addAttribute("produtos", produtos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("statusSelecionado", status);
        model.addAttribute("ordemSelecionada", ordem);
        model.addAttribute("totalProdutos", service.listar().size());
        model.addAttribute("produtosAtivos", service.listar().stream().filter(Produto::getAtivo).count());
        model.addAttribute("produtosInativos", service.listar().stream().filter(produto -> !produto.getAtivo()).count());
        return "produtos";
    }

    @PostMapping
    public String criar(@ModelAttribute @Valid ProdutoRequest request,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Preencha os campos do produto corretamente.");
            return "redirect:/produtos";
        }

        try {
            service.criar(request);
            redirectAttributes.addFlashAttribute("successMessage", "Produto cadastrado com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute @Valid ProdutoRequest request,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Preencha os campos do produto corretamente.");
            return "redirect:/produtos";
        }

        try {
            service.atualizar(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Produto atualizado com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @PostMapping("/excluir/{id}")
    public String excluirTela(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("successMessage", "Produto removido com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @PostMapping("/categorias")
    public String criarCategoria(@RequestParam String nome, RedirectAttributes redirectAttributes) {
        try {
            service.criarCategoria(nome);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria cadastrada com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @PostMapping("/categorias/editar")
    public String editarCategoria(@RequestParam String nomeAtual,
                                  @RequestParam String nome,
                                  RedirectAttributes redirectAttributes) {
        try {
            service.atualizarCategoria(nomeAtual, nome);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria atualizada com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @PostMapping("/categorias/excluir")
    public String excluirCategoria(@RequestParam String nome, RedirectAttributes redirectAttributes) {
        try {
            service.excluirCategoria(nome);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria excluida com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @ResponseBody
    @PostMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Produto criarApi(@RequestBody @Valid ProdutoRequest request) { return service.criar(request); }

    @ResponseBody
    @GetMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Produto> listarApi() { return service.listar(); }

    @ResponseBody
    @GetMapping(value = "/api/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Produto buscarApi(@PathVariable Long id) { return service.buscar(id); }

    @ResponseBody
    @PutMapping(value = "/api/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Produto atualizarApi(@PathVariable Long id, @RequestBody @Valid ProdutoRequest request) {
        return service.atualizar(id, request);
    }

    @ResponseBody
    @DeleteMapping("/api/{id}")
    public void excluirApi(@PathVariable Long id) { service.excluir(id); }

    private List<Produto> filtrarProdutos(String q, String status, String ordem) {
        String termo = q == null ? "" : q.trim().toLowerCase(Locale.ROOT);

        Comparator<Produto> comparador = switch (ordem) {
            case "id-asc" -> Comparator.comparing(Produto::getId);
            case "id-desc" -> Comparator.comparing(Produto::getId).reversed();
            case "nome-asc" -> Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER);
            case "nome-desc" -> Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER).reversed();
            case "preco-asc" -> Comparator.comparing(Produto::getPreco);
            case "preco-desc" -> Comparator.comparing(Produto::getPreco).reversed();
            default -> Comparator.comparing(Produto::getId);
        };

        return service.listar().stream()
                .filter(produto -> termo.isBlank() || produto.getNome().toLowerCase(Locale.ROOT).contains(termo))
                .filter(produto -> switch (status) {
                    case "ativo" -> produto.getAtivo();
                    case "inativo" -> !produto.getAtivo();
                    default -> true;
                })
                .sorted(comparador.thenComparing(Produto::getId))
                .toList();
    }
}
