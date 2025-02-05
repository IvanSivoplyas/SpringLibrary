package controllers;

import org.junit.Test;
import ru.silvan.springcourse.controllers.MainPageController;

import static org.junit.Assert.assertEquals;

public class MainPageControllerTest {
    @Test
    public void mainPageShouldBeOpened() {
        MainPageController controller = new MainPageController();

        String result = controller.mainpage();

        assertEquals(result, "mainpage");
    }
}
