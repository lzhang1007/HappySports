package com.android.orient.sports.happysports;

import org.junit.Test;

import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect()  {
        Random random = new Random(4);
        int v = random.nextInt();
        System.out.println(v);
    }
}