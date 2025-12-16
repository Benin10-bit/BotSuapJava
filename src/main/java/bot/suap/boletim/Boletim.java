package bot.suap.boletim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Boletim {
    private WebDriver driver;

    public Boletim(WebDriver driver){
        this.driver = driver;
    }

    public void acessar(String matricula){
        String url = String.format("https://suap.ifrn.edu.br/edu/aluno/%s/?tab=boletim",matricula);
        driver.get(url);
    }

    public List<List<String>> listarNotas() {
        List<List<String>> texto = new ArrayList<>();
        List<WebElement> linhas = driver.findElements(By.tagName("tr"));

        for (WebElement tr : linhas) {
            List<WebElement> colunas = tr.findElements(By.tagName("td"));
            List<String> linhaporMateria = new ArrayList<>();

            for (WebElement td : colunas) {
                String tdText = td.getText().trim();
                String header = td.getAttribute("headers");

                if (tdText.startsWith("TIN.")) {
                    linhaporMateria.add(tdText);
                }

                switch (header) {
                    case "th_n1n" -> linhaporMateria.add("nota1: " + tdText);
                    case "th_n2n" -> linhaporMateria.add("nota2: " + tdText);
                    case "th_n3n" -> linhaporMateria.add("nota3: " + tdText);
                    case "th_n4n" -> linhaporMateria.add("nota4: " + tdText);
                }
            }

            if (!linhaporMateria.isEmpty()) {
                texto.add(linhaporMateria);
            }
        }

        return texto;
    }
}
