import org.junit.jupiter.api.Test;
import inlamningsuppgift2.joakim.Tomteland;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TomtelandTest {

    Tomteland tl = new Tomteland();



    List<String> uTomten = Arrays.asList("Tröger", "Trötter", "Blyger", "Dammråttan", "Skumtomten", "Glader",
            "Butter", "Rådjuret", "Nyckelpigan", "Haren", "Räven", "Gråsuggan", "Myran", "Bladlusen");
    List<String> uGlader = Arrays.asList("Tröger", "Trötter", "Blyger", "Dammråttan", "Skumtomten");
    List<String> uButter = Arrays.asList("Rådjuret", "Nyckelpigan", "Haren", "Räven", "Gråsuggan", "Myran", "Bladlusen");
    List<String> uTrotter = Arrays.asList("Dammråttan", "Skumtomten");
    List<String> uSkumtomten = Arrays.asList("Dammråttan");
    List<String> uRaven = Arrays.asList("Gråsuggan", "Myran", "Bladlusen");
    List<String> uMyran = Arrays.asList("Bladlusen");


    @Test
    final void getUnderlingsBladlusenTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Bladlusen", list);
        assertEquals(0, underlings.size());
    }

    @Test
    final void getUnderlingsTrogerTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Tröger", list);
        assertEquals(0, underlings.size());
    }

    @Test
    final void getUnderlingsBlygerTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Blyger", list);
        assertEquals(0, underlings.size());
    }

    @Test
    final void getUnderlingsDammrattanTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Dammråttan", list);
        assertEquals(0, underlings.size());
    }

    @Test
    final void getUnderlingsGrasugganTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Gråsuggan", list);
        assertEquals(0, underlings.size());
    }

    @Test
    final void getUnderlingsHarenTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Haren", list);
        assertEquals(0, underlings.size());
    }

    @Test
    final void getUnderlingsNyckelpiganTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Nyckelpigan", list);
        assertEquals(0, underlings.size());
    }

    @Test
    final void getUnderlingsRadjuretTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Rådjuret", list);
        assertEquals(0, underlings.size());
    }

    @Test
    final void getUnderlingsMyranTest(){
        List<String> list = new ArrayList<>();
        List<String> underlings = tl.getUnderlings("Myran", list);
        assertEquals(underlings.size(), uMyran.size());
        assertFalse(underlings.retainAll(uMyran));
    }

    @Test
    final void getUnderlingsRavenTest(){
        List<String> list = new LinkedList<>();
        List<String> underlings = tl.getUnderlings("Räven", list);
        assertEquals(underlings.size(), uRaven.size());
        assertFalse(underlings.retainAll(uRaven));
    }

    @Test
    final void getUnderlingsSkumtomtenTest(){
        List<String> list = new LinkedList<>();
        List<String> underlings = tl.getUnderlings("Skumtomten", list);
        assertEquals(underlings.size(), uSkumtomten.size());
        assertFalse(underlings.retainAll(uSkumtomten));
    }

    @Test
    final void getUnderlingsTrotterTest(){
        List<String> list = new LinkedList<>();
        List<String> underlings = tl.getUnderlings("Trötter", list);
        assertEquals(underlings.size(), uTrotter.size());
        assertFalse(underlings.retainAll(uTrotter));
    }


    @Test
    final void getUnderlingsButterTest(){
        List<String> list = new LinkedList<>();
        List<String> underlings = tl.getUnderlings("Butter", list);
        assertEquals(underlings.size(), uButter.size());
        assertFalse(underlings.retainAll(uButter));
    }

    @Test
    final void getUnderlingsGladerTest(){
        List<String> list = new LinkedList<>();
        List<String> underlings = tl.getUnderlings("Glader", list);
        assertEquals(underlings.size(), uGlader.size());
        assertFalse(underlings.retainAll(uGlader));
    }

    @Test
    final void getUnderlingsTomtenTest(){
        List<String> list = new LinkedList<>();
        List<String> underlings = tl.getUnderlings("Tomten", list);
        assertEquals(underlings.size(), uTomten.size());
        assertFalse(underlings.retainAll(uTomten));
    }

}