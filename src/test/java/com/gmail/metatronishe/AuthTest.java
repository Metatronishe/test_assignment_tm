package com.gmail.metatronishe;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

import static io.github.bonigarcia.wdm.ChromeDriverManager.*;


public class AuthTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
        getInstance().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    @Before
    public void teardown() {

        driver.get("https://account.templatemonster.com/auth/");
    }

    @Test //create
    public void signUp() {
        WebElement emailField = driver.findElement(By.xpath("//*[@type='email']"));
        String testMail = ("testo1111." + (int) (Math.random() * 1000) + "@i.ua");
        emailField.sendKeys(testMail);
        WebElement continueButton = driver.findElement(By.xpath("//*[@id='id-index-continue-button']/button"));
        continueButton.click();
        WebElement passwordField = driver.findElement(By.xpath("//*[@type='password']"));
        passwordField.sendKeys("testpass");
        WebElement signUpButton = driver.findElement(By.xpath("//*[@id='id-create-new-account']"));
        signUpButton.click();
        WebElement confirmationMail = driver.findElement(By.xpath("//*[@class='not-a-link bold-text word-break__break-all display__block']"));
        String mailUser = confirmationMail.getText();
        Assert.assertEquals(testMail, mailUser);
    }

    @Test //login
    public void signIn() {
        WebElement emailField = driver.findElement(By.xpath("//*[@type='email']"));
        emailField.sendKeys("testo1111@i.ua");
        WebElement continueButton = driver.findElement(By.xpath("//*[@id='id-index-continue-button']/button"));
        continueButton.click();
        WebElement passwordField = driver.findElement(By.xpath("//*[@type='password']"));
        passwordField.sendKeys("testo1111");
        WebElement loginButton = driver.findElement(By.xpath("//*[@id='id-password-login-button']"));
        loginButton.click();
        WebElement profile = driver.findElement(By.xpath("//*[@class='user-account-info__name t1']"));
        String mailUser = profile.getText();
        Assert.assertEquals("testo1111@i.ua", mailUser);

    }

    @Test
    public void loginWithFacebook() {
        WebElement fbbutton = driver.findElement(By.xpath("//*[@id='id-general-facebook-button']"));
        fbbutton.click();
        String parentHandle = driver.getWindowHandle();
        for(String childHandle : driver.getWindowHandles()){
            if (!childHandle.equals(parentHandle)){
                driver.switchTo().window(childHandle);
            }
        }
        WebElement fbsignin = driver.findElement(By.xpath("//*[@class='_50f7']"));
        String fbsigninmessage = fbsignin.getText();
        Assert.assertEquals("TemplateMonster.com", fbsigninmessage);
    }

    @After
    public void tearDown() {

        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public static void tearDownClass() {

        driver.quit();
    }
}
