package cliente;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import Interfaces.RMIContactsInterface;

public class ClientContact {
    public static void main(String[] args) throws RemoteException {
        RMIContactsInterface agenda = null;
        try {
            System.out.println("Localizando el registro de objetos remitos");
            Registry registry = LocateRegistry.getRegistry("localhost", 5555);
            System.out.println("Obteniendo el stab del objeto remoto");
            agenda = (RMIContactsInterface) registry.lookup("Agenda");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer respuesta = 1;

        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca su nombre");
        String nombre = sc.next();

        System.out.println("Introduzca su contrasena");
        int contra = sc.nextInt();


        if (agenda.Log(nombre, contra)) {

            System.out.println("Login correcto, " + nombre);
            while (respuesta != 0) {

                if (agenda != null) {
                    System.out.println("Realizando operaciones con el objeto remoto");

                    System.out.println(".......................... \n" + ".  0 Salir \n"
                            + ".  1 Ver contactos  \n" + ".  2 Crear contacto \n" + ".  3 Modificar contacto \n"
                            + ".  4 Borrar contacto  \n" + ".  5  Buscar contacto  \n" + ".  6 Salir  \n");

                    respuesta = sc.nextInt();

                    if (respuesta == 1) {
                        System.out.println("Ver lista contactos \n " + (agenda).CheckContacts());
                    }
                    if (respuesta == 2) {
                        System.out.println("Introduzca nuevo nombre");
                        String Nombre = sc.next();

                        System.out.println("Introduzca nuevo numero");
                        int numero = sc.nextInt();

                        System.out.println("Introduzca nuevo email");
                        String email = sc.next();

                        System.out.println("Nuevo contacto " + (agenda).newContact(Nombre, numero, email));

                    }
                    if (respuesta == 3) {

                        System.out.println((agenda).CheckContacts());

                        System.out.println("Escribe la id delcontacto a modificar ");
                        int idM = sc.nextInt();

                        System.out.println("Introduzca nuevo nombre ");
                        String nombreM = sc.next();

                        System.out.println("Introduzca nuevo numero");
                        int numero = sc.nextInt();

                        System.out.println("Introduzca nuevo  email");
                        String email = sc.next();

                        System.out.println(
                                "Contacto modificado " + (agenda).modiContact(nombreM, numero, email, idM));
                    }
                    if (respuesta == 4) {

                        System.out.println((agenda).CheckContacts());

                        System.out.println("Escribe la id del contacto que necesita a borrar ");
                        int idB = sc.nextInt();

                        System.out.println((agenda).deleteContact(idB));
                        System.out.println("Borrado");
                    }
                    if (respuesta == 5) {
                        System.out.println("Escribe el nombre del contacto ");
                        String nom = sc.next();

                        System.out.println((agenda).lookFor(nom));
                        // System.out.println("Borrado");
                    }
                    if (respuesta == 6) {
                        System.out.println("Sesion cerrada");
                        main(args);

                    }
                }
            }
        } else {
            System.out.println("Login incorrecto");
            main(args);
        }

    }
}
