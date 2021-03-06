package server.service.autovehicle;

import lib.dto.autovehicle.VehicleDto;
import server.convert.autovehicle.VehicleConvetor;
import server.dao.interfaces.CompanyDao;
import server.dao.interfaces.PersonDao;
import server.dao.interfaces.VehicleDao;
import server.dao.impl.autovehicle.VehicleDaoImpl;
import server.dao.impl.client.CompanyDaoImpl;
import server.dao.impl.client.PersonDaoImpl;
import server.model.autovehicle.Vehicle;
import server.model.client.Company;
import server.model.client.Person;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehicleServiceImpl extends UnicastRemoteObject implements lib.service.VehicleService {

    private final VehicleDao vehicleDao;
    private final CompanyDao companyDao;
    private final PersonDao personDao;



    public VehicleServiceImpl() throws RemoteException {
        var entityManagerFactory = Persistence.createEntityManagerFactory("serviceAuto");
        var entityManager = entityManagerFactory.createEntityManager();
        vehicleDao = new VehicleDaoImpl(entityManager);
        personDao = new PersonDaoImpl(entityManager);
        companyDao = new CompanyDaoImpl(entityManager);

    }

    @Override
    public boolean createVehicle(VehicleDto vehicleDto) throws RemoteException{

        Vehicle vehicle = VehicleConvetor.convert(vehicleDto);


        try{

            Person person = personDao.findPersonById(vehicleDto.getClientDto().getId());

           if(person != null){
               vehicle.setClient(person);

           }else{
               Company company = companyDao.findCompanyById(vehicleDto.getClientDto().getId());
               vehicle.setClient(company);
           }


        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("vehicle.getClientDto()  -> null");
        }

        Optional<Vehicle> optionalVehicle = vehicleDao.findBySerialNumber(vehicle.getSerialNumber());


        if(optionalVehicle.isEmpty()) {
            return vehicleDao.createVehicle(vehicle);
        }

        throw new IllegalArgumentException();
    }




    @Override
    public VehicleDto findById(int id) throws RemoteException{
        return VehicleConvetor.convert(vehicleDao.findById(id));
    }

    @Override
    public VehicleDto findBySerialNumber(String serialNumeber){
        return vehicleDao.findBySerialNumber(serialNumeber)
                .map(VehicleConvetor::convert)
                .orElseThrow(NoSuchElementException::new);

    }

    @Override
    public Collection<VehicleDto> findAllVehicles() throws RemoteException{
        return vehicleDao.findAllVehicles().stream()
                                .map(VehicleConvetor::convert)
                                .collect(Collectors.toSet());
    }

    @Override
    public List<Object[]> findVehicleWithClient(String serialNumber) throws RemoteException{
        return vehicleDao.findVehicleWithClient(serialNumber);


    }

}
