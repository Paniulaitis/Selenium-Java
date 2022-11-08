import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// clean test -Dtest=HWCase1#appliancesTest -Dbrowser=chrome -Dparams=--start-maximized -DpageLoadStrategy=EAGER
public class HWCase1 {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(HWCase1.class);


    // Чтение передаваемого параметра browser (-Dbrowser)
    String browser = System.getProperty("browser", "chrome");
    String params = System.getProperty("params", "");
    String pageLoadStrategy = System.getProperty("loadstrategy", "NORMAL");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + browser);
        driver = WebDriverFactory.getDriver(browser.toLowerCase(), params.toLowerCase(), pageLoadStrategy.toUpperCase());
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void getTitleTest() {
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");
        // Подождать пока появится заголовок страницы
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.titleContains("DNS – интернет магазин цифровой и бытовой техники по доступным ценам."));
        logger.info("Заголовок страницы: " + driver.getTitle());
    }

    @Test
    public void getURLTest() {
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");
        logger.info("URL страницы: " + driver.getCurrentUrl());
    }

    @Test
    public void getSizeTest() {
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");
        logger.info("Размер окна браузера: " + driver.manage().window().getSize());
    }

    @Test
    public void appliancesTest() {
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By cityXpath = By.xpath("//span[contains(text(),'Всё верно')]");
        wait.until(ExpectedConditions.elementToBeClickable(cityXpath));
        WebElement cityButton = driver.findElement(cityXpath);
        cityButton.click();
        logger.info("Нажата кнопка подтверждения города");

        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(cityXpath)));

        By appliancesXpath = By.xpath("//div[@class='menu-desktop']//a[contains(text(),'Бытовая техника')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(appliancesXpath));
        WebElement appliancesButton = driver.findElement(appliancesXpath);
        appliancesButton.click();
        logger.info("Нажата кнопка Бытовая техника");

        By appliancesTextXpath = By.xpath("//h1[@class='subcategory__page-title']");
        WebElement appliancesText = driver.findElement(appliancesTextXpath);
        String text = appliancesText.getText();
        logger.info("Надпись на странице техники: " + text);
        Assertions.assertTrue(text.equals("Бытовая техника"));

        By kitchenAppliancesXpath = By.xpath("//span[contains(text(),'Техника для кухни')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(kitchenAppliancesXpath));
        WebElement kitchenAppliancesButton = driver.findElement(kitchenAppliancesXpath);
        kitchenAppliancesButton.click();
        logger.info("Нажата кнопка Техника для кухни");

        By kitchenAppliancesTextXpath = By.xpath("//h1[@class='subcategory__page-title']");
        WebElement kitchenAppliancesText = driver.findElement(kitchenAppliancesTextXpath);
        text = kitchenAppliancesText.getText();
        logger.info("Надпись на странице кухни: " + text);
        Assertions.assertTrue(text.equals("Техника для кухни"));

        By assembleYourKitchenXpath = By.xpath("//a[@class='button-ui button-ui_white configurator-links-block__links-link']");
        WebElement assembleYourKitchenText = driver.findElement(assembleYourKitchenXpath);
        logger.info("Кнопка Собрать свою кухню найдена");
        Assertions.assertTrue(true);


        By categoriesKitchenAppliancesXpath = By.xpath("//div[@class='subcategory__item-container ']//a");
        List<WebElement> categoriesKitchenAppliances = driver.findElements(categoriesKitchenAppliancesXpath);
        for (WebElement element : categoriesKitchenAppliances) {
            logger.info("WebElement: " + element.getTagName() + " = " + element.getText());
        }
        logger.info("Количество кухонных категорий: " + categoriesKitchenAppliances.size());
        Assertions.assertTrue(categoriesKitchenAppliances.size()>5);

    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}