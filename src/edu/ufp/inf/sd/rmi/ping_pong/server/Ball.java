package edu.ufp.inf.sd.rmi.ping_pong.server;

import java.io.Serializable;

public class Ball implements Serializable {
	private static final long serialVersionUID = 8319555968852697715L;

	private int value;

	public Ball() {
		super();
		this.randomize();
	}

	public void randomize() {
		value = (int) (Math.random() * 15);
	}

	public int getValue() {
		return value;
	}

}
