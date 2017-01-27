package Servidor;

import Interfaces.RMIContactsInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements RMIContactsInterface {

	BBDD database;



	ArrayList<String> contactos = new ArrayList<String>();

	@Override
	public boolean Log(String nombre, int contra) throws RemoteException {
		database = new BBDD();

		if (database.comprobarUser(nombre, contra)) {
			return true;
		} else {
			return false;
		}

	}

	String datos[] = new String[2];

	@Override
	public ArrayList<String> newContact(String nombre, int numero, String email) {
		database = new BBDD();

		contactos.add(nombre);
		contactos.add(numero + "");
		contactos.add(email);

		database.insertContact(nombre, numero, email);

		return contactos;
	}

	@Override
	public int modiContact(String nombre, int numero, String email, int id) {
		database = new BBDD();
		database.modificarContacto(nombre,numero,email,id);

		return id;
	}



	public static void main(String[] args) {
		Registry reg = null;
		try {
			System.out.println("Crea el registro de objetos, escuchando en el puerto 5555");
			reg = LocateRegistry.createRegistry(1099);
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido crear el registro");
			e.printStackTrace();
		}
		System.out.println("Creando el objeto Server");
		Server serverObject = new Server();
		try {
			System.out.println("Inscribiendo el objeto Server en el registro");
			System.out.println("Se le da un nombre unico: Agenda");
			reg.rebind("Agenda", (RMIContactsInterface) UnicastRemoteObject.exportObject(serverObject, 0));
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido inscribir el objeto Server.");
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<String> CheckContacts() {
		database = new BBDD();
		//System.out.println(database.verContactos());

		// TODO Auto-generated method stub
		return database.verContactos();
	}



	@Override
	public ArrayList<String> lookFor(String nombre) throws RemoteException {
		
		database = new BBDD();
		//System.out.println(database.verContactos());

		// TODO Auto-generated method stub
		return database.buscarC(nombre);
		
		
	}
	@Override
	public int deleteContact(int id) {
		database = new BBDD();
		database.borrarcontacto(id);

		return id;
	}

}
