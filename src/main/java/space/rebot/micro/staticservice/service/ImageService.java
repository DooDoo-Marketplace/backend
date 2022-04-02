package space.rebot.micro.staticservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.staticservice.repository.ImagesRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImagesRepository imagesRepository;

//    @Autowired
//    private HttpServletRequest context;


    public void deleteImage(UUID id) {
        //User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        imagesRepository.deleteById(id);
    }

}
