package bot.suap.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    public static WebDriver create() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
