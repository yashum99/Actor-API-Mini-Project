package com.yashu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.yashu.entity.ActorEntity;

public interface IActorRepository extends JpaRepository<ActorEntity, Integer> {

	@Query("from ActorEntity where remunaration>=:start and remunaration<=:end order by aname asc")
	List<ActorEntity> showActorsByRemunaration(double start,double end);
	
	
	@Modifying
	@Transactional
	@Query("delete from ActorEntity where category in(:cat1,:cat2,:cat3)")
	public int deleteActorsByCategory(String cat1,String cat2,String cat3);
}
