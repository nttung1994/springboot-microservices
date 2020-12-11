package com.linhtinhstuff.rest.webservice.restfulwebservice.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linhtinhstuff.rest.webservice.restfulwebservice.exception.UserNotFoundException;

@RestController
public class UserJpaController {
	
	@Autowired
	private UserDaoService service;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping(path="/jpa/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(path="/jpa/users/{id}")
	public EntityModel<User> getUserById(@PathVariable int id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User id: " + id));
		
		EntityModel<User> entityModel = EntityModel.of(user);
		Link linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers()).withRel("all-users");
		entityModel.add(linkTo);
		
		return entityModel;
	}
	
	@PostMapping(path="/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path="/jpa/users/{id}")
	public void deleteUserById(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping(path="/jpa/users/{userId}/posts")
	public List<Post> getAllPostsOfUser(@PathVariable int userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User id: " + userId));
		
		return user.getPosts();
	}
	
	@PostMapping(path="/jpa/users/{userId}/posts")
	public ResponseEntity<Object> createPost(@PathVariable Integer userId, @RequestBody Post post) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User id: " + userId));
		post.setUser(user);
		postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

}
