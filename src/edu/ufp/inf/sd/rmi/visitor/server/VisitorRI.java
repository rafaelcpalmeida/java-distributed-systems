package edu.ufp.inf.sd.rmi.visitor.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface VisitorRI extends Remote {
    String methodName() throws RemoteException;
}