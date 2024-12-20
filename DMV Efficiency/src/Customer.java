/**
 * Customer - object for customers
 *
 * @author
 * @copyright 2024 Howard Community College
 * @version 1.0
 */

public class Customer
{
    int ticketNumber;
    int transactionTime;

    public Customer(int ticketNumber, int transactionTime)
    {
        this.ticketNumber = ticketNumber;
        this.transactionTime = transactionTime;
    }

    public int getTicketNumber()
    {
        return ticketNumber;
    }
    public void setTicketNumber(int ticketNumber)
    {
        this.ticketNumber = ticketNumber;
    }

    public int getTransactionTime()
    {
        return transactionTime;
    }

    public void setTransactionTime(int transactionTime)
    {
        this.transactionTime = transactionTime;
    }
}
