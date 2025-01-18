package com.jp.literAlura.principal;

import com.jp.literAlura.model.Livro;
import com.jp.literAlura.service.LivroService;
import com.jp.literAlura.service.IdiomaService;
import com.jp.literAlura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static org.springframework.core.io.support.PropertiesLoaderUtils.loadProperties;

@Component
public class Principal implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private static IdiomaService idiomaService;

    private static Scanner leitura = new Scanner(System.in);

    public Principal(LivroService livroService, AutorService autorService, IdiomaService idiomaService) {
        this.livroService = livroService;
        this.autorService = autorService;
        this.idiomaService = idiomaService;
    }

    public void exibeMenu() throws SQLException {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma

                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    submenuListarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o livro que deseja buscar");
        String livro = leitura.nextLine();
        livroService.saveLivro(livro);
    }

    private void listarLivrosRegistrados() {
        System.out.println("Listando livros: ");
        livroService.listBooks().forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        System.out.println("Listando autores: ");
        autorService.listAuthors().forEach(System.out::println);
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Digite o ano do Autor que deseja saber");
        try {
            Integer ano = leitura.nextInt();
            leitura.nextLine();
            autorService.listAuthorsAliveInSpecifYear(ano).forEach(System.out::println);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Digite somente números");
        }
    }

    private void submenuListarLivrosPorIdioma() throws SQLException {
        Scanner scanner = new Scanner(System.in); // Cria uma instância de Scanner local

        while (true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Espanhol (es)");
            System.out.println("2 - Inglês (en)");
            System.out.println("3 - Francês (fr)");
            System.out.println("4 - Português (pt)");
            System.out.println("0 - Voltar");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer de entrada

            String idioma = "";
            switch (opcao) {
                case 1:
                    idioma = "es";
                    break;
                case 2:
                    idioma = "en";
                    break;
                case 3:
                    idioma = "fr";
                    break;
                case 4:
                    idioma = "pt";
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
                    continue;
            }

            // Chamar o método de serviço para listar livros pelo idioma
            List<Livro> livros = listarLivrosPorIdioma(idioma);
            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado para o idioma: " + idioma);
            } else {
                for (Livro livro : livros) {
                    System.out.println(livro);
                }
            }
        }
    }

    private List<Livro> listarLivrosPorIdioma(String idioma) {
        // Chamar o método do serviço que faz a busca pelo idioma
        return livroService.listBooksByLanguage(idioma);
    }


    @Override
    public void run(String... args) throws Exception {
        exibeMenu();
    }
}