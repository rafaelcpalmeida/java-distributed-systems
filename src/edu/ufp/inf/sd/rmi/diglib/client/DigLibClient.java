package edu.ufp.inf.sd.rmi.diglib.client;

import edu.ufp.inf.sd.rmi.diglib.server.Book;
import edu.ufp.inf.sd.rmi.diglib.server.DigLibLoginRI;
import edu.ufp.inf.sd.rmi.diglib.server.DigLibSessionRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigLibClient {


    private SetupContextRMI contextRMI;
    private DigLibLoginRI digLibLoginRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.exit(-1);
        } else {
            assert args != null;
            DigLibClient clt = new DigLibClient(args);
            clt.lookupService();
            System.out.println();
            System.out.println();
            System.out.println();
            clt.playService();
        }
    }

    public DigLibClient(String[] args) {
        try {
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(DigLibClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void lookupService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl = contextRMI.getServicesUrl(0);
                digLibLoginRI = (DigLibLoginRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void playService() {
        try {
            DigLibSessionRI valid_session = login("guest", "ufp");

            if (valid_session != null) {
                Book[] existing_books = searchBooks(valid_session, "Distributed Systems: principles and paradigms", "Tanenbaum");

                if (existing_books.length > 0) {
                    System.out.println(String.format("Found %s book(s):", existing_books.length));
                    System.out.println();

                    for (Book book : existing_books) {
                        System.out.println(book.toString());
                        System.out.println();
                    }
                } else {
                    System.out.println("No books were found with your search criteria :(");
                    System.out.println();
                }

                Book[] null_books = searchBooks(valid_session, "Empty", "None");

                if (null_books.length > 0) {
                    System.out.println(String.format("Found %s book(s):", null_books.length));
                    System.out.println();

                    for (Book book : null_books) {
                        System.out.println(book.toString());
                        System.out.println();
                    }
                } else {
                    System.out.println("No books were found with your search criteria :(");
                    System.out.println();
                }
            } else {
                System.out.println("Login failed :( Check your credentials");
                System.out.println();
            }

            DigLibSessionRI invalid_session = login("gues", "uf");

            if (invalid_session != null) {
                Book[] null_books = searchBooks(invalid_session, "Empty", "None");

                if (null_books.length > 0) {
                    System.out.println(String.format("Found %s book(s):", null_books.length));

                    for (Book book : null_books) {
                        System.out.println(book.toString());
                        System.out.println();
                    }
                } else {
                    System.out.println("No books were found with your search criteria :(");
                    System.out.println();
                }
            } else {
                System.out.println("Login failed :( Check your credentials");
                System.out.println();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DigLibSessionRI login (String username, String password) throws RemoteException {
        return digLibLoginRI.login(username, password);
    }

    private Book[] searchBooks(DigLibSessionRI session, String title, String author) throws RemoteException {
        return session.search(title, author);
    }
}
