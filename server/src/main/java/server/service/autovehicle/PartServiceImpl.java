package server.service.autovehicle;

import lib.dto.autovehicle.PartDto;
import lib.service.PartService;
import server.convert.autovehicle.PartConvertor;
import server.dao.impl.autovehicle.PartDaoImpl;
import server.dao.impl.autovehicle.ServiceOrderDaoImpl;
import server.dao.interfaces.PartDao;
import server.dao.interfaces.ServiceOrderDao;
import server.model.autovehicle.Part;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PartServiceImpl extends UnicastRemoteObject implements PartService {

    private PartDao partDao;
    private ServiceOrderDao serviceOrderDao;

    public PartServiceImpl() throws RemoteException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("serviceAuto");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        partDao = new PartDaoImpl(entityManager);
        serviceOrderDao = new ServiceOrderDaoImpl(entityManager);
    }

    @Override
    public boolean createPart(PartDto partDto) throws RemoteException{
        Part part = PartConvertor.convert(partDto);
        return partDao.createPart(part);

    }

    @Override
    public PartDto findPartById(int id) throws RemoteException{
       Part part = partDao.findPartById(id);
       return PartConvertor.convert(part);
    }

    @Override
    public PartDto findPartByName(String name) throws RemoteException{

       return partDao.findPartByName(name)
               .map(PartConvertor::convert)
               .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Collection<PartDto> findAllParts() throws RemoteException {
        return partDao.findAllParts().stream()
                .map(PartConvertor::convert)
                .collect(Collectors.toSet());

    }
}
