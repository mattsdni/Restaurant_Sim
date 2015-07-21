package Main;

/**
 * Created by Matt on 7/17/2015.
 */
public class BellCurve
{
    static boolean haveSpare;
    static double rand1 = 0, rand2 = 0;
    public static double generateGaussianNoise(double variance)
    {
        haveSpare = false;
        double rand1 = 0, rand2 = 0;
        double TWO_PI = 6.2831853071795864769252866;

        if(haveSpare)
        {
            haveSpare = false;
            return Math.sqrt(variance * rand1) * Math.sin(rand2);
        }

        haveSpare = true;

        rand1 = Math.random();
        if(rand1 < 1e-100) rand1 = 1e-100;
        rand1 = -2 * Math.log(rand1);
        rand2 = Math.random() * TWO_PI;

        return Math.sqrt(variance * rand1) * Math.cos(rand2);
    }
}
