package rahulshettyacademy.SeleniumFrameworkDesign;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.PageObjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
	String productname="ZARA COAT 3";
		WebDriverManager.firefoxdriver().setup();
		
		WebDriver driver= new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		LandingPage landingpage=new LandingPage(driver);
		landingpage.goTo();
		landingpage.loginpplication("shishi@example.com", "Shishira1!");
		
		List<WebElement> elements=driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod=elements.stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals(productname)).findFirst().orElse(null);
		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		List<WebElement> cartproducts=driver.findElements(By.cssSelector(".cartSection h3"));
		
		Boolean match=   cartproducts.stream().anyMatch(cartproduct-> cartproduct.getText().equalsIgnoreCase("zara coat 3"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		Assert.assertTrue(match);
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions act = new Actions(driver);
		act.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("//button[contains(@class,ta-item)][2]")).click();
		
		driver.findElement(By.cssSelector(".action__submit")).click();
		String confirmmessage=driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmmessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		System.out.print("Order completed");
		driver.close();
	}
		
		
		

	}


