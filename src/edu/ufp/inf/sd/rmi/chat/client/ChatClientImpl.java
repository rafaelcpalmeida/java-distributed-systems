package edu.ufp.inf.sd.rmi.chat.client;

import edu.ufp.inf.sd.rmi.chat.server.ChatRI;
import edu.ufp.inf.sd.rmi.chat.server.State;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ChatClientImpl extends UnicastRemoteObject implements ChatClientRI {


    protected ChatRI chatRI;
    protected ChatClient chatClient;

    public ChatClientImpl(ChatClient chatClient, ChatRI chatRI) throws RemoteException {
        super();

        this.chatRI = chatRI;
        this.chatClient = chatClient;
    }

    @Override
    public void update() throws RemoteException {
        this.chatClient.updateMessages();
    }

    public State getLastObserverState() throws RemoteException {
        return this.chatRI.getState();
    }
}