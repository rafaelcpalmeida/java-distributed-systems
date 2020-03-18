package edu.ufp.inf.sd.rmi.diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class DigLibSessionImpl extends UnicastRemoteObject implements DigLibSessionRI {


    private DBMockup database;

    public DigLibSessionImpl(DBMockup db) throws RemoteException {
        super();
        this.database = db;
    }

    @Override
    public Book[] search(String title, String author) {
        return this.database.select(title, author);
    }
}