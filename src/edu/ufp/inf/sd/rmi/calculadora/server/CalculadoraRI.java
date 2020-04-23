package edu.ufp.inf.sd.rmi.calculadora.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CalculadoraRI extends Remote {
    Float somar(ArrayList<Float> numeros) throws RemoteException;
    Float multiplicar(Float primeiro, Float segundo) throws RemoteException;
    Integer subtrair(Integer primeiro, Integer segundo) throws RemoteException;
    Integer dividir(Integer primeiro, Integer segundo) throws RemoteException, ArithmeticException;
    Float moda(ArrayList<Float> numeros) throws RemoteException;
    double media(ArrayList<Float> numeros) throws RemoteException;
    Integer power(Integer primeiro, Integer segundo) throws RemoteException;
}