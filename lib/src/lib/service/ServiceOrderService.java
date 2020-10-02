package lib.service;

import lib.dto.autovehicle.PartDto;
import lib.dto.autovehicle.ServiceOrderDto;
import lib.dto.autovehicle.Status;
import lib.dto.bill.BillDto;
import lib.dto.bill.TotalPriceDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

public interface ServiceOrderService extends Remote {

    boolean createServiceOrder(ServiceOrderDto serviceOrderDto) throws RemoteException;

    ServiceOrderDto findById(int id) throws RemoteException;

    Collection<ServiceOrderDto> findAll() throws RemoteException;

    List<Integer> findAllServiceOrderIds() throws RemoteException;

    boolean updateServiceOrder(ServiceOrderDto serviceOrderDto) throws RemoteException;

    int updateParsAndPartsCount(int orderId) throws RemoteException;

    List<Object[]> findOrdersByIds(int id) throws RemoteException;

    List<PartDto> initInfoOnPartPageAndCreateOrderPage(int orderId) throws RemoteException;

    int setTotalPriceToOrder(int orderId, double totalPrice) throws RemoteException;

    void makeBill(List<PartDto> partsDtos, String path, BillDto billDto, TotalPriceDto totalPriceDto) throws RemoteException;

    int updateServiceOrderStatus(int orderId, Status status) throws RemoteException;

    List<Object[]> findAllServiceOrderIdAndStatus() throws RemoteException;
}
