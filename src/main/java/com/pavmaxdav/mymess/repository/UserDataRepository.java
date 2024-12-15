package com.pavmaxdav.mymess.repository;

import com.pavmaxdav.mymess.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    UserData getById(@NonNull Integer id);
    void removeById(Integer id);
}
