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
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

// clean test -Dtest=HWCase2Test#appliancesTest -Dbrowser=chrome -Dparams=--start-maximized -DpageLoadStrategy=EAGER
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

        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\Открыта страница dns-shop.png"));
            logger.info("Скриншот сохранен в файле [temp\\Открыта страница dns-shop.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By cityXpath = By.xpath("//span[contains(text(),'Всё верно')]");
        wait.until(ExpectedConditions.elementToBeClickable(cityXpath));
        WebElement cityButton = driver.findElement(cityXpath);
        new Actions(driver)
                .scrollToElement(cityButton)
                .perform();
        cityButton.click();
        logger.info("Нажата кнопка подтверждения города");

        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\Нажата кнопка подтверждения города.png"));
            logger.info("Скриншот сохранен в файле [temp\\Нажата кнопка подтверждения города.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(cityXpath)));

        By appliancesXpath = By.xpath("//div[@class='menu-desktop']//a[contains(text(),'Бытовая техника')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(appliancesXpath));
        WebElement appliancesButton = driver.findElement(appliancesXpath);
        new Actions(driver)
                .moveToElement(appliancesButton)
                .perform();
        logger.info("Курсор наведен на Бытовая техника");

        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\Курсор наведен на Бытовая техника.png"));
            logger.info("Скриншот сохранен в файле [temp\\Курсор наведен на Бытовая техника.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Actions(driver)
                .scrollToElement(appliancesButton)
                .perform();


        By appliancesCategoriesXpath = By.xpath("//div[@class='menu-desktop__submenu menu-desktop__submenu_top']//a[@class='ui-link menu-desktop__first-level']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(appliancesCategoriesXpath));
        List<WebElement> categoriesAppliances = driver.findElements(appliancesCategoriesXpath);
        String appliancesCategoriesText="";
        for (WebElement element : categoriesAppliances) {
            logger.info("WebElement: " + element.getTagName() + " = " + element.getText());
            appliancesCategoriesText+=element.getText();
        }
        Assertions.assertEquals("Встраиваемая техникаТехника для кухниТехника для дома", appliancesCategoriesText,  "Подкатегории первого уровня бытовой техники ошибочны");
        logger.info("Подкатегории первого уровня бытовой техники в норме");


        By appliancesСookingXpath = By.xpath("//div[@class='menu-desktop__second-level-wrap']//a[contains(text(),'Плиты и печи')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(appliancesСookingXpath));
        WebElement appliancesStoveButton = driver.findElement(appliancesСookingXpath);
        new Actions(driver)
                .moveToElement(appliancesStoveButton)
                .perform();
        logger.info("Курсор наведен на Плиты и печи");

        By subCategoriesKitchenAppliancesXpath = By.xpath("//div[@class='menu-desktop__second-level-wrap']//a[contains(text(),'Плиты и печи')]//a[@class='ui-link menu-desktop__popup-link']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(subCategoriesKitchenAppliancesXpath));
        List<WebElement> subCategoriesKitchenAppliances = driver.findElements(subCategoriesKitchenAppliancesXpath);
        Assertions.assertTrue(subCategoriesKitchenAppliances.size()>5);
        logger.info("Количество подкатегорий Плиты и печи: " + subCategoriesKitchenAppliances.size() + ", и это таки больше 5");

        try {
            Screenshot screenshot = new AShot().takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\Курсор наведен на Плиты и печи.png"));
            logger.info("Скриншот сохранен в файле [temp\\Курсор наведен на Плиты и печи.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        appliancesStoveButton.click();
        logger.info("Нажата кнопка Плиты и печи");

        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\Нажата кнопка Плиты и печи.png"));
            logger.info("Скриншот сохранен в файле [temp\\Нажата кнопка Плиты и печи.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        By eStoveXpath = By.xpath("//div[@class='subcategory__item-container ']//span[contains(text(),'Плиты электрические')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(eStoveXpath));
        WebElement eStoveButton = driver.findElement(eStoveXpath);
        new Actions(driver)
                .scrollToElement(eStoveButton)
                .perform();
        eStoveButton.click();
        logger.info("Нажата кнопка Плиты электрические");

        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\Нажата кнопка Плиты электрические.png"));
            logger.info("Скриншот сохранен в файле [temp\\Нажата кнопка Плиты электрические.png]");
        } catch (IOException e) {
            e.printStackTrace();
        }

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