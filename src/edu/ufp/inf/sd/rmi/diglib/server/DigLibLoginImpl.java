package edu.ufp.inf.sd.rmi.diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class DigLibLoginImpl extends UnicastRemoteObject implements DigLibLoginRI {

    private HashMap<String, DigLibSessionRI> sessions = new HashMap<>();
    private DBMockup database;

    public DigLibLoginImpl(DBMockup db) throws RemoteException {
        super();
        this.database = db;
    }

    @Override
    public DigLibSessionRI login(String username, String password) throws RemoteException {
        if (this.sessions.containsKey(username)) {
            return this.sessions.get(username);
        }

        if (this.database.exists(username, password)) {
            DigLibSessionImpl lib = new DigLibSessionImpl(this.database);
            this.sessions.put(username, lib);

            return lib;
        }

        return null;
    }
}