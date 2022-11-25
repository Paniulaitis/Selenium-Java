import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
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

// clean test -Dtest=HWCase3Test#appliancesTest -Dbrowser=chrome -Dparams=--start-maximized -DpageLoadStrategy=EAGER
public class HWCase3Test {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(HWCase3Test.class);


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

        Listener listener = new Listener();
        WebDriver eventFiringWebDriver = new EventFiringDecorator<>(listener).decorate(driver);

        eventFiringWebDriver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        By pCXpath = By.xpath("//div[@class='menu-desktop']//a[contains(text(),'ПК, ноутбуки, периферия')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pCXpath));
        WebElement pCButton = eventFiringWebDriver.findElement(pCXpath);
        new Actions(eventFiringWebDriver)
                .moveToElement(pCButton)
                .perform();
        logger.info("Курсор наведен на ПК, ноутбуки, периферия");


        By notebooksXpath = By.xpath("//div[@class='menu-desktop__second-level-wrap']//a[contains(text(),'Ноутбуки')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(notebooksXpath));
        WebElement notebooksButton = eventFiringWebDriver.findElement(notebooksXpath);
        new Actions(eventFiringWebDriver)
                .moveToElement(pCButton)
                .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(notebooksXpath));
        notebooksButton.click();
        logger.info("Нажата кнопка Ноутбуки");


        By headerXpath = By.xpath("//header");
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerXpath));
        WebElement header = eventFiringWebDriver.findElement(headerXpath);
        JavascriptExecutor js = (JavascriptExecutor) eventFiringWebDriver;
        String script = "arguments[0].style.display='none';";
        js.executeScript(script, header);
        logger.info("Хедер скрыт");


        By asusXpath = By.xpath("//label[@class='ui-checkbox ui-checkbox_list']//span[contains(text(),'ASUS  ')]");

        new Actions(eventFiringWebDriver)
                .scrollByAmount(0, 500)
                .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(asusXpath));
        WebElement asusCheckBox = eventFiringWebDriver.findElement(asusXpath);

        asusCheckBox.click();
        logger.info("Установлен фильтр asus");

        new Actions(eventFiringWebDriver)
                .scrollByAmount(0, 1000)
                .perform();

        By ramXpath = By.xpath("//span[@class='ui-collapse__link-text'][contains(text(),'Объем оперативной памяти (ГБ)')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ramXpath));
        WebElement ramButton = eventFiringWebDriver.findElement(ramXpath);
        ramButton.click();
        logger.info("Раскрыто подменю фильтра оперативной памяти");

        By ram32Xpath = By.xpath("//label[@class='ui-checkbox ui-checkbox_list']//span[contains(text(),'32 ГБ  ')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ram32Xpath));
        WebElement ram32Button = eventFiringWebDriver.findElement(ram32Xpath);
        ram32Button.click();
        logger.info("Установлен фильтр оперативной памяти 32гб");

        By showXpath = By.xpath("//button[@class='button-ui button-ui_brand left-filters__button'][contains(text(),'Применить')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(showXpath));
        WebElement showButton = eventFiringWebDriver.findElement(showXpath);
        showButton.click();
        logger.info("Применены фильтры");

        By orderXpath = By.xpath("//span[@class='top-filter__selected'][contains(text(),'Сначала недорогие')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderXpath));
        WebElement orderButton = eventFiringWebDriver.findElement(orderXpath);
        orderButton.click();
        logger.info("Раскрыто подменю упорядочивания");

        By expensiveXpath = By.xpath("//span[@class='ui-radio__content'][contains(text(),'Сначала дорогие')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(expensiveXpath));
        WebElement expensiveButton = eventFiringWebDriver.findElement(expensiveXpath);
        expensiveButton.click();
        logger.info("Выбрано Сначала дорогие");

        By productNameXpath = By.xpath("//div[@class='catalog-products view-simple']//div[1]//span[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameXpath));
        new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(productNameXpath));
        String productNameText = driver.findElement(productNameXpath).getText();

        By productXpath = By.xpath("//div[@class='catalog-products view-simple']//div[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(productXpath));
        WebElement productButton = eventFiringWebDriver.findElement(productXpath);
        productButton.click();
        logger.info("Выбран первый товар");

        String currentUrl = eventFiringWebDriver.getCurrentUrl();
        eventFiringWebDriver.switchTo().newWindow(WindowType.WINDOW);
        eventFiringWebDriver.manage().window().maximize();
        eventFiringWebDriver.get(currentUrl);
        logger.info("Страница открыта в новом окне");

        By headXpath = By.xpath("//h1[@class='product-card-top__title']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(headXpath));
        WebElement headLabel = eventFiringWebDriver.findElement(headXpath);
        Assertions.assertTrue(productNameText.contains(headLabel.getText()));
        logger.info("Заголовок соответствет ожидаемому");

        By specificationsHeadXpath = By.xpath("//div[@class='product-card-description__title']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(specificationsHeadXpath));
        WebElement specificationsHead = eventFiringWebDriver.findElement(specificationsHeadXpath);
        Assertions.assertTrue(specificationsHead.getText().contains("ASUS"));
        logger.info("Заголовок характеристик соответствет ожидаемому");

        By uncollapseButtonXpath = By.xpath("//button[@class='button-ui button-ui_white product-characteristics__expand']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(uncollapseButtonXpath));
        WebElement uncollapseButton = eventFiringWebDriver.findElement(uncollapseButtonXpath);
        uncollapseButton.click();
        logger.info("Нажата кнопка развернуть характеристики");

        By uncollapsedSpecificationsXpath = By.xpath("//div[@class='product-characteristics__group-title'][contains(text(),'Внешний вид')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(uncollapsedSpecificationsXpath));
        logger.info("Развернуты характеристики");
        new Actions(eventFiringWebDriver)
                .scrollByAmount(0, 1000)
                .perform();

        By specificationsRamXpath = By.xpath("//div[@class='product-characteristics__spec-title'][contains(text(),' Объем оперативной памяти ')]/following-sibling::div[@class='product-characteristics__spec-value']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(specificationsRamXpath));
        WebElement specificationsRam = eventFiringWebDriver.findElement(specificationsRamXpath);
        Assertions.assertEquals("32 ГБ", specificationsRam.getText(),  "Объем RAM не соответствет ожидаемому");
        logger.info("Объем RAM соответствет ожидаемому");
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}