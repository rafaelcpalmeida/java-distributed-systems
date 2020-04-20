package edu.ufp.inf.sd.rmi.visitor.test.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class TestImpl extends UnicastRemoteObject implements TestRI {


    public TestImpl() throws RemoteException {
        super();
    }

    @Override
    public String methodName() throws RemoteException {
        System.out.println("someone call me!");
        return "someone called me!";
    }
}