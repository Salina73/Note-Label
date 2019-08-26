package com.StartNotes.controller;

import com.StartNotes.exception.ResourceNotFoundException;
import com.StartNotes.model.Note;
import com.StartNotes.repository.NoteRepository;
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
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	TokenGeneration token1;
	
    @Autowired
	Response statusResponse;
    
    Note updatedNote;
  
    // Create a new Note
    @PostMapping("/notes/token")
    public  Response createNote(@RequestHeader String token ,@Valid @RequestBody Note note)
    {
    	Response response=userService.validateEmailId(token);
    	System.out.println(response);
    	if(response.getStatusCode()==200)
    	{
    		Long id = token1.decodeToken(token);
    		note.setuserid(id);
    		noteRepository.save(note);
    		return ResponseHelper.statusResponse(200, "Note added successfully");
    	}
    	statusResponse = ResponseHelper.statusResponse(404, "Invalid credentials");
    	return statusResponse;
    }
    
    // Get All Notes
    @GetMapping("/notes")
    public List<Note> getAllNotes()
    {
        return noteRepository.findAll();
    }
    
    // Update a Note
    @PutMapping("/notes/token")
    public Note updateNote(@RequestHeader String token, @Valid @RequestBody Note noteDetails) 
    {	
    	Response response=userService.validateEmailId(token);
    	System.out.println(response);
    	if(response.getStatusCode()==200)
    	{
    		Long id = token1.decodeToken(token);
    		Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
    	
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        updatedNote = noteRepository.save(note);
    	}
        return updatedNote;
    	
    }
    
    // Delete a Note
    @DeleteMapping("/notes/id")
    public ResponseEntity<?> deleteNote(@RequestHeader(value = "id") Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);

        return ResponseEntity.ok().build();
    }
    
    // Get a Single Note
    @GetMapping("/notes/{id}")
    public Note getNoteById(@RequestHeader(value = "id") Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }
}