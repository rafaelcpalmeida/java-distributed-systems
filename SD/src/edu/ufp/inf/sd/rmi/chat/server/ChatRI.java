package edu.ufp.inf.sd.rmi.chat.server;

import edu.ufp.inf.sd.rmi.chat.client.ChatClientRI;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ChatRI extends Remote {
    void attach(ChatClientRI observerRI) throws RemoteException;
    void detach(ChatClientRI observerRI) throws RemoteException;
    State getState() throws RemoteException;
    void setState(State state) throws RemoteException;
}