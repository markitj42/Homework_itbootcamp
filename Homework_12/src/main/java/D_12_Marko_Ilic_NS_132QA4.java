import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class D_12_Marko_Ilic_NS_132QA4 {
    protected static WebDriver driver;

    public static void main(String[] args)  {

        driverSetup(DriverSetup.CHROME);
        driver.get("https://www.wikipedia.org/");
        setWindowsProperties();
        selectLanguageToSrb();
        searchForJava();
        titleCheck("Java (програмски језик)");
        driver.close();

    }
    private static void selectLanguageToSrb() {
        Select dropdown = new Select(driver.findElement(By.id("searchLanguage")));
        dropdown.selectByVisibleText("Српски / Srpski");
        System.out.println("Srpski jezik je selektovan");
    }

    private static void searchForJava() {
        String optionToSelect = "Java (програмски језик)";
        driver.findElement(By.id("searchInput")).sendKeys("Java");

        WebElement suggestions = driver.findElement(By.xpath("//*[@id=\"typeahead-suggestions\"]/div"));
        List<WebElement> suggestionTitles = suggestions.findElements(By.className("suggestion-link"));

        for (WebElement element : suggestionTitles) {
            String currentOption = element.getText();
            //System.out.println("this is element: " + element);
            if (currentOption.contains(optionToSelect)) {
                //System.out.println("this is current option: " + currentOption);
                element.click();
                System.out.println(optionToSelect + " is selected");
                break;
            }
        }

    }

    public static String getWikiTitle() {
        WebElement wikiTitle = driver.findElement(By.xpath("//*[@id=\"firstHeading\"]"));
        return wikiTitle.getText();
    }

    public static void titleCheck(String title) {
        if (title.equals(getWikiTitle())) {
            System.out.println("Title is valid");
        } else {
            System.out.println("Title is not valid");
        }
    }

    private static void driverSetup(DriverSetup driverSetup) {
        switch (driverSetup) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            case EDGE -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            default -> System.out.println("Driver is not supported");
        }
    }

    public static void setWindowsProperties() {
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
