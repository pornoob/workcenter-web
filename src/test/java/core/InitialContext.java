package core;

import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "/META-INF/spring.xml" })
public class InitialContext {

    @BeforeClass
    public static void setUp() {
    }
}
