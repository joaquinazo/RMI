package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RMIContactsInterface extends Remote {

	public boolean Log(String name, int pwd) throws RemoteException;
	
	public ArrayList<String> CheckContacts() throws RemoteException;

	public ArrayList<String> newContact(String nombre, int numero, String email)throws RemoteException;

	public int modiContact(String nombre, int numero, String email, int id)throws RemoteException;

	public int deleteContact(int id)throws RemoteException;
	
	public ArrayList<String> lookFor(String nombre)throws RemoteException;
}
