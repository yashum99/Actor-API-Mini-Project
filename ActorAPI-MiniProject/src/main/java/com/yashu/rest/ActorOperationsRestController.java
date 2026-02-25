package com.yashu.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashu.service.IActorMgmtService;
import com.yashu.vo.ActorVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/actor-api")
@Tag(name = "Actor" , description = "Actor Operation Controller")
@Slf4j
public class ActorOperationsRestController {
	
	@Autowired
	private IActorMgmtService actorService;
	
	
	
	@Operation(
			summary = "This endpoint is used to save an actor",
			description = "Used to save a single record in the database")
	
	@ApiResponses(value = {
			
			@ApiResponse(responseCode = "201" ,
					     description = "Actor Record sved successfully" ,
					     content=@Content( mediaType = "application/json",
					     schema = @Schema(implementation = String.class) )
					),
			
			 @ApiResponse(
				        responseCode = "400",
				        description = "Invalid input data"
				    ),
			@ApiResponse(
				        responseCode = "500",
				        description = "Internal server error"
				    )
			})
	@PostMapping("/save")
	public ResponseEntity<String> saveActor(@RequestBody ActorVO vo){
		
		log.info("saveActor() post method started... ");
		
			String registerActor = actorService.registerActor(vo);
			
			log.info("saveActor() post method ended... ");
			
			return new ResponseEntity<String>(registerActor, HttpStatus.CREATED);
		
	}
	
	
	
	
	@Operation(
		    summary = "Save multiple actors",
		    description = "This endpoint is used to save multiple actor records into the database"
		)
		@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "201",
		        description = "Actors saved successfully",
		        content = @Content(
		            mediaType = "application/json",
		            schema = @Schema(implementation = String.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "400",
		        description = "Invalid input data"
		    ),
		    @ApiResponse(
		        responseCode = "500",
		        description = "Internal server error"
		    )
		})
	@PostMapping("/saveAll")
	public ResponseEntity<String> saveActor(@RequestBody List<ActorVO> voList){
		
		    log.info("saveActor() post method[saveAll] started... ");
		
			String registerActorList = actorService.registerActorList(voList);
			
			log.info("saveActor() post method[saveAll] ended... ");
			
			return new ResponseEntity<String>(registerActorList, HttpStatus.OK);
		
	}
	
	
	
	@Operation(
		    summary = "Fetch all actors",
		    description = "This endpoint retrieves all actor records from the database"
		)
		@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "List of all actors retrieved successfully",
		        content = @Content(
		            mediaType = "application/json",
		            array = @ArraySchema(schema = @Schema(implementation = ActorVO.class))
		        )
		    ),
		    @ApiResponse(
		        responseCode = "500",
		        description = "Internal server error"
		    )
		})
	@GetMapping("/all")
	public ResponseEntity<List<ActorVO>> displayAllActorsData()
	{
		log.info("displayAllActorsData() get method started... ");
		
		List<ActorVO> showAllActors = actorService.showAllActors();
		
		log.info("displayAllActorsData() get method ended... ");
		
		return new ResponseEntity<List<ActorVO>>(showAllActors, HttpStatus.OK);
		
		
	}
	
	@GetMapping("/byId/{id}")
	public ResponseEntity<ActorVO> displayActorById(@PathVariable Integer id)
	{
		log.info("displayActorById() get method started... ");
		
		ActorVO showActorById = actorService.showActorById(id);
		
		log.info("displayActorById() get method ended... ");
		
		return new ResponseEntity<ActorVO>(showActorById, HttpStatus.OK);
		
		
	}
	
	@GetMapping("/byremunaration/{start}/{end}")
	public ResponseEntity<List<ActorVO>> displayActorByRemunaration(@PathVariable double start,@PathVariable double end)
	{
		log.info("displayActorByRemunaration() GET method started...");
		
		List<ActorVO> showActorByRemunaration = actorService.showActorByRemunaration(start, end);
		
		log.info("displayActorByRemunaration() GET method ended...");
		
		return new ResponseEntity<List<ActorVO>>(showActorByRemunaration, HttpStatus.OK);
		
		
	}
	@PatchMapping("/update/{aid}/{percent}")
	public ResponseEntity<String> udateActorRemunaeation(@PathVariable Integer aid,@PathVariable double percent)
	{
		log.info("udateActorRemunaeation() Patch method started...");
		
		String msg = actorService.updateActorByRemunaration(aid, percent);
		
		log.info("udateActorRemunaeation() Patch method ended...");
		
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> udateActorData(@RequestBody ActorVO vo)
	{
		log.info("udateActorData() Put method started...");
		
		String msg = actorService.updateActor(vo);
		
		log.info("udateActorData() Put method ended...");
		
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable int id)
	{
		log.info("deleteById() Delete method started...");
		
		String msg = actorService.removeActorById(id);
		
		log.info("deleteById() Delete method ended...");
		
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{cat1}/{cat2}/{cat3}")
	public ResponseEntity<String> deleteByCategory(@PathVariable String cat1,@PathVariable String cat2,@PathVariable String cat3)
	{
		log.info("deleteByCategory() Delete method started...");
		
		String msg = actorService.removeActorByCategory(cat1, cat2, cat3);
		
		log.info("deleteByCategory() Delete method ended...");
		
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
	}

}
