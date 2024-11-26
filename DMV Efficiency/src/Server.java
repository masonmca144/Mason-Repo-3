/**
 * Server - object for servers
 * @author
 * @copyright 2024 Howard Community College
 * @version 1.0
 */

public class Server implements Runnable
{
    int serverNumber;
    Customer customerBeingServed;
    private boolean isAvailable;

    public Server(int serverNumber)
    {
        this.serverNumber = serverNumber;
        this.isAvailable = true;
    }

    public synchronized boolean isAvailable()
    {
        return isAvailable;
    }
    public synchronized void assignCustomer(Customer customerBeingServed)
    {
        this.customerBeingServed = customerBeingServed;
        this.isAvailable = false;
    }

    private synchronized void unassignCustomer()
    {
        customerBeingServed = null;
        this.isAvailable = true;
    }

    @Override
    public void run()
    {
        if(customerBeingServed != null)
        {
            System.out.println(String.format("Server %s takes customer %s", serverNumber, customerBeingServed.ticketNumber));

            try
            {
                Thread.sleep(customerBeingServed.getTransactionTime());
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println(String.format("Server %s is done with customer %s", serverNumber, customerBeingServed.ticketNumber));
            unassignCustomer();
        }
    }
}
