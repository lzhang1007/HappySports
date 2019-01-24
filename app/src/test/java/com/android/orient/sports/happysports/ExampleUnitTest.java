package com.android.orient.sports.happysports;

import org.junit.Test;

import static com.android.orient.sports.happysports.encrypt.EncryptUtlsKt.encodeSMS4;
import static com.android.orient.sports.happysports.encrypt.EncryptUtlsKt.encodeSMS4toString;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println(encodeSMS4toString("12306", "com.dfzq.kldf"));
    }

    @Test
    public void test1() {
        byte[] byte2hex_2 = encodeSMS4("12306", "com.dfzq.kldf");
        StringBuilder sb = new StringBuilder();
        for (byte b : byte2hex_2) {
            sb.append(b);
        }
        System.out.println("\n加密后：" + sb.toString() + "\n");
    }

}