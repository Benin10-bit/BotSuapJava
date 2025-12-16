package bot.suap;
import bot.suap.boletim.Boletim;
import bot.suap.driver.DriverFactory;
import bot.suap.loginPage.LoginPage;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void gerarRelatorio(List<List<String>> listaNotas) {
        for (List<String> disciplina : listaNotas) {
            if (disciplina.isEmpty()) continue;

            String nomeDisciplina = disciplina.get(0);
            List<Double> notas = new ArrayList<>();
            List<String> notasFaltando = new ArrayList<>();

            for (int i = 1; i < disciplina.size(); i++) {
                String coluna = disciplina.get(i).trim();

                if (coluna.equals("-")) {
                    notasFaltando.add(coluna);
                } else {
                    String[] partes = coluna.split(":");
                    if (partes.length == 2) {
                        try {
                            notas.add(Double.parseDouble(partes[1].trim()));
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }

            double soma = 0;
            double pesoTotal = 0;

            if (!notas.isEmpty()) { soma += notas.get(0) * 2; pesoTotal += 2; }
            if (notas.size() > 1) { soma += notas.get(1) * 2; pesoTotal += 2; }
            if (notas.size() > 2) { soma += notas.get(2) * 3; pesoTotal += 3; }
            if (notas.size() > 3) { soma += notas.get(3) * 3; pesoTotal += 3; }

            double media = pesoTotal > 0 ? soma / pesoTotal : 0;

            String observacao;
            if (media >= 90) observacao = "Excelente desempenho!";
            else if (media >= 80) observacao = "Bom desempenho.";
            else if (media >= 70) observacao = "Desempenho regular.";
            else observacao = "Precisa melhorar.";

            System.out.println("Disciplina: " + nomeDisciplina);
            System.out.println("Notas registradas: " + notas);
            if (!notasFaltando.isEmpty()) {
                System.out.println("Notas faltando: " + notasFaltando);
            }
            System.out.printf("Média: %.2f%n", media);
            System.out.println("Observação: " + observacao);
            System.out.println("-----------------------------------");
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite sua matrícula:");
        String matricula = input.nextLine();

        System.out.println("Digite sua senha:");
        String senha = input.nextLine();

        WebDriver driver = DriverFactory.create();

        try {
            LoginPage login = new LoginPage(driver);
            Boletim boletim = new Boletim(driver);

            login.acessar();
            login.login(matricula, senha);

            boletim.acessar(matricula);
            List<List<String>> notas = boletim.listarNotas();

            gerarRelatorio(notas);
        } finally {
            driver.quit();
        }
    }
}
