package com.lab;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class LabWhiteTest {

    @BeforeClass
    public static void setup() {
        Lab.process(".\\text.txt");
    }

    @Test
    public void test1() {
        String expected = "No \"apple\" and \"banana\" in the graph!";
        System.out.println("Expected result: " + expected);
        String actual = Lab.queryBridgeWords("apple", "banana");
        System.out.println("Actual result: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        String expected = "No \"apple\" in the graph!";
        System.out.println("Expected result: " + expected);
        String actual = Lab.queryBridgeWords("apple", "sun");
        System.out.println("Actual result: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        String expected = "No \"banana\" in the graph!";
        System.out.println("Expected result: " + expected);
        String actual = Lab.queryBridgeWords("sun", "banana");
        System.out.println("Actual result: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test4() {
        String expected = "No bridge words from \"people\" to \"rely\"!";
        System.out.println("Expected result: " + expected);
        String actual = Lab.queryBridgeWords("people", "rely");
        System.out.println("Actual result: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test5() {
        String expected = "The bridge word from \"radiant\" to \"of\" is: sphere.";
        System.out.println("Expected result: " + expected);
        String actual = Lab.queryBridgeWords("radiant", "of");
        System.out.println("Actual result: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test6() {
        String expected = "The bridge words from \"its\" to \"light\" are: brilliant, and warm.";
        System.out.println("Expected result: " + expected);
        String actual = Lab.queryBridgeWords("its", "light");
        System.out.println("Actual result: " + actual);
        assertEquals(expected, actual);
    }
}