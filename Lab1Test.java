import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Lab1Test {

    @Before
    public void setUp() throws Exception {
        String path = "C:\\Users\\王鹏睿wpr\\IdeaProjects\\lab3\\src\\main\\java\\text.txt";
        Lab1.process(path);
/*        String filename = "C:\\Users\\王鹏睿wpr\\IdeaProjects\\lab3\\graph.jpg";
        BufferedImage image = readImage(filename);
        ArrayList<String> list = new ArrayList<>();*/
    }

    @Test
    public void testQueryBridgeWordsWithValidInput() {
        String word1 = "is";
        String word2 = "a";

        String result = Lab1.queryBridgeWords(word1, word2);
        assertEquals("The bridge words from \"is\" to \"a\" is: truly.", result);

        String word3 = "its";
        String word4 = "light";
        result = Lab1.queryBridgeWords(word3, word4);
        assertEquals("The bridge words from \"its\" to \"light\" are: brilliant,and warm.", result);
    }

    @Test
    public void testQueryBridgeWordsWithNoResults() {
        String word1 = "light";
        String word2 = "solar";
        String result = Lab1.queryBridgeWords(word1, word2);
        assertEquals("No bridge words from \"light\" to \"solar\"!", result);
    }

    @Test
    public void testQueryBridgeWordsWithInvalidInput() {
        String word1 = "ab";
        String word2 = "brilliant";
        String result = Lab1.queryBridgeWords(word1, word2);
        assertEquals("No \"ab\" in the graph!", result);
    }


    @Test
    public void testQueryBridgeWordsWithEmptyInput() {
        String word1 = "";
        String word2 = "";
        String result = Lab1.queryBridgeWords(word1, word2);
        assertEquals("No \"\" and \"\" in the graph!", result);
    }

}




