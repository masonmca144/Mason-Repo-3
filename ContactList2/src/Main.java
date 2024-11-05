/**
 * Main - Display a menu and allow user to display a list of contacts / sort them
 *
 * @author
 * @copyright 2024 Howard Community College
 * @version 1.0
 */

import javax.swing.plaf.synth.SynthUI;
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
        ContactsBST contactTree = getContactsFromFile(input);

        Boolean programRunning = true;

        while(programRunning)
        {
            displayMenu();

            int choice = validateIntInput(input);

            switch (choice)
            {
                case 1:
                    addContact(contactTree, input);
                    break;
                case 2:
                    removeContact(contactTree, input);
                    break;
                case 3:
                    contactTree.Print();
                    break;
                case 4:
                    searchContact(contactTree, input);
                    break;
                case 5:
                    System.out.println("Good Bye!");
                    programRunning = false;
                    break;
                default:
                    System.out.println("Please choose one of the available options.");

            }
        }


    }

    static void addContact(ContactsBST contactTree, Scanner input)
    {
        System.out.print("Enter new contact name: ");

        String name = input.nextLine();

        System.out.println();

        System.out.print("Enter new contact number: ");

        String number = input.nextLine();

        System.out.println();

        Contact contact = new Contact();
        contact.setName(name);
        contact.setNumber(number);

        contactTree.Insert(contact);
    }

    static void removeContact(ContactsBST contactTree, Scanner input)
    {
        System.out.print("Enter contact name to remove: ");

        String name = input.nextLine();

        System.out.println();

        contactTree.Remove(name);
    }

    static void searchContact(ContactsBST contactTree, Scanner input)
    {
        System.out.print("Enter contact name: ");

        String name = input.nextLine();

        System.out.println();

        Contact contact = contactTree.Search(name);

        if(contact != null)
        {
            System.out.println(String.format("Contact [%s: %s]", contact.getName(), contact.getNumber()));
        }
        else
        {
            System.out.println("Contact not found.");
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

    private static void displayMenu()
    {
        System.out.println("Contact List");
        System.out.println("------------");
        System.out.println("1. Add a contact");
        System.out.println("2. Remove a contact");
        System.out.println("3. Display contacts in alphabetical order");
        System.out.println("4. Search a contact");
        System.out.println("5. Exit");
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