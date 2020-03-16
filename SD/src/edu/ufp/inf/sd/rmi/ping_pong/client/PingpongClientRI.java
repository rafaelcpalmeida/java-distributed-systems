package edu.ufp.inf.sd.rmi.ping_pong.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.ufp.inf.sd.rmi.ping_pong.server.Ball;
import edu.ufp.inf.sd.rmi.ping_pong.server.PingpongRI;

public interface PingpongClientRI extends Remote {

	void pong(PingpongRI serverRI, Ball ball) throws RemoteException;

}
