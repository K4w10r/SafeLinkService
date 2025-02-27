package com.example.s27560tpo10.controller;

import com.example.s27560tpo10.model.LinkDTO;
import com.example.s27560tpo10.model.NewLinkDTO;
import com.example.s27560tpo10.services.LinksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class LinksApiController {

    private final LinksService linksService;
    private final ObjectMapper objectMapper;

    public LinksApiController(LinksService linksService, ObjectMapper objectMapper) {
        this.linksService = linksService;
        this.objectMapper = objectMapper;
    }

    @Tag(name = "POST", description = "Add new link")
    @PostMapping("/api/links")
    public ResponseEntity<LinkDTO> createLink(@Valid @RequestBody NewLinkDTO link){
        /*try{
            System.out.println(objectMapper.writeValueAsString(link));
        }catch(JsonProcessingException e){e.printStackTrace();}*/
        LinkDTO savedLink = linksService.saveLink(link);
        URI savedBookLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/api/links/{id}")
                .buildAndExpand(savedLink.getId())
                .toUri();
        return ResponseEntity.created(savedBookLocation).body(savedLink);
    }

    @Tag(name = "GET", description = "Get information about link")
    @GetMapping("/api/links/{id}")
    public ResponseEntity<LinkDTO> getLink(@PathVariable String id){
        return linksService.getLinkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "GET", description = "View site from link")
    @GetMapping("/red/{id}")
    public ResponseEntity<String> redirectLink(@PathVariable String id){
        return linksService.getRedirectionById(id)
                .map(link -> ResponseEntity.status(302).body(link))
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "PATCH", description = "Update link information")
    @PatchMapping ("/api/links/{id}")
    public ResponseEntity<?> updateLink(@PathVariable String id, @RequestBody JsonMergePatch patch){
        try {
            LinkDTO linkDTO = linksService.getLinkById(id).orElseThrow();
            LinkDTO patchedLinkDTO = applyPatch(linkDTO, patch);
            JsonNode patchNode = objectMapper.valueToTree(patch);
            if(!Objects.equals(patchNode.get("password").asText(), linkDTO.getPassword())){
                return ResponseEntity.status(403).body("reason : wrong password");
            }
            linksService.updateLink(patchedLinkDTO);
        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
    }

    private LinkDTO applyPatch(LinkDTO linkDTO, JsonMergePatch patch) throws JsonProcessingException, JsonPatchException {
        JsonNode bookNode = objectMapper.valueToTree(linkDTO);
        JsonNode patchNode = patch.apply(bookNode);
        return objectMapper.treeToValue(patchNode, LinkDTO.class);
    }

    @Tag(name = "DELETE", description = "Delete link")
    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<?> deleteLink(@PathVariable String id,@RequestBody JsonMergePatch patch){
        try{
            LinkDTO linkDTO = linksService.getLinkById(id).orElseThrow();
            JsonNode patchNode = objectMapper.valueToTree(patch);
            if(!Objects.equals(patchNode.get("password").asText(), linkDTO.getPassword())){
                return ResponseEntity.status(403).body("reason : wrong password");
            }
            linksService.deleteLink(id);
        } catch (NoSuchElementException ex){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handle(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
