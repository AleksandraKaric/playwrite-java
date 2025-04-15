package packageOne;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.*;

import java.util.Arrays;


public class FirstPlaywrightTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;

    @BeforeAll
    public static void setUpBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no sandbox","--disable extensions","--no gpu"))
        );
        browserContext = browser.newContext();
    }

    @BeforeEach
    public void setUp(){
        page = browserContext.newPage();
    }

    @AfterAll
    public static void tearDown(){
        browser.close();
        playwright.close();
    }
    @Test
    void shouldShowThePageTitle(){

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));

    }

    @Test
    void shouldSearchByKeywords(){

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
