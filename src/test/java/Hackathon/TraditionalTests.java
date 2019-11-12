package Hackathon;

import APP_Pages.Home_Page;
import APP_Pages.Login_Page;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TraditionalTests {

    private static WebDriver driver;

    //Pages
    private Login_Page loginPage = PageFactory.initElements(driver, Login_Page.class);
    private Home_Page homePage = PageFactory.initElements(driver, Home_Page.class);

    //Browsers
    //private String url = "https://demo.applitools.com/hackathon.html";
    private String url = "https://demo.applitools.com/hackathonV2.html";

    @BeforeSuite
    public void setUp_chrome(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/driver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        String currentWindowHandle = driver.getWindowHandle();
        ((JavascriptExecutor)driver).executeScript("alert('Test')");
        driver.switchTo().alert().accept();
        driver.switchTo().window(currentWindowHandle);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void Test_1_Login_Page_UI_Validation()throws Exception{
        driver.get(url);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        compare_page_images("Login_Page");
        String LP_header_text = get_text(loginPage.LP_header);
        Assert.assertEquals("Login Form", LP_header_text);
        String username_label = get_text(loginPage.LP_username_label);
        Assert.assertEquals("Username", username_label);
        String password_label = get_text(loginPage.LP_password_label);
        Assert.assertEquals("Password", password_label);
        String remember_me_label = get_text(loginPage.LP_remember_me_label);
        Assert.assertEquals("Remember Me", remember_me_label);
        String username_placeholder_text = get_element_placeholder(loginPage.LP_username_input_field);
        Assert.assertEquals("Enter your username", username_placeholder_text);
        String username_password_text = get_element_placeholder(loginPage.LP_password_input_field);
        Assert.assertEquals("Enter your password", username_password_text);
        boolean LP_logo_visibility = get_element_visibility(loginPage.LP_image);
        Assert.assertTrue(LP_logo_visibility, "Element is NOT Visible");
        boolean LP_Username_image_visibility = get_element_visibility(loginPage.LP_username_logo);
        Assert.assertTrue(LP_Username_image_visibility, "Element is NOT Visible");
        boolean LP_password_image_visibility = get_element_visibility(loginPage.LP_password_logo);
        Assert.assertTrue(LP_password_image_visibility, "Element is NOT Visible");
        boolean LP_remember_me_image_visibility = get_element_visibility(loginPage.LP_remember_me_check_box);
        Assert.assertTrue(LP_remember_me_image_visibility, "Element is NOT Visible");
        boolean LP_twitter_image_visibility = get_element_visibility(loginPage.LP_twitter_image);
        Assert.assertTrue(LP_twitter_image_visibility, "Element is NOT Visible");
        boolean LP_facebook_image_visibility = get_element_visibility(loginPage.LP_facebook_image);
        Assert.assertTrue(LP_facebook_image_visibility, "Element is NOT Visible");
        boolean LP_linkedin_image_visibility = get_element_visibility(loginPage.LP_linkedin_image);
        Assert.assertTrue(LP_linkedin_image_visibility, "Element is NOT Visible");
    }

    @Test
    public void Test_2_Login_Boundary_No_Credentials()throws Exception{
        driver.get(url);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        login("", "");
        compare_page_images("After_No_Credentials");
        if (!login_successful(loginPage.LP_alert_warning)) {
            String error_message = get_text(loginPage.LP_alert_warning);
            Assert.assertEquals("Both Username and Password must be present", error_message);
        } else {
            Assert.fail();
        }
    }

    @Test
    public void Test_2_Login_Boundary_Username_Only()throws Exception{
        driver.get(url);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        login("Mamunnyc", "");
        compare_page_images("After_Only_Username");
        if (!login_successful(loginPage.LP_alert_warning)) {
            String error_message = get_text(loginPage.LP_alert_warning);
            Assert.assertEquals("Password must be present", error_message);
        } else {
            Assert.fail();
        }
    }

    @Test
    public void Test_2_Login_Boundary_Password_Only()throws Exception{
        driver.get(url);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        login("", "March320");
        compare_page_images("After_Only_Password");
        if (!login_successful(loginPage.LP_alert_warning)) {
            String error_message = get_text(loginPage.LP_alert_warning);
            Assert.assertEquals("Username must be present", error_message);
        } else {
            Assert.fail();
        }
    }

    @Test
    public void Test_2_Login_Boundary_Both_Credentials()throws Exception{
        driver.get(url);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        login("Mamunnyc", "March320");
        compare_page_images("After_Both_Credentials");
        if (login_successful(loginPage.LP_alert_warning)){
            System.out.println("Logged in Successfully");
        }else {
            Assert.fail();
        }
    }

    @Test
    public void Test_3_Table_Sort()throws Exception{
        driver.get(url);
        login("Mamunnyc", "March320");
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        driver.findElement(By.xpath(homePage.table_amount_column)).click();
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        compare_page_images("Table_Sort");
        ArrayList<Double> numbers = new ArrayList<>();
        int row_count = driver.findElements(By.xpath(homePage.table_amount_column_values)).size();
        for (int i = 1; i < row_count+1; i++){
            String dollar_amount = get_text("("+homePage.table_amount_column_values+")["+i+"]");
            String[] splitting = dollar_amount.replace(",", "").split(" ");
            numbers.add(Double.parseDouble(splitting[0]+splitting[1].trim()));
        }
        boolean sorted = true;
        for ( int i = 0; i <numbers.size() - 1; i++){
            if (numbers.get(i) > numbers.get(i + 1)){
                sorted = false;
            }
            Assert.assertTrue(sorted, "Numbers are NOT Sorted");
        }
    }

    @Test
    public void Test_4_Canvas_Chart()throws Exception{
        driver.get(url);
        login("Mamunnyc", "March320");
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        driver.findElement(By.xpath(homePage.show_expense_button)).click();
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        compare_page_images("Canvas_Chart");
}

    @Test
    public void Test_5_Flash_Sale_Gif()throws Exception{
        //String url_ad = "https://demo.applitools.com/hackathon.html?showAd=true";
        String url_ad = "https://demo.applitools.com/hackathonV2.html?showAd=true";
        driver.get(url_ad);
        login("Mamunnyc", "March320");
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        compare_page_images("Flash_Sale");
        int flash_sale_count = driver.findElements(By.xpath(homePage.flash_sale_gifs)).size();
        if (flash_sale_count !=0){
            for (int i = 1; i < flash_sale_count+1; i++){
                boolean isDisplayed = driver.findElement(By.xpath("("+homePage.flash_sale_gifs+")["+i+"]")).isDisplayed();
                Assert.assertTrue(isDisplayed, "Flash Sale Ad Exists but NOT Displayed");
            }
        }else {
            Assert.fail("Flash Sale Ads don't Exists");
        }
    }

    @AfterSuite
    public static void cleanUp(){
        driver.quit();
        driver = null;
    }

    //Get text
    private String get_text(String xpath_locator){
        WebElement element = driver.findElement(By.xpath(xpath_locator));
        String text = null;
        try {
            text =  element.getText();
        }catch (Exception e){
            e.printStackTrace();
        }
        return text;
    }

    private String get_element_placeholder(String xpath_locator){
        String attribute_value;
        WebElement element = driver.findElement(By.xpath(xpath_locator));
        attribute_value = element.getAttribute("placeholder");
        return attribute_value;
    }

    private void login(String username, String password){
        driver.findElement(By.xpath(loginPage.LP_username_input_field)).clear();
        driver.findElement(By.xpath(loginPage.LP_password_input_field)).clear();
        driver.findElement(By.xpath(loginPage.LP_username_input_field)).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), username);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath(loginPage.LP_password_input_field)).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), password);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath(loginPage.LP_login_button)).click();
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
    }

    private boolean login_successful(String xpath_locator){
        boolean login_successful = false;
        int alert_waring_count = driver.findElements(By.xpath(xpath_locator)).size();
        if (alert_waring_count !=0){
            System.out.println("Login Unsuccessful");
        }else {
            System.out.println("Login Successful");
            login_successful = true;
        }
        return login_successful;
    }

    private boolean get_element_visibility(String xpath_locator){
        boolean element_visible;
        WebElement element = driver.findElement(By.xpath(xpath_locator));
        element_visible = element.isDisplayed();
        return element_visible;
    }

    /*private void get_page_screenshot(String screenshot_name){
        deleteFile(System.getProperty("user.dir")+"/Traditional_Comparison_Images/"+screenshot_name+"_Expected.png");
        String image_path = System.getProperty("user.dir")+"/Traditional_Comparison_Images/";
        Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE, 500, true).withName(screenshot_name+"_Expected").save(image_path);
    }*/

    private void compare_page_images(String screenshot_name)throws Exception{
        deleteFile(System.getProperty("user.dir")+"/Traditional_Comparison_Images/"+screenshot_name+"_Actual.png");
        File image = new File(System.getProperty("user.dir")+"/Traditional_Comparison_Images/"+screenshot_name+"_Expected.png");
        BufferedImage expected_image = ImageIO.read(image);
        String image_path = System.getProperty("user.dir")+"/Traditional_Comparison_Images/";
        boolean image_same = Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE, 500, true).withName(screenshot_name+"_Actual").equalsWithDiff(expected_image, image_path+screenshot_name+"_Actual", 0);
        Assert.assertTrue(image_same, "Page Images Are NOT Same");
    }

    private void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            Files.deleteIfExists(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
