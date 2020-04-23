package edu.ufp.inf.sd.rmi.ping_pong.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.ufp.inf.sd.rmi.ping_pong.client.PingpongClientRI;

public class PingpongImpl extends UnicastRemoteObject implements PingpongRI {
	private static final long serialVersionUID = 3987871099203661797L;

	public PingpongImpl() throws RemoteException {
		super();
	}

	@Override
	public void ping(PingpongClientRI clientRI, Ball ball) throws RemoteException {
		new Thread(new PingThread(this, clientRI, ball)).start();
	}

}

class PingThread implements Runnable {

	private final PingpongRI serverRI;

	private final PingpongClientRI clientRI;

	private final Ball ball;

	public PingThread(PingpongRI serverRI, PingpongClientRI clientRI, Ball ball) {
		super();
		this.serverRI = serverRI;
		this.clientRI = clientRI;
		this.ball = ball;
	}

	@Override
	public void run() {
		try {
			System.out.println("Ping");
			if (ball.getValue() > 0) {
				System.out.println("Sending ball back, nº " + ball.getValue());
				clientRI.pong(serverRI, ball);
			} else {
				System.out.println("Not sending ball back, nº " + ball.getValue());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
