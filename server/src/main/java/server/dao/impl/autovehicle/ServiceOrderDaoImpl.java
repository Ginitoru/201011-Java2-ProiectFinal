package server.dao.impl.autovehicle;

import lib.dto.autovehicle.Status;
import server.dao.interfaces.ServiceOrderDao;
import server.model.autovehicle.ServiceOrder;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class ServiceOrderDaoImpl implements ServiceOrderDao {

    private EntityManager entityManager;

    public ServiceOrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean createServiceOrder(ServiceOrder serviceOrder){
        entityManager.getTransaction().begin();
        entityManager.persist(serviceOrder);
        entityManager.getTransaction().commit();

        return entityManager.getTransaction().getRollbackOnly();

    }

    @Override
    public ServiceOrder findById(int id){
           ServiceOrder serviceOrder = entityManager.find(ServiceOrder.class, id);
           entityManager.refresh(serviceOrder);
           return serviceOrder;
    }

    @Override
    public boolean updateServiceOrder(ServiceOrder serviceOrder){

        entityManager.getTransaction().begin();


        entityManager.merge(serviceOrder);
        entityManager.getTransaction().commit();

        return entityManager.getTransaction().getRollbackOnly();
    }

    @Override
    public int updateServiceOrderStatus(int orderId, Status status){
        String jpql = "UPDATE ServiceOrder s SET s.status = :status WHERE s.id = :id";

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(jpql);
        query.setParameter("status", status);
        query.setParameter("id", orderId);
        int row = query.executeUpdate();
        entityManager.getTransaction().commit();

        return row;
    }

    @Override
    public List<Object[]> findAllServiceOrderIdAndStatus(){
        String jpql = "SELECT s.id, s.status FROM ServiceOrder AS s";

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);

        return query.getResultList();

    }

    @Override
    public int updateTotalPriceAndStatus(int orderId, double totalPrice, Status status){
        String jpql = "UPDATE ServiceOrder s SET s.status = :status, s.total = :totalPrice  WHERE s.id = :id";

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(jpql);
        query.setParameter("status", status);
        query.setParameter("totalPrice", totalPrice);
        query.setParameter("id", orderId);
        int row = query.executeUpdate();
        entityManager.getTransaction().commit();

        return row;
    }

}

