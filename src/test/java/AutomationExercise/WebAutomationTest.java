package AutomationExercise;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebAutomationTest {
	
	@Test
	public void automationExercise1() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		ChromeDriver driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		
		//Open web and click on sign in button
		driver.get("https://automationexercise.com");
		driver.findElement(By.xpath("//a[contains(@href,'login')]")).click();
		
		//Sign in using valid credential
		driver.findElement(By.name("email")).sendKeys("qat@mailinator.com");
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.xpath("//button[text()='Login']")).click();
		
		//Mapping product name with price
		Map<String, Integer> details = new HashMap<>();
		List<WebElement> products = driver
				.findElements(By.xpath("//div[@class='features_items']//div[contains(@class,'productinfo')]"));
		for (int i = 1; i <= products.size(); i++) {
			details.put(
					driver.findElement(By.xpath(
							"(//div[@class='features_items']//div[contains(@class,'productinfo')]/p)[" + i + "]"))
							.getText(),
					Integer.parseInt(driver.findElement(By.xpath(
							"(//div[@class='features_items']//div[contains(@class,'productinfo')]/h2)[" + i + "]"))
							.getText().split(" ")[1]));
		}
		
		
		//Sort the product by price low to high
		details.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
		
		//Scroll up the screen and add to cart green and white top
		WebElement womenToggleBtn = driver.findElement(By.xpath("//h4[normalize-space()='Women']/a"));
		scrollToMiddle(driver, womenToggleBtn);
		womenToggleBtn.click();
		driver.findElement(By.xpath("//div[@id='Women']//a[normalize-space()='Tops']")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(
				By.xpath("//div[contains(@class,'productinfo')]//p[text()='Fancy Green Top']/following-sibling::a"))
				.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Continue Shopping']")).click();
		js.executeScript("window.scrollBy(0,-350)");
				driver.findElement(
				By.xpath("//div[contains(@class,'productinfo')]//p[text()='Summer White Top']/following-sibling::a"))
				.click();
		Thread.sleep(2000);
		
		//View cart and proceed to checkout
		driver.findElement(By.linkText("View Cart")).click();
		driver.findElement(By.linkText("Proceed To Checkout")).click();
		driver.findElement(By.name("message")).sendKeys("Order Placed");
		driver.findElement(By.linkText("Place Order")).click();
		driver.findElement(By.name("name_on_card")).sendKeys("Test Card");
		driver.findElement(By.name("card_number")).sendKeys("410000000000");
		driver.findElement(By.name("cvc")).sendKeys("123");
		driver.findElement(By.name("expiry_month")).sendKeys("01");
		driver.findElement(By.name("expiry_year")).sendKeys("1900");
		driver.findElement(By.id("submit")).click();
		
		//Confirm order has been placed
		Assert.assertTrue(driver.findElement(By.xpath("//h2[@data-qa='order-placed']")).isDisplayed(), "Order Failed");
		Assert.assertEquals(
				driver.findElement(By.xpath("//h2[@data-qa='order-placed']/following-sibling::p")).getText(),
				"Congratulations! Your order has been confirmed!", "Order not confirmed");
		driver.quit();
	}

	public static void scrollToMiddle(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})",
	element);
	}
}
