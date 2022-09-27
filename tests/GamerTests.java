import org.junit.Assert;
import org.junit.Test;

public class GamerTests {
    @Test
    public void testTestsWorking() {
        Gamer play = new Gamer("Walrus");
        Assert.assertNotEquals(3,2);
        Assert.assertEquals(3,3);
    }
}
