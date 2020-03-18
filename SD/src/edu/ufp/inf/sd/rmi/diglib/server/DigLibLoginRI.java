package edu.ufp.inf.sd.rmi.diglib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface DigLibLoginRI extends Remote {
    DigLibSessionRI login(String username, String password) throws RemoteException;
}