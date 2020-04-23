package edu.ufp.inf.sd.rmi.calculadora.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class CalculadoraImpl extends UnicastRemoteObject implements CalculadoraRI {


    public CalculadoraImpl() throws RemoteException {
        super();
    }

    @Override
    public Float somar(ArrayList<Float> numeros) throws RemoteException {
        AtomicReference<Float> soma = new AtomicReference<>((float) 0);

        numeros.forEach((numero) -> soma.updateAndGet(v -> v + numero));

        return soma.get();
    }

    @Override
    public Float multiplicar(Float primeiro, Float segundo) throws RemoteException {
        return primeiro * segundo;
    }

    @Override
    public Integer subtrair(Integer primeiro, Integer segundo) throws RemoteException {
        return primeiro - segundo;
    }

    @Override
    public Integer dividir(Integer primeiro, Integer segundo) throws RemoteException, ArithmeticException {
        return primeiro / segundo;
    }

    @Override
    public Float moda(ArrayList<Float> numeros) throws RemoteException {
        float maxValue = (float) 0, maxCount = (float) 0;

        for (int i = 0; i < numeros.size(); ++i) {
            float count = (float) 0;
            for (Float numero : numeros) {
                if (numero.equals(numeros.get(i))) ++count;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = numeros.get(i);
            }
        }

        return maxValue;
    }

    @Override
    public double media(ArrayList<Float> numeros) throws RemoteException {
        Float sum = (float) 0;
        for (Float numero : numeros) {
            sum += numero;
        }
        return sum / numeros.size();
    }

    @Override
    public Integer power(Integer primeiro, Integer segundo) throws RemoteException {
        return (int) Math.pow(primeiro, segundo);
    }
}