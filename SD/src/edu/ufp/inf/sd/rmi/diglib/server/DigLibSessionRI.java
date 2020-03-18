package edu.ufp.inf.sd.rmi.diglib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface DigLibSessionRI extends Remote {
    Book[] search(String title, String author) throws RemoteException;
}