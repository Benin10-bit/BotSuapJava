package bot.suap.loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    public void acessar(){
        driver.get("https://suap.ifrn.edu.br/accounts/login/?next=/");
    }
    public void login(String matricula, String senha){
        driver.findElement(By.id("id_username")).sendKeys(matricula);
        driver.findElement(By.id("id_password")).sendKeys(senha);
        driver.findElement(By.className("success")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("nav")));
    }

}
