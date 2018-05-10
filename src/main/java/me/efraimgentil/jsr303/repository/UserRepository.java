package me.efraimgentil.jsr303.repository;

import me.efraimgentil.jsr303.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {


    boolean existsByUserName(String username);

    boolean existsByUserNameEqualsAndIdNot(String username , Integer id);


}
