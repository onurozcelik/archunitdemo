package net.onurozcelik.archunitdemo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.onurozcelik.archunitdemo.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
