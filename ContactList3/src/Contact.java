/**
 * Contact - Object to contain all contact info
 *
 * @author
 * @copyright 2024 Howard Community College
 * @version 2.0
 */

public class Contact implements Comparable<Contact>
{
    String name = "";
    String number = "";

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return String.format("[%s: %s]", getName(), getNumber());
    }

    @Override
    public int compareTo(Contact c)
    {
        return this.name.compareTo(c.name);
    }
}