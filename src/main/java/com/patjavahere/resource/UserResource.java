package com.patjavahere.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.patjavahere.exception.UserNotFoundException;
import com.patjavahere.model.User;
import com.patjavahere.services.UserDaoService;

@RestController
public class UserResource {
	@Autowired
	private UserDaoService service;

	@GetMapping("/users")
	public List<User> retriveAllUsers() {
		return this.service.findAll();
	}

	@GetMapping("/users/{id}")
	public EntityModel<User> retriveUser(@PathVariable final int id) {
		final User user = this.service.findOne(id);
		if (user == null)
			throw new UserNotFoundException("id: " + id);

		return EntityModel.of(user, linkTo(methodOn(this.getClass()).retriveUser(user.getId())).withSelfRel(),
				linkTo(methodOn(this.getClass()).retriveAllUsers()).withRel("users"));
	}

//method that delete a user resource
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable final int id) {
		final User user = this.service.deleteById(id);
		if (user == null)
//runtime exception
			throw new UserNotFoundException("id: " + id);
	}

//method that posts a new user detail and returns the status of the user resource
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody final User user) {
		final User sevedUser = this.service.save(user);
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(sevedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}