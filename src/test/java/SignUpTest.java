import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.*;


public class SignUpTest {

    @Test
    public void zipCode5Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //driver.navigate().refresh();
        driver.get("https://sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("11111");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        boolean isSignUpPageOpened = driver.findElement(By.cssSelector("input[value=Register]")).isDisplayed();
        assertTrue(isSignUpPageOpened, "Page is not opened");
        driver.quit();
    }

    @Test
    public void zipCode4Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("2222");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError,
                "Oops, error on page. ZIP code should have 5 digits",
                "Wrong error message");
        driver.quit();
    }

    @Test
    public void zipCode6Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("111111");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        String actualError = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualError,
                "Oops, error on page. ZIP code should have 5 digits",
                "Wrong error message");
        driver.quit();
    }



    @Test
    public void successfulSignUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Nick");
        driver.findElement(By.name("last_name")).sendKeys("Click");
        driver.findElement(By.name("email")).sendKeys("test@test.te");
        driver.findElement(By.name("password1")).sendKeys("qwerty");
        driver.findElement(By.name("password2")).sendKeys("qwerty");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualConfirmation = driver.findElement(By.cssSelector("span[class=confirmation_message]")).getText();
        assertEquals(actualConfirmation,
                "Account is created!",
                "Wrong error message");
        driver.quit();
    }


    @Test
    public void negativeSuccessfulSignUpFirstName() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("");
        driver.findElement(By.name("last_name")).sendKeys("Click");
        driver.findElement(By.name("email")).sendKeys("test@test.te");
        driver.findElement(By.name("password1")).sendKeys("qwerty");
        driver.findElement(By.name("password2")).sendKeys("qwerty");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String negActualConfirmation = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(negActualConfirmation,
                "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "Wrong error message");
        driver.quit();
    }

}
