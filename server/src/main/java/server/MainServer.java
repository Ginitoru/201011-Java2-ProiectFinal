package server;

import client.controller.PictureController;
import server.service.PictureServiceImpl;
import server.service.UserServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {
    public static void main(String[] args) {

        try {
            PictureServiceImpl x = new PictureServiceImpl();
            x.sendPicturesToDatabase();


        } catch (RemoteException e) {
            e.printStackTrace();
        }


        try {
            Registry registry = LocateRegistry.createRegistry(4545);
            registry.rebind("imageService", new PictureServiceImpl());
            registry.rebind("userService", new UserServiceImpl());

        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }



        System.out.println("++++++++++++++++++");
        System.out.println(PictureController.getInstance().getPicture());

    }
}
