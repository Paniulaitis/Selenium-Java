import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    // Возврат драйвера для конкретного браузера по его названию
    public static WebDriver getDriver(String browserName, String params, String pageLoadStrategy) {
        switch (browserName) {
             // Создание драйвера для браузера Mozilla Firefox
            case "firefox" :
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (params != "")
                {
                    firefoxOptions.addArguments(params);
                }
                else {
                    firefoxOptions.addArguments("-private");
                    firefoxOptions.addArguments("--kiosk");
                }

                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.valueOf(pageLoadStrategy.toUpperCase()));

                return new FirefoxDriver(firefoxOptions);

            default:
                // Создание драйвера для браузера Google Chrome
                WebDriverManager.chromedriver().setup();
                logger.info("Драйвер для браузера Google Chrome");

                // Добавление свойств браузера Google Chrome (настройки сессии)
                ChromeOptions chromeOptions = new ChromeOptions();

                // Добавление аргументов запуска Google Chrome
                if (params != "")
                {
                    chromeOptions.addArguments(params);
                }
                else {
                    chromeOptions.addArguments("--start-fullscreen");
                    chromeOptions.addArguments("--incognito");
                }

                chromeOptions.setPageLoadStrategy(PageLoadStrategy.valueOf(pageLoadStrategy.toUpperCase()));

                return new ChromeDriver(chromeOptions);
        }
    }
}
