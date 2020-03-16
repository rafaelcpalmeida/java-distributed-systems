package edu.ufp.inf.sd.rmi.calculadora.client;

import edu.ufp.inf.sd.rmi.calculadora.server.CalculadoraRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculadoraClient {


    private SetupContextRMI contextRMI;
    private CalculadoraRI myRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.exit(-1);
        } else {
            assert args != null;
            CalculadoraClient clt = new CalculadoraClient(args);
            clt.lookupService();
            clt.playService();
        }
    }

    public CalculadoraClient(String[] args) {
        try {
            String registryIP   = args[0];
            String registryPort = args[1];
            String serviceName  = args[2];
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(CalculadoraClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void lookupService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl = contextRMI.getServicesUrl(0);
                myRI = (CalculadoraRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void playService() {
        try {
            ArrayList<Float> numeros = new ArrayList<>();

            numeros.add((float) 1);
            numeros.add((float) 2);
            numeros.add((float) 3);
            numeros.add((float) 2);
            numeros.add((float) 1);

            System.out.println("Soma: " + this.myRI.somar(numeros));

            System.out.println("Multiplicação: " + this.myRI.multiplicar((float) 2, (float) 2));

            System.out.println("Subtração: " + this.myRI.subtrair(2, 2));

            try {
                System.out.println("Divisão: " + this.myRI.dividir(2, 0));
            } catch (ArithmeticException ex) {
                System.out.println("Erro: impossível dividir por zero");
            }

            System.out.println("Divisão: " + this.myRI.dividir(2, 2));

            System.out.println("Moda: " + this.myRI.moda(numeros));

            System.out.println("Média: " + this.myRI.media(numeros));

            System.out.println("Power: " + this.myRI.power(2, 2));
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
