package edu.learn.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumKindle {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.gecko.driver", "/home/ram/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.ultimatix.net/");
		

		driver.findElement(By.id("USER")).sendKeys("rparthas");
		driver.findElement(By.id("PASSWORD")).sendKeys("Talent17!");
		driver.findElement(By.id("login_button")).submit();
		
		Thread.sleep(3000);
		driver.get("https://www.ultimatix.net/ultimatixPortalWeb/portlets/applications/redirect.jsp?PARAM=463");
		driver.switchTo().alert().accept();

		driver.wait(2000);

		// Close the driver
		driver.quit();

	}

}
