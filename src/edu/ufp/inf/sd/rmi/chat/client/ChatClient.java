package edu.ufp.inf.sd.rmi.chat.client;

import edu.ufp.inf.sd.rmi.chat.server.ChatRI;
import edu.ufp.inf.sd.rmi.chat.server.State;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient {
    
    private SetupContextRMI contextRMI;
    private ChatRI chatServerRI;
    private ChatClientImpl observer;
    
    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.exit(-1);
        } else {
            assert args != null;
            ChatClient clt = new ChatClient(args);
            clt.lookupService();
            clt.playService();
        }
    }

    public ChatClient(String[] args) {
        try {
            String registryIP   = args[0];
            String registryPort = args[1];
            String serviceName  = args[2];
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void lookupService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl = contextRMI.getServicesUrl(0);
                chatServerRI = (ChatRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void playService() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            String message;

            observer = new ChatClientImpl(this, chatServerRI);

            chatServerRI.attach(observer);

            System.out.println("Type your message, " + username + ": ");
            while(!(message = scanner.nextLine()).equals("###")) {
                System.out.println("Type your message, " + username + ": ");
                this.observer.chatRI.setState(new State(username, message));
            }

            chatServerRI.detach(observer);

            System.out.println("Goodbye, " + username);
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void updateMessages() throws RemoteException {
        System.out.println("[" + this.observer.getLastObserverState().getId() + "] " + this.observer.getLastObserverState().getInfo());
    }
}
