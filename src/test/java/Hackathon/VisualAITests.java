package Hackathon;

import APP_Pages.Home_Page;
import APP_Pages.Login_Page;
import com.applitools.eyes.*;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class VisualAITests {
	private static Login_Page loginPage = new Login_Page();
	private static Home_Page homePage = new Home_Page();

	//Browsers
	//private String url = "https://demo.applitools.com/hackathon.html";
	private String url = "https://demo.applitools.com/hackathonV2.html";

	private EyesRunner runner;
	private Eyes eyes;
	private static BatchInfo batch;
	private WebDriver driver;
	private String testName;

	@BeforeClass
	public static void setBatch() {
		// Must be before ALL tests (at Class-level)
		batch = new BatchInfo("Hackathon");
	}

	@Before
	public void beforeEach() {
		// Initialize the Runner for your test.
		runner = new ClassicRunner();
		// Initialize the eyes SDK
		eyes = new Eyes(runner);
		// set batch name
		eyes.setBatch(batch);
		// Set your personal Applitols API Key from your environment variables.
		eyes.setApiKey("sTWN101kOeWsriz4111Q2aqK7103rZP5Ysld1P42xTr6NoEXc110");
		// Use Chrome browser
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/driver/chromedriver.exe");
		driver = new ChromeDriver();
		eyes.open(driver, "Hackathon 2019 App", testName, new RectangleSize(800, 800));
	}

	@Rule
	public TestWatcher watcher = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			testName = description.getDisplayName();
		}
	};

	@Test
	public void Test_1_Login_Page_UI_Validation() {
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		eyes.checkWindow("Login Page");
		eyes.closeAsync();
	}

	@Test
	public void Test_2_Login_Boundary_No_Credentials(){
		driver.get(url);
		login("", "");
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		eyes.checkWindow("Login With No Credentials");
		eyes.closeAsync();
	}

	@Test
	public void Test_2_Login_Boundary_Username_Only(){
		driver.get(url);
		login("Mamunnyc", "");
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		eyes.checkWindow("Login With Username Only");
		eyes.closeAsync();
	}

	@Test
	public void Test_2_Login_Boundary_Password_Only(){
		driver.get(url);
		login("", "March320");
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		eyes.checkWindow("Login With Password Only");
		eyes.closeAsync();
	}

	@Test
	public void Test_2_Login_Boundary_Both_Credentials(){
		driver.get(url);
		login("Mamunnyc", "March320");
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		eyes.checkWindow("Login With Both Credentials");
		eyes.closeAsync();
	}

	@Test
	public void Test_3_Table_Sort(){
		driver.get(url);
		login("Mamunnyc", "March320");
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		driver.findElement(By.xpath(homePage.table_amount_column)).click();
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		eyes.checkWindow("Table Sort");
		eyes.closeAsync();
	}

	@Test
	public void Test_4_Canvas_Chart(){
		driver.get(url);
		login("Mamunnyc", "March320");
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		driver.findElement(By.xpath(homePage.show_expense_button)).click();
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		eyes.checkWindow("Canvas Chart");
		eyes.closeAsync();
	}

	@Test
	public void Test_5_Flash_Sale_Gif(){
		//String url_ad = "https://demo.applitools.com/hackathon.html?showAd=true";
		String url_ad = "https://demo.applitools.com/hackathonV2.html?showAd=true";
		driver.get(url_ad);
		login("Mamunnyc", "March320");
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		eyes.checkWindow("Flash Sale Gif");
		eyes.closeAsync();
	}

	@After
	public void afterEach() {
		driver.quit();
		eyes.abortIfNotClosed();
		TestResultsSummary allTestResults = runner.getAllTestResults();
		System.out.println(allTestResults);
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
}
