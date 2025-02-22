package packageOne;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Playwright;

@UsePlaywright
/* @UsePlaywright omogućava automatsko kreiranje i zatvaranje Playwright resursa (browser, page). Metode primaju Page page kao argument,
jer Playwright ekstenzija automatski kreira i prosleđuje Page objekat. */

public class FirstPlaywrightTest {

/*    Ovo je zastareli metod
    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    void setUp(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        page = browser.newPage();
    }

    @AfterEach
    void teardown(){
        browser.close();
        playwright.close();
    }
*/
    @Test
    void shouldShowThePageTitle(Page page){

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));

    }

    @Test
    void shouldSearchByKeywords(Page page){

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

    }
}
