package com.pavmaxdav.mymess.service;

import com.pavmaxdav.mymess.entity.UserData;
import com.pavmaxdav.mymess.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDataService {
    private UserDataRepository userDataRepository;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Transactional
    public void removeById(Integer id) {
        UserData userData = userDataRepository.getById(id);
        userData.getUser().setUserData(null);

        userDataRepository.removeById(id);
    }
}
