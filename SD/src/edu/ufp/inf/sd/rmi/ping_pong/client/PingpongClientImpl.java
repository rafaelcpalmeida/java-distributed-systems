package edu.ufp.inf.sd.rmi.ping_pong.client;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;

import edu.ufp.inf.sd.rmi.ping_pong.server.Ball;
import edu.ufp.inf.sd.rmi.ping_pong.server.PingpongRI;

public class PingpongClientImpl extends RemoteObject implements PingpongClientRI {
	private static final long serialVersionUID = -8489335256275948306L;

	public PingpongClientImpl(PingpongRI serverRI) throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);

		Ball ball = new Ball();
		System.out.println("Starting with ball nº " + ball.getValue());
		serverRI.ping(this, ball);
	}

	@Override
	public void pong(PingpongRI serverRI, Ball ball) throws RemoteException {
		System.out.println("Pong");
		ball.randomize();
		System.out.println("new value: " + ball.getValue());
		serverRI.ping(this, ball);
	}

}
