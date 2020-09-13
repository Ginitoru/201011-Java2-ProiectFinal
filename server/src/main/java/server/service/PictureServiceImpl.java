package server.service;

import lib.dto.picture.PictureDto;
import lib.service.PictureService;
import server.convert.PictureConvertor;
import server.dao.PictureDao;
import server.dao.impl.PictureDaoImpl;
import server.model.picture.Picture;

import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class PictureServiceImpl extends UnicastRemoteObject implements PictureService {

    private final PictureDao pictureDao;
    private Path path = Paths.get("./server/src/main/resources/images");
    private final List<PictureDto> pictures = new CopyOnWriteArrayList<>();


    public PictureServiceImpl() throws RemoteException {

        var entityManagerFactory = Persistence.createEntityManagerFactory("serviceAuto");
        var entityManager = entityManagerFactory.createEntityManager();

        this.pictureDao = new PictureDaoImpl(entityManager);
        addPictureToList();
    }

    //method 1
    public void sendPicturesToDatabase(){

        try {
            if(findAllBackgroundPictures().isEmpty()){
                Files.list(path)
                        .forEach(this::sendPicture);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method 2
    private void sendPicture(Path path){
        Picture image = new Picture();
        pictureDao.sendPicturesToDatabase(image, path);
    }


    private List<Picture> findAllBackgroundPictures(){
      return  pictureDao.findAllBackgroundPictures();
    }

    private void addPictureToList(){
       pictureDao.findAllBackgroundPictures().stream()
                .map(PictureConvertor::convert)
                .forEach(pictures::add);
    }

    @Override
    public PictureDto getPicture() throws RemoteException {
        Random random = new Random();

        return pictures.get(random.nextInt(pictures.size()));
    }
}
