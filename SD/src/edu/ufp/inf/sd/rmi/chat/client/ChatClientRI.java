package edu.ufp.inf.sd.rmi.chat.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientRI extends Remote {
    void update() throws RemoteException;
}
