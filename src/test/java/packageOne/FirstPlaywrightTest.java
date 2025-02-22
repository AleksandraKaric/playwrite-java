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

        // Lokator tip: CSS atribut selektor
        // Traži input polje gde je placeholder atribut jednak "Search"
        page.locator("[placeholder=Search]").fill("Pliers");

        // Lokator tip: Tekstualni selektor sa :has-text()
        // Traži <button> element koji sadrži tekst "Search"
        page.locator("button:has-text('Search')").click();

        // Lokator tip: CSS klasa selektor
        // Traži sve elemente sa klasom "card" (pretpostavljeni rezultati pretrage)
        int matchingSearchResult = page.locator(".card").count();

        Assertions.assertTrue(matchingSearchResult > 0);

        browser.close();
        playwright.close();
    }
}
