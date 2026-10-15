package com.alimentabem.controller;

import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.*;
import com.alimentabem.repository.*;
import com.alimentabem.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Fachada {

    private final ControladorDoador controladorDoador;
    private final ControladorDoacao controladorDoacao;

    private final AlimentoService alimentoService;
    private final EstoqueService estoqueService;

    private final InstituicaoService instituicaoService;
    private final DistribuicaoService distribuicaoService;

    public Fachada() {
        IRepositorioDoador repoDoador = new RepositorioDoadorMemoria();
        IRepositorioDoacao repoDoacao = new RepositorioDoacaoMemoria();
        IRepositorioAlimento repoAlimento = new RepositorioAlimentoMemoria();
        IRepositorioEstoque repoEstoque = new RepositorioEstoqueMemoria();
        IRepositorioInstituicao repoInstituicao = new RepositorioInstituicaoMemoria();
        IRepositorioDistribuicao repoDistribuicao = new RepositorioDistribuicaoMemoria();

        this.controladorDoador = new ControladorDoador(repoDoador);
        this.controladorDoacao = new ControladorDoacao(repoDoacao, repoDoador);

        this.estoqueService = new EstoqueService(repoEstoque);
        this.alimentoService = new AlimentoServiceImpl(repoAlimento, repoEstoque);

        ViaCepService viaCepService = new ViaCepService();
        this.instituicaoService = new InstituicaoServiceImpl(repoInstituicao, repoDistribuicao, viaCepService);
        this.distribuicaoService = new DistribuicaoServiceImpl(repoDistribuicao, this.estoqueService);
    }

    // --- DOADORES ---
    public void cadastrarDoador(Doador doador) { controladorDoador.cadastrarDoador(doador); }
    public Doador buscarDoadorPorDocumento(String doc) { return controladorDoador.buscarPorDocumento(doc); }
    public List<Doador> listarDoadores() { return controladorDoador.listarDoadores(); }

    public void atualizarDoador(String documento, String nome, String telefone, String email, String razaoSocial) {
        controladorDoador.atualizarDoador(documento, nome, telefone, email, razaoSocial);
    }

    public void removerDoador(String documento) {
        Doador doador = controladorDoador.buscarPorDocumento(documento);
        for (Doacao doacao : controladorDoacao.listarDoacoes()) {
            if (doacao.getDoador().getDocumento().equals(doador.getDocumento())) {
                throw new ValidacaoException("Não é possível remover '" + doador.getNome() + "': ele já possui doações registradas.");
            }
        }
        controladorDoador.removerDoador(documento);
    }

    // --- DOAÇÕES ---
    public void registrarDoacao(Doacao doacao) {
        controladorDoacao.registrarDoacao(doacao);
        estoqueService.registrarDoacao(doacao);
    }
    public List<Doacao> listarDoacoes() { return controladorDoacao.listarDoacoes(); }

    // --- ALIMENTOS E ESTOQUE ---
    public Alimento cadastrarAlimento(String nome, CategoriaAlimento cat, UnidadeMedida um) {
        return alimentoService.cadastrar(nome, cat, um);
    }
    public Alimento buscarAlimentoPorNome(String nome) { return alimentoService.buscarPorNome(nome); }
    public List<Alimento> listarAlimentos() { return alimentoService.listarTodos(); }

    public void atualizarAlimento(String nome, String novoNome, CategoriaAlimento cat, UnidadeMedida um) {
        alimentoService.atualizar(nome, novoNome, cat, um);
    }

    public void removerAlimento(String nome) {
        alimentoService.remover(nome);
    }

    public List<LoteEstoque> listarEstoque() { return estoqueService.listarLotes(); }
    public List<LoteEstoque> listarEstoqueVencendoEm(int dias) { return estoqueService.listarVencendoEm(dias); }

    // --- INSTITUIÇÕES BENEFICIÁRIAS ---
    public InstituicaoBeneficiaria cadastrarInstituicao(String nome, String cep, int capacidadeMensal) {
        return instituicaoService.cadastrar(nome, cep, capacidadeMensal);
    }
    public InstituicaoBeneficiaria buscarInstituicaoPorNome(String nome) {
        return instituicaoService.buscarPorNome(nome);
    }
    public List<InstituicaoBeneficiaria> listarInstituicoes() { return instituicaoService.listarTodas(); }

    public void atualizarInstituicao(String nome, String novoNome, String novoCep, int novaCapacidade) {
        instituicaoService.atualizar(nome, novoNome, novoCep, novaCapacidade);
    }

    public void removerInstituicao(String nome) {
        instituicaoService.remover(nome);
    }

    // --- DISTRIBUIÇÕES ---
    public Distribuicao registrarDistribuicao(InstituicaoBeneficiaria inst, Map<Alimento, Double> itens, int familias, LocalDate data) {
        return distribuicaoService.registrarDistribuicao(inst, itens, familias, data);
    }
    public List<Distribuicao> listarTodasDistribuicoes() { return distribuicaoService.listarTodas(); }
}