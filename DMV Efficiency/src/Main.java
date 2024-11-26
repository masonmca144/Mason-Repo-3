/**
 * Main - Create a queue of customers, use threads to allow multiple server to work simultaneously
 *
 * @author
 * @copyright 2024 Howard Community College
 * @version 1.0
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args)
    {
        final int SERVER_COUNT = 3;

        //Setup scanner
        Scanner input = new Scanner(System.in);

        //generate customer queue
        Queue<Customer> customerQueue = generateQueueFromFile(input);

        //generate server list
        ArrayList<Server> serverList = generateServerList(SERVER_COUNT);

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                SERVER_COUNT,
                SERVER_COUNT,
                3L, TimeUnit.SECONDS,
                queue
        );

        while(!customerQueue.isEmpty())
        {
            Server availableServer = findAvailableServer(serverList);

            if(availableServer != null)
            {
                Customer customer = customerQueue.poll();

                availableServer.assignCustomer(customer);

                executor.execute(availableServer);
            }
        }

        executor.shutdown();
    }

    private static Queue<Customer> generateQueueFromFile(Scanner input)
    {
        boolean validated = false;
        Queue<Customer> customerQueue = new LinkedList<>();

        while(!validated)
        {
            try
            {
                System.out.print("Enter file name: ");

                String fileName = input.nextLine();

                System.out.println();

                Path inputFilePath = Paths.get(fileName);

                Scanner inputFileScanner = new Scanner(inputFilePath);

                while(inputFileScanner.hasNextLine())
                {
                    String line = inputFileScanner.nextLine();

                    String[] splitString = line.trim().split(" ");

                    int ticketNumber = Integer.parseInt(splitString[0].trim());
                    int transactionTime = Integer.parseInt(splitString[1].trim().trim());

                    Customer customer = new Customer(ticketNumber, transactionTime);

                    customerQueue.add(customer);
                }
                validated = true;
            }
            catch(IOException e)
            {
                System.out.println("File not found. Please try again.");
            }
        }
        return customerQueue;
    }

    private static ArrayList<Server> generateServerList(int amount)
    {
        ArrayList<Server> serverList = new ArrayList<>();

        for(int i = 0; i < amount; i++)
        {
            Server server = new Server(i + 1);
            serverList.add(server);
        }

        return serverList;
    }

    private static Server findAvailableServer(ArrayList<Server> serverList)
    {
        for(Server server : serverList)
        {
            if(server.isAvailable())
            {
                return server;
            }
        }
        return null;
    }
}