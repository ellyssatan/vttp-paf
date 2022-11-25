package vttp_pa.day22.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_pa.day22.models.User;
import vttp_pa.day22.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository uRepo;
    
    public boolean createUser(final User user) throws Exception {

        int count = uRepo.createUser(user);
        System.out.printf("Insert user count: %d", count);

        return count > 0;
    }

    public boolean findUser(User user) throws Exception {
        return uRepo.findUser(user);
    }
}
