package packageOne;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Playwright;

public class FirstPlaywrightTest {

    @Test
    void shouldShowThePageTitle(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));

        browser.close();
        playwright.close();
    }

    @Test
    void shouldSearchByKeywords(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();

        page.navigate("https://practicesoftwaretesting.com/");
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();


        int matchingSearchResult = page.locator(".card").count();

        Assertions.assertTrue(matchingSearchResult > 0);

        browser.close();
        playwright.close();
    }
}
