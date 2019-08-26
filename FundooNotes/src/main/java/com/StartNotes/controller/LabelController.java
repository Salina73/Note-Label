package com.StartNotes.controller;

import com.StartNotes.exception.ResourceNotFoundException;
import com.StartNotes.model.Label;
import com.StartNotes.model.Note;
import com.StartNotes.repository.LabelRepository;
import com.StartNotes.response.Response;
import com.StartNotes.service.UserService;
import com.StartNotes.utility.ResponseHelper;
import com.StartNotes.utility.TokenGeneration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class LabelController {

    @Autowired
    LabelRepository labelRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	TokenGeneration token1;
	
    @Autowired
	Response statusResponse;
  
    // Create a new Label
    @PostMapping("/label")
    public  Response createNote(@RequestHeader String token ,@Valid @RequestBody Label label)
    {
    	Response response=userService.validateEmailId(token);
    	System.out.println(response);
    	if(response.getStatusCode()==200)
    	{
    		Long id = token1.decodeToken(token);
    		label.setLabelid(id);
    		labelRepository.save(label);
    		return ResponseHelper.statusResponse(200, "Lable added successfully");
    	}
    	statusResponse = ResponseHelper.statusResponse(404, "Invalid credentials");
    	return statusResponse;
    }
    
    // Get All Label
    @GetMapping("/label")
    public List<Label> getAllNotes()
    {
        return labelRepository.findAll();
    }
    
    // Update a Lable
    @PutMapping("/label/{id}")
    public Note updateNote(@RequestHeader(value = "id") Long noteId,@Valid @RequestBody Note noteDetails) 
    {	
        Note note = labelRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        Note updatedNote = labelRepository.save(note);
        return updatedNote;
    }
    
    // Delete a Lable
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@RequestHeader(value = "id") Long noteId) {
        Note note = labelRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        labelRepository.delete(note);

        return ResponseEntity.ok().build();
    }
    
    // Get a Single Lable
    @GetMapping("/notes/{id}")
    public Note getNoteById(@RequestHeader(value = "id") Long noteId) {
        return labelRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }
}