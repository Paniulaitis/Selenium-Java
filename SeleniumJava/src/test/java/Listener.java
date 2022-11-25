import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.WebDriverListener;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Listener implements WebDriverListener {

    String months[] = {"Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен",
            "Окт", "Ноя", "Дек"};

    String pathName = "";

    private Logger logger = LogManager.getLogger(Listener.class);

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        pathName = "temp\\" + months[gCalendar.get(Calendar.MONTH)]+ " " + gCalendar.get(Calendar.DATE) + " " +
                gCalendar.get(Calendar.HOUR) + "-" + gCalendar.get(Calendar.MINUTE) + "-" + gCalendar.get(Calendar.SECOND) + ".png";
        logger.info("Найден веб элемент " + locator);

        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File(pathName));
            logger.info("Скриншот сохранен в файле " + pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Actions(driver)
                .moveToElement(result)
                .perform();
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        pathName = "temp\\" + months[gCalendar.get(Calendar.MONTH)] + " " + gCalendar.get(Calendar.YEAR) + " " +
                gCalendar.get(Calendar.HOUR) + "-" + gCalendar.get(Calendar.MINUTE) + "-" + gCalendar.get(Calendar.SECOND) + ".png";
        logger.info("Найдены веб элементы " + locator);

        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File(pathName));
            logger.info("Скриншот сохранен в файле " + pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterGetText(WebElement element, String result){
        logger.info("Получен текст" + result);
    }


    @Override
    public void afterClick(WebElement element) {
        logger.info("Нажата кнопка");
    }
}