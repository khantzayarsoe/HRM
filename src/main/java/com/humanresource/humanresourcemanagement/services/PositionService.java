package com.humanresource.humanresourcemanagement.services;



import com.humanresource.humanresourcemanagement.entities.Position;
import com.humanresource.humanresourcemanagement.repositories.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository ;

    public List<Position> getALlPositions(){
        return positionRepository.findAll();
    }

    public Position getPositionById  (Long id){
        return positionRepository.findById(id).orElseThrow(()-> new RuntimeException("Department Not Found"+id));
    }

    public  Position createPosition(Position position){
        return positionRepository.save(position);
    }

    public Position updatePosition(Long id, @NonNull Position positionDetails){
        Position position=getPositionById(id);
        position.setPositionName(positionDetails.getPositionName());
        position.setDesc(positionDetails.getDesc());
        return positionRepository.save(position);
    }

    public  void deletePosition(Long id){
        positionRepository.deleteById(id);

    }
}
