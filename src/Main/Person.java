package Main;

/**
 * Created by Matt on 7/19/2015.
 */
public class Person
{
    public Clock spawnTime; //don't change the time on this one
    public Clock eatingTime;

    public Person(String t)
    {
        spawnTime = new Clock(t);
    }
    public void setEatingTime(Clock e)
    {
        eatingTime = e;
    }

}
