import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// clean test -Dtest=HWCase2#appliancesTest -Dbrowser=chrome -Dparams=--start-maximized -DpageLoadStrategy=EAGER
public class HWCase2Test {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(HWCase2Test.class);


    // Чтение передаваемого параметра browser (-Dbrowser)
    String browser = System.getProperty("browser", "chrome");
    String params = System.getProperty("params", "");
    String pageLoadStrategy = System.getProperty("loadstrategy", "NORMAL");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + browser);
        driver = WebDriverFactory.getDriver(browser.toLowerCase(), params.toLowerCase(), pageLoadStrategy.toUpperCase());
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        logger.info("Драйвер стартовал!");
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
        Actions actions = new Actions(driver);
        actions
                .moveToElement(appliancesButton)
                .perform();
        logger.info("Курсор наведен на Бытовая техника");


        By appliancesCategoriesXpath = By.xpath("//div[@class='menu-desktop__submenu menu-desktop__submenu_top']//a[@class='ui-link menu-desktop__first-level']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(appliancesCategoriesXpath));
        List<WebElement> categoriesAppliances = driver.findElements(appliancesCategoriesXpath);
        String appliancesCategoriesText="";
        for (WebElement element : categoriesAppliances) {
            logger.info("WebElement: " + element.getTagName() + " = " + element.getText());
            appliancesCategoriesText+=element.getText();
        }
        Assertions.assertEquals("Техника для кухниТехника для домаКрасота и здоровье", appliancesCategoriesText,  "Подкатегории первого уровня бытовой техники ошибочны");
        logger.info("Подкатегории первого уровня бытовой техники в норме");


        By appliancesСookingXpath = By.xpath("//div[@class='menu-desktop__second-level-wrap']//a[contains(text(),'Приготовление пищи')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(appliancesСookingXpath));
        WebElement appliancesСookingButton = driver.findElement(appliancesСookingXpath);
        actions = new Actions(driver);
        actions
                .moveToElement(appliancesСookingButton)
                .perform();
        logger.info("Курсор наведен на Приготовление пищи");


        By subCategoriesKitchenAppliancesXpath = By.xpath("//div[@class='menu-desktop__second-level-wrap']//a[contains(text(),'Приготовление пищи')]//a[@class='ui-link menu-desktop__popup-link']");
        List<WebElement> subCategoriesKitchenAppliances = driver.findElements(subCategoriesKitchenAppliancesXpath);
        Assertions.assertTrue(subCategoriesKitchenAppliances.size()>5);
        logger.info("Количество подкатегорий Приготовление пищи: " + subCategoriesKitchenAppliances.size() + ", и это таки больше 5");


        By stoveXpath = By.xpath("//div[@class='menu-desktop__second-level-wrap']//a[contains(text(),'Приготовление пищи')]//a[contains(text(),'Плиты')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(stoveXpath));
        WebElement stoveButton = driver.findElement(stoveXpath);
        actions = new Actions(driver);
        actions
                .moveToElement(stoveButton)
                .perform();
        logger.info("Курсор наведен на Плиты");
        stoveButton.click();
        logger.info("Нажата кнопка Плиты");

        By eStoveXpath = By.xpath("//div[@class='subcategory__item-container ']//span[contains(text(),'Плиты электрические')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(eStoveXpath));
        WebElement eStoveButton = driver.findElement(eStoveXpath);
        eStoveButton.click();
        logger.info("Нажата кнопка Плиты электрические");

        By eStoveCountXpath = By.xpath("//span[@class='products-count']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(eStoveCountXpath));
        WebElement eStoveCount = driver.findElement(eStoveCountXpath);
        String eStoveCountText = eStoveCount.getText();
        int stoveCount = Integer.parseInt(eStoveCountText.replaceAll("[^0-9]", ""));
        Assertions.assertTrue(stoveCount>100);
        logger.info("Количество электических печей: " + stoveCount + ", и это больше 100");

    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}