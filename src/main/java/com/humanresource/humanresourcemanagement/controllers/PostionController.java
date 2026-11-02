package com.humanresource.humanresourcemanagement.controllers;



import com.humanresource.humanresourcemanagement.entities.Position;
import com.humanresource.humanresourcemanagement.services.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class PostionController {


    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<List<Position>> getAllPosition(){
        return ResponseEntity.ok(positionService.getALlPositions());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id){
        return ResponseEntity.ok(positionService.getPositionById(id));
    }

    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody Position position){
        return ResponseEntity.ok(positionService.createPosition(position));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Position> updatePosition( @PathVariable Long id, @RequestBody Position positionDetails){

        return  ResponseEntity.ok(positionService.updatePosition(id,positionDetails));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity< Void > deletePosition(@PathVariable Long id){
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();

    }
}
