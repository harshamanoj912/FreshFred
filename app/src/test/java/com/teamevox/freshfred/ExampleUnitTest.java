package com.teamevox.freshfred;

import com.teamevox.freshfred.IT19208718.Calc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    public void setUp(){
        Calc calc = new Calc();
    }

    @Test
    public void commission_isCorrect() {

        float ans = (float) Calc.calculateCommission(100, 10);

        assertEquals(ans, 10, 0.001);
    }











}