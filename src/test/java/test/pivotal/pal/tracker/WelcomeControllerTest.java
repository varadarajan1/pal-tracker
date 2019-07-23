package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.HelloController;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WelcomeControllerTest {

    @Test
    public void itSaysHello() throws Exception {
        HelloController controller = new HelloController("A welcome message");
        assertThat(controller.sayHello()).isEqualTo("A welcome message");
    }
}
