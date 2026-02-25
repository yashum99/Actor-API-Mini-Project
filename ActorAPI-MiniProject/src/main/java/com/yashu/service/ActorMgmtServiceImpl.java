package com.yashu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yashu.entity.ActorEntity;
import com.yashu.exception.ActorNotFoundException;
import com.yashu.repository.IActorRepository;
import com.yashu.vo.ActorVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActorMgmtServiceImpl implements IActorMgmtService {

	@Autowired
	IActorRepository actorRepo;

	@Override
	public String registerActor(ActorVO vo) {
		log.info("registerActor() started for actor name: {}", vo.getAname());

		ActorEntity entity = new ActorEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setCreatedBy(System.getProperty("user.name"));

		Integer aid = actorRepo.save(entity).getAid();

		log.info("registerActor() completed successfully with id: {}", aid);

		return "Data Saved with id value::" + aid;
	}

	@Override
	public String registerActorList(List<ActorVO> voList) {

		log.info("registerActorList() started. Number of actors to save: {}", voList.size());

		List<ActorEntity> enties = new ArrayList<>();

		voList.forEach(avo -> {
			ActorEntity ae = new ActorEntity();

			BeanUtils.copyProperties(avo, ae);
			ae.setCreatedBy(System.getProperty("user.name"));
			enties.add(ae);

			log.debug("Prepared actor entity for: {}", avo.getAname());

		});

		List<ActorEntity> entities = actorRepo.saveAll(enties);

		log.info("registerActorList() completed successfully. {} actors saved.", entities.size());

		return "All Actors are saved...";
	}

	@Override
	public List<ActorVO> showAllActors() {

		log.info("showAllActors() method started");

		List<ActorEntity> actorList = actorRepo.findAll();
		log.info("Number of actors retrieved from database: {}", actorList.size());

		List<ActorVO> actorVO = new ArrayList<>();
		actorList.forEach(entites -> {

			ActorVO vo = new ActorVO();

			BeanUtils.copyProperties(entites, vo);

			actorVO.add(vo);

			log.debug("Prepared ActorVO for actor: {}", vo.getAname());

		});

		log.info("showAllActors() completed successfully. Returning {} actors.", actorVO.size());
		return actorVO;
	}

	@Override
	public ActorVO showActorById(Integer id) {
		log.info("showActorById() started for id: {}", id);

		ActorEntity entity = actorRepo.findById(id).orElseThrow(() -> {

			log.warn("Actor with id {} not found", id);
			return new ActorNotFoundException("Invalid id !!");
		});

		ActorVO vo = new ActorVO();
		BeanUtils.copyProperties(entity, vo);

		log.info("showActorById() completed successfully for id: {}", id);
		return vo;
	}

	@Override
	public List<ActorVO> showActorByRemunaration(double start, double end) {

		 log.info("showActorByRemunaration() started for range: {} - {}", start, end);
		
		List<ActorEntity> actorList = actorRepo.showActorsByRemunaration(start, end);
		log.info("Number of actors retrieved from database: {}", actorList.size());

		List<ActorVO> actorVOList = new ArrayList<>();

		actorList.forEach(entites -> {

			ActorVO vo = new ActorVO();

			BeanUtils.copyProperties(entites, vo);

			actorVOList.add(vo);
	        log.debug("Prepared ActorVO for actor: {}", vo.getAname()); 

		});

	    log.info("showActorByRemunaration() completed successfully. Returning {} actors.", actorVOList.size());
		return actorVOList;
	}

	@Override
	public String updateActorByRemunaration(Integer aid, double percent) {
	    log.info("updateActorByRemunaration() started for actor id: {} with percent increase: {}%", aid, percent);

	    ActorEntity entity = actorRepo.findById(aid)
	            .orElseThrow(() -> {
	                log.warn("Actor with id {} not found", aid);
	                return new ActorNotFoundException("Invalid id !!");
	            });

	    double oldRemunaration = entity.getRemunaration();
	    double newRemunaration = oldRemunaration + (oldRemunaration * percent / 100.0);
	    entity.setRemunaration(newRemunaration);

	    actorRepo.save(entity);

	    log.info("Actor id: {} remuneration updated from {} to {}", aid, oldRemunaration, newRemunaration);
	    log.info("updateActorByRemunaration() completed successfully for actor id: {}", aid);

	    return aid + " Id Actor remuneration updated successfully!!";
	}

	@Override
	public String updateActor(ActorVO vo) {
	    log.info("updateActor() started for actor id: {}", vo.getAid());

	    ActorEntity entity = actorRepo.findById(vo.getAid())
	            .orElseThrow(() -> {
	                log.warn("Actor with id {} not found", vo.getAid());
	                return new ActorNotFoundException("Invalid id !!");
	            });

	    BeanUtils.copyProperties(vo, entity);
	    entity.setUpdatedBy(System.getProperty("user.name"));

	    actorRepo.save(entity);

	    log.info("updateActor() completed successfully for actor id: {} by user: {}", vo.getAid(), entity.getUpdatedBy());

	    return vo.getAid() + " Actor is updated successfully.";
	}
	
	@Override
	public String removeActorById(int id) {
	    log.info("removeActorById() started for actor id: {}", id);

	    ActorEntity entity = actorRepo.findById(id)
	            .orElseThrow(() -> {
	                log.warn("Actor with id {} not found", id);
	                return new ActorNotFoundException("Invalid id !!");
	            });

	    actorRepo.deleteById(id);

	    log.info("removeActorById() completed successfully for actor id: {}", id);

	    return id + " Actor deleted successfully!!!";
	}

	@Override
	public String removeActorByCategory(String cat1, String cat2, String cat3) {
	    log.info("removeActorByCategory() started for categories: {}, {}, {}", cat1, cat2, cat3);

	    int count = actorRepo.deleteActorsByCategory(cat1, cat2, cat3);

	    if (count == 0) {
	        log.warn("No actors found in categories: {}, {}, {}", cat1, cat2, cat3);
	        return "Actors not found in those categories";
	    }

	    log.info("{} actors deleted successfully for categories: {}, {}, {}", count, cat1, cat2, cat3);
	    return count + " Actors deleted successfully";
	}

}
