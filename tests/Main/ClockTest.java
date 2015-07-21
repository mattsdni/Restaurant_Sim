package Main;

import static org.junit.Assert.*;

public class ClockTest
{
    Clock c1, c2, c3;
    @org.junit.Before
    public void setUp() throws Exception
    {
        c1 = new Clock(13,23);
        c2 = new Clock(13,50);
        c3 = new Clock(90);
    }

    @org.junit.Test
    public void testMinuteConstructor() throws Exception
    {
        System.out.println(c3);
    }

    @org.junit.Test
    public void testPlus() throws Exception
    {
        System.out.println(c1.plus(c2));
    }

    @org.junit.Test
    public void testCompareTo() throws Exception
    {
        if (c1.compareTo(c2) > 0)
        {
            System.out.println("c1 bigger");
        }
        if (c1.compareTo(c2) < 0)
        {
            System.out.println("c2 bigger");
        }
        if (c1.compareTo(c2) == 0)
        {
            System.out.println("same size");
        }
    }
}