package com.alimentabem.ui;

import com.alimentabem.controller.Fachada;
import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Fachada fachada = new Fachada();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1 -> menuDoadores();
                    case 2 -> {
                        MenuAlimentos menuAlimentos = new MenuAlimentos(fachada, scanner);
                        menuAlimentos.exibirMenu();
                    }
                    case 3 -> {
                        MenuInstituicoes menuInst = new MenuInstituicoes(fachada, scanner);
                        menuInst.menuGestaoInstituicoes();
                    }
                    case 4 -> menuRegistrarDoacao();
                    case 5 -> {
                        MenuInstituicoes menuInst = new MenuInstituicoes(fachada, scanner);
                        menuInst.menuRegistrarDistribuicao();
                    }
                    case 0 -> System.out.println("Saindo do Sistema AlimentaBem. Até logo!");
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
            } catch (ValidacaoException e) {
                System.out.println("Erro de Validação: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n========== SISTEMA ALIMENTABEM ==========");
        System.out.println("1. Gestão de Doadores");
        System.out.println("2. Gestão de Alimentos e Estoque");
        System.out.println("3. Gestão de Instituições Beneficiárias");
        System.out.println("4. Registrar Nova Doação (Entrada)");
        System.out.println("5. Registrar Distribuição (Saída FIFO)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void menuDoadores() {
        System.out.println("\n--- GESTÃO DE DOADORES ---");
        System.out.println("1. Cadastrar Pessoa Física");
        System.out.println("2. Cadastrar Empresa");
        System.out.println("3. Listar Doadores");
        System.out.print("Opção: ");
        int op = Integer.parseInt(scanner.nextLine());

        switch (op) {
            case 1 -> {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Telefone: ");
                String tel = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("CPF: ");
                String cpf = scanner.nextLine();
                
                PessoaFisica pf = new PessoaFisica(nome, tel, email, cpf);
                fachada.cadastrarDoador(pf);
                System.out.println("Pessoa Física cadastrada com sucesso!");
            }
            case 2 -> {
                System.out.print("Nome Fantasia: ");
                String nome = scanner.nextLine();
                System.out.print("Telefone: ");
                String tel = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("CNPJ: ");
                String cnpj = scanner.nextLine();
                System.out.print("Razão Social: ");
                String rs = scanner.nextLine();

                Empresa emp = new Empresa(nome, tel, email, cnpj, rs);
                fachada.cadastrarDoador(emp);
                System.out.println("Empresa cadastrada com sucesso!");
            }
            case 3 -> {
                System.out.println("\n--- Lista de Doadores ---");
                List<Doador> doadores = fachada.listarDoadores();
                if (doadores == null || doadores.isEmpty()) {
                    System.out.println("Nenhum doador cadastrado até o momento.");
                } else {
                    for (Doador d : doadores) {
                        System.out.println(d);
                    }
                }
            }
        }
    }

    private static void menuRegistrarDoacao() {
        System.out.println("\n--- REGISTRAR DOAÇÃO (ENTRADA) ---");
        System.out.print("Documento do Doador (CPF/CNPJ): ");
        String doc = scanner.nextLine();
        Doador doador = fachada.buscarDoadorPorDocumento(doc);

        Doacao doacao = new Doacao(doador);
        boolean adicionando = true;

        while (adicionando) {
            System.out.print("Nome do Alimento: ");
            String nomeAlimento = scanner.nextLine();
            Alimento alimento = fachada.buscarAlimentoPorNome(nomeAlimento);

            System.out.print("Quantidade doada (" + alimento.getUnidadeMedida().getSigla() + "): ");
            double qtd = Double.parseDouble(scanner.nextLine());

            System.out.print("Data de Validade (AAAA-MM-DD): ");
            String dataStr = scanner.nextLine();
            java.time.LocalDate val = java.time.LocalDate.parse(dataStr);

            doacao.adicionarItem(new ItemDoacao(alimento, qtd, val));

            System.out.print("Deseja adicionar mais um alimento a esta doação? (S/N): ");
            String resp = scanner.nextLine();
            if (!resp.equalsIgnoreCase("S")) adicionando = false;
        }

        fachada.registrarDoacao(doacao);
        System.out.println("Doação registrada e estoque atualizado com sucesso!");
    }
}