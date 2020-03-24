package edu.ufp.inf.sd.rmi.chat.server;

import edu.ufp.inf.sd.rmi.chat.client.ChatClientRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class ChatImpl extends UnicastRemoteObject implements ChatRI {

    private ArrayList<ChatClientRI> observers;
    private State chatState;

    public ChatImpl() throws RemoteException {
        super();
        observers = new ArrayList<>();
    }

    @Override
    public void attach(ChatClientRI observerRI) {
        observers.add(observerRI);
    }

    @Override
    public void detach(ChatClientRI observerRI) {
        observers.remove(observerRI);
    }

    @Override
    public State getState() {
        return this.chatState;
    }

    @Override
    public void setState(State state) {
        this.chatState = state;
        notifyAllObservers();
    }

    public void notifyAllObservers() {
        observers.forEach(observer -> {
            try {
                observer.update();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}