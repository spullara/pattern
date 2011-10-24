package com.github.spullara.java.util.regex;

import org.junit.Test;

public class MicroPerformanceTest {

    private static final int TIMES = 100000000;

    interface Closure {
        boolean is();
    }

    Closure closureTrue = new Closure() {
        @Override
        public boolean is() {
            return true;
        }
    };
    Closure closureFalse = new Closure() {
        @Override
        public boolean is() {
            return false;
        }
    };

    @Test
    public void testClosures() {
        int total = 0;
        // Ultimately false
        Closure closure = new Closure() {
            @Override
            public boolean is() {
                return false;
            }
        };
        // Make it 20 deep
        for (int i = 0; i < 20; i++) {
            final Closure current = closure;
            closure = new Closure() {
                @Override
                public boolean is() {
                    return current.is();
                }
            };
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            if (closure.is()) total++;
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println(TIMES / diff + ", " + total);
    }

    @Test
    public void testArray() {
        int total = 0;
        Closure[] closures = new Closure[20];
        for (int i = 0; i < closures.length - 1; i++) {
            closures[i] = closureTrue;
        }
        closures[closures.length - 1] = closureFalse;
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            int length = closures.length;
            boolean result = true;
            for (int j = 0; j < length; j++) {
                if (!closures[j].is()) result = false;
            }
            if (result) total++;
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println(TIMES / diff + ", " + total);
    }

    @Test
    public void testUnrolled() {
        int total = 0;
        Closure[] closures = new Closure[20];
        for (int i = 0; i < closures.length - 1; i++) {
            closures[i] = closureTrue;
        }
        closures[closures.length - 1] = closureFalse;
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES*10; i++) {
            int j = 0;
            if (closures[j++].is())
                if (closures[j++].is())
                    if (closures[j++].is())
                        if (closures[j++].is())
                            if (closures[j++].is())
                                if (closures[j++].is())
                                    if (closures[j++].is())
                                        if (closures[j++].is())
                                            if (closures[j++].is())
                                                if (closures[j++].is())
                                                    if (closures[j++].is())
                                                        if (closures[j++].is())
                                                            if (closures[j++].is())
                                                                if (closures[j++].is())
                                                                    if (closures[j++].is())
                                                                        if (closures[j++].is())
                                                                            if (closures[j++].is())
                                                                                if (closures[j++].is())
                                                                                    if (closures[j++].is())
                                                                                        if (closures[j].is()) total++;
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println(TIMES*10 / diff + ", " + total);
    }
}
