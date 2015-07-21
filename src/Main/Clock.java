package Main;

/**
 * Created by Matt on 7/17/2015.
 */
public class Clock implements Comparable<Clock>
{
    private int minutes, hours;
    private double speed; //time speed multiplier

    public Clock()
    {
        minutes = 0;
        hours = 0;
        speed = 1;
    }

    public Clock(int h, int m)
    {
        minutes = m;
        hours = h;
        speed = 1;
    }

    public Clock(int h, int m, double s)
    {
        minutes = m;
        hours = h;
        speed = s;
    }

    public Clock(int m)
    {
        minutes = m % 60;
        hours = m/60;
    }

    public Clock(String t)
    {
        String[] tmp = t.split(":");
        minutes = Integer.parseInt(tmp[1]);
        hours = Integer.parseInt(tmp[0]);
        speed = 1;
    }

    public int compareTo(Clock c)
    {
        if (this.hours < c.getHours())
            return -1;
        if (this.hours > c.getHours())
            return 1;
        //hours must be equal at this point
        if (this.minutes < c.getMinutes())
            return -1;
        if (this.minutes > c.getMinutes())
            return 1;
        //time must be equal at this point
        return 0;
    }

    /**
     * Add two clocks.
     * @param c another clock
     * @return the result
     */
    public Clock plus(Clock c)
    {
        int newHour = 0;
        int newMin = 0;
        newMin = this.minutes + c.getMinutes();
        if (newMin > 60)
        {
            newHour = 1;
            newMin-=60;
        }
        newHour += this.hours + c.getHours();
        return new Clock(newHour, newMin);
    }

    public int getMinutes()
    {
        return minutes;
    }

    public int getHours()
    {
        return hours;
    }

    public float getTimeAsDecimal()
    {
        return hours+(minutes/60.0f);
    }
    public String getFormattedTime()
    {
        return hours+":"+minutes;
    }

    public void tick()
    {
        if (minutes < 60)
            minutes+=1*speed;
        else
        {
            minutes = 0;
            hours++;
        }
    }

    public String toString()
    {
        return this.getFormattedTime();
    }



}
