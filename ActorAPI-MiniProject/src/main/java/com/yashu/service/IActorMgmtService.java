package com.yashu.service;

import java.util.List;

import com.yashu.vo.ActorVO;


public interface IActorMgmtService {
	
	public String registerActor(ActorVO vo);
	
	public String registerActorList(List<ActorVO> vo);

	public List<ActorVO> showAllActors();
	
	public ActorVO showActorById(Integer id);
	
	public List<ActorVO> showActorByRemunaration(double start,double end);
	
	public String updateActorByRemunaration(Integer aid,double percent);
	
	public String updateActor(ActorVO vo);
	
	public String removeActorById(int id);
	
	public String removeActorByCategory(String cat1,String cat2,String cat3);
	
	
}
