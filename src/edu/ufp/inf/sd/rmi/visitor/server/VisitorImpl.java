package edu.ufp.inf.sd.rmi.visitor.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class VisitorImpl extends UnicastRemoteObject implements VisitorRI {


    public VisitorImpl() throws RemoteException {
        super();
    }

    @Override
    public String methodName() throws RemoteException {
        System.out.println("someone call me!");
        return "someone called me!";
    }
}