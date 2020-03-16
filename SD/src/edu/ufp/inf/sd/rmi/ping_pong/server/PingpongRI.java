package edu.ufp.inf.sd.rmi.ping_pong.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.ufp.inf.sd.rmi.ping_pong.client.PingpongClientRI;

public interface PingpongRI extends Remote {

	void ping(PingpongClientRI clientRI, Ball ball) throws RemoteException;

}