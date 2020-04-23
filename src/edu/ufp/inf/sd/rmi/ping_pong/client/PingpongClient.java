package edu.ufp.inf.sd.rmi.ping_pong.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ufp.inf.sd.rmi.ping_pong.server.PingpongRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

public class PingpongClient {

	private SetupContextRMI contextRMI;
	private PingpongRI myRI;

	public static void main(String[] args) {
		if (args != null && args.length < 2) {
			System.exit(-1);
		} else {
			assert args != null;
			PingpongClient clt = new PingpongClient(args);
			clt.lookupService();
			clt.playService();
		}
	}

	public PingpongClient(String[] args) {
		try {
			String registryIP = args[0];
			String registryPort = args[1];
			String serviceName = args[2];
			contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[] { serviceName });
		} catch (RemoteException e) {
			Logger.getLogger(PingpongClient.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	private void lookupService() {
		try {
			Registry registry = contextRMI.getRegistry();
			if (registry != null) {
				String serviceUrl = contextRMI.getServicesUrl(0);
				myRI = (PingpongRI) registry.lookup(serviceUrl);
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
			}
		} catch (RemoteException | NotBoundException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void playService() {
		try {
			System.out.println("Lets play!");
			new PingpongClientImpl(myRI);
		} catch (RemoteException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
}
