package com.example.s27560tpo10.services;

import com.example.s27560tpo10.model.Link;
import com.example.s27560tpo10.model.LinkDTO;
import com.example.s27560tpo10.model.NewLinkDTO;
import com.example.s27560tpo10.repository.LinksRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LinksService {
    private final LinksRepository linksRepository;
    private final LinkDtoMapper mapper;

    public LinksService(LinksRepository linksRepository, LinkDtoMapper mapper) {
        this.linksRepository = linksRepository;
        this.mapper = mapper;
    }

    public LinkDTO saveLink(LinkDTO linkDTO){
        linkDTO.setId(LinkDtoMapper.generateId());
        linkDTO.setRedirectUrl("http://localhost:8080/" + linkDTO.getId());
        Link link = linksRepository.save(mapper.map(linkDTO));
        return mapper.map(link);
    }
    public LinkDTO saveLink(NewLinkDTO newLinkDTO){
        Link link = linksRepository.save(mapper.map(newLinkDTO));
        return mapper.map(link);
    }
    public Optional<LinkDTO> getLinkById(String id){
        return linksRepository.findById(id).map(mapper::map);
    }

    public Optional<String> getRedirectionById(String id){
        //add +1 to visits
        try{
            LinkDTO linkDTO = getLinkById(id).orElseThrow();
            linkDTO.setVisits(linkDTO.getVisits() + 1);
            System.out.println("added visit, current visit is: " + linkDTO.getVisits());
            updateLink(linkDTO);
        }catch(NoSuchElementException e){e.printStackTrace();}
        return getLinkById(id).map(LinkDTO::getTargetUrl);
    }

    public Optional<Link> updateLink(LinkDTO linkDTO){
        return Optional.of(linksRepository.save(mapper.map(linkDTO)));
    }

    public void deleteLink(String id){linksRepository.deleteById(id);}
}
