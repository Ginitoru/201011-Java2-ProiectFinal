package lib.service;

import lib.dto.user.UserDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    boolean create(UserDto userDto) throws RemoteException;

    boolean loginWithUsername(String userName, String password) throws RemoteException;

    boolean loginWithEmailAdress(String emailAdress, String password) throws RemoteException;
}