package com.example.s27560tpo10.services;

import com.example.s27560tpo10.model.Link;
import com.example.s27560tpo10.model.LinkDTO;
import com.example.s27560tpo10.model.NewLinkDTO;
import org.springframework.stereotype.Service;

@Service
public class LinkDtoMapper {
    public LinkDTO map(Link link){
        LinkDTO linkDTO = new LinkDTO();
        linkDTO.setId(link.getId());
        linkDTO.setName(link.getName());
        linkDTO.setTargetUrl(link.getTargetUrl());
        linkDTO.setRedirectUrl(link.getRedirectUrl());
        linkDTO.setPassword(link.getPassword());
        return linkDTO;
    }

    public Link map(LinkDTO linkDTO){
        Link link = new Link();
        link.setId(linkDTO.getId());
        link.setName(linkDTO.getName());
        link.setTargetUrl(linkDTO.getTargetUrl());
        link.setRedirectUrl(linkDTO.getRedirectUrl());
        link.setPassword(linkDTO.getPassword());
        link.setVisits(linkDTO.getVisits());
        return link;
    }
    public Link map(NewLinkDTO newLinkDTO){
        Link link = new Link();
        link.setId(generateId());
        link.setName(newLinkDTO.getName());
        link.setPassword(newLinkDTO.getPassword());
        link.setTargetUrl(newLinkDTO.getTargetUrl());
        link.setRedirectUrl("http://localhost:8080/red/" + link.getId());
        link.setVisits(link.getVisits());
        return link;
    }

    public static String generateId(){
        String id = "";
        for(int i = 0; i < 10; i ++){
            char c = (char) ((Math.random() * (90 - 65 + 1)) + 65);
            String str = String.valueOf(c);
            if(((int) (Math.random() * (1 + 1))) == 1) {
                str = str.toLowerCase();
            };
            id += str;
        }
        return id;
    }
}
