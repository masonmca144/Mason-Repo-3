/**
 * Main - Display a menu and allow user to display a list of contacts / sort them
 *
 * @author
 * @copyright 2024 Howard Community College
 * @version 1.0
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
public class Main {
    public static void main(String[] args)
    {
        //Setup Scanner
        Scanner input = new Scanner(System.in);
        //Read file for contacts
        //ArrayList<Contact> contacts = getContactsFromFile(input);
        ContactsBST contacts = new ContactsBST();

        Boolean listSorted = false;
        Boolean programRunning = true;

        while(programRunning)
        {
            displayMenu();

            int choice = validateIntInput(input);

            switch (choice)
            {
                case 1:
                    if(!listSorted)
                    {
                        alphabeticSort(contacts);
                        listSorted = true;
                    }
                    displayAlphabeticOrder(contacts);
                    break;
                case 2:
                    if(!listSorted)
                    {
                        alphabeticSort(contacts);
                        listSorted = true;
                    }
                    displayReverseOrder(contacts);
                    break;
                case 3:
                    System.out.print("Enter contact name: ");
                    String contactToSearch = input.nextLine();
                    System.out.println();

                    if(listSorted)
                    {
                        contactSearchBinary(contacts, contactToSearch);
                    }
                    else
                    {
                        contactSearchSequential(contacts, contactToSearch);
                    }
                    break;
                case 4:
                    System.out.println("Good Bye!");
                    programRunning = false;
                    break;
                default:
                    System.out.println("Please choose one of the available options.");

            }
        }


    }
    static void contactSearchBinary(ArrayList<Contact> contacts, String searchName)
    {
        int targetIndex = 0;
        boolean found = false;

        int left = 0;
        int right = contacts.size() - 1;

        while(left <= right)
        {
            int mid = left + (right - left) / 2;

            int compare = contacts.get(mid).getName().compareToIgnoreCase(searchName);

            if(compare == 0)
            {
                targetIndex = mid;
                found = true;
                break;
            }
            else if (compare < 0)
            {
                    left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }

        if(found)
        {
            System.out.println("Contact " + contacts.get(targetIndex).toString());
            System.out.println();
        }
        else
        {
            System.out.println("Contact not found.\n");
        }
    }

    static void contactSearchSequential(ArrayList<Contact> contacts, String searchName)
    {
        for(int i = 0; i < contacts.size() - 1; i++)
        {
            if(contacts.get(i).name.compareToIgnoreCase(searchName) == 0)
            {
                System.out.println("Contact " + contacts.get(i).toString());
                System.out.println();
                return;
            }
        }

        System.out.println("Contact not found.");
    }

    static void displayAlphabeticOrder(ArrayList<Contact> contacts)
    {
        for(int i = 0; i < contacts.size(); i++)
        {
            System.out.println(contacts.get(i).toString());
        }

        System.out.println();
    }

    static void displayReverseOrder(ArrayList<Contact> contacts)
    {
        for(int i = contacts.size() - 1; i >= 0; i--)
        {
            System.out.println(contacts.get(i).toString());
        }
        System.out.println();
    }

    private static ContactsBST getContactsFromFile(Scanner input)
    {
        boolean validated = false;
        ContactsBST contacts = new ContactsBST();

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

                    String number = splitString[splitString.length - 1];
                    String name = String.join(" ", splitString[0], splitString[1]);

                    Contact contact = new Contact();
                    contact.setName(name);
                    contact.setNumber(number);

                    contacts.Insert(contact);
                }
                validated = true;
            }
            catch(IOException e)
            {
                System.out.println("File not found. Please try again.");
            }
        }
        return contacts;
    }

    private static void alphabeticSort(ArrayList<Contact> contacts)
    {
        for(int i = 0; i < contacts.size() - 1; i++)
        {
            int min = i;

            for(int j = i + 1; j < contacts.size(); j++)
            {
                if(contacts.get(j).getName().compareTo(contacts.get(min).getName()) < 0)
                {
                    min = j;
                }
            }

            if(min != i)
            {
                Contact temp = contacts.get(i);

                contacts.set(i, contacts.get(min));
                contacts.set(min, temp);
            }
        }
    }

    private static void displayMenu()
    {
        System.out.println("Contact List");
        System.out.println("------------");
        System.out.println("1. Display contacts in alphabetical order");
        System.out.println("2. Display contacts in reverse alphabetical order");
        System.out.println("3. Search contacts");
        System.out.println("4. Exit");
        System.out.print("Enter your selection here: ");
    }

    private static int validateIntInput(Scanner input)
    {
        String choiceString = input.nextLine();
        System.out.println();

        int choice = 0;
        Boolean validated = false;

        while(!validated)
        {
            try
            {
                choice = Integer.parseInt(choiceString);
                validated = true;
            }
            catch(Exception e)
            {
                System.out.print("Incorrect input. Please enter a number: ");
                choiceString = input.nextLine();
                System.out.println();
            }
        }
        return choice;
    }
}