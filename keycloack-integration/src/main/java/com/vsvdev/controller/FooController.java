package com.vsvdev.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vsvdev.dto.ResponseMessage;
import com.vsvdev.model.Foo;

@RestController
@RequestMapping("/foo")
@CrossOrigin
public class FooController {

	List<Foo> foos = Stream.of(new Foo(1, "foo 1"), new Foo(2, "foo 2"), new Foo(3, "foo 3"))
			.collect(Collectors.toList());

	@GetMapping("/list")
	@RolesAllowed("backend-user")
	public ResponseEntity<List<Foo>> list() {
		return new ResponseEntity(foos, HttpStatus.OK);
	}

	@RolesAllowed("backend-user")
	@GetMapping("/detail/{id}")
	public ResponseEntity<Foo> detail(@PathVariable("id") int id) {
		Foo foo = foos.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
		return new ResponseEntity(foo, HttpStatus.OK);
	}

	@RolesAllowed("backend-admin")
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Foo foo) {
		int maxIndex = foos.stream().max(Comparator.comparing(m -> m.getId())).get().getId();
		foo.setId(maxIndex + 1);
		foos.add(foo);
		return new ResponseEntity(new ResponseMessage("created"), HttpStatus.CREATED);
	}

	@RolesAllowed("backend-admin")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Foo foo) {
		Foo fooUpdate = foos.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
		fooUpdate.setName(foo.getName());
		return new ResponseEntity(new ResponseMessage("updated"), HttpStatus.OK);
	}

	@RolesAllowed("backend-admin")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		Foo foo = foos.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
		foos.remove(foo);
		return new ResponseEntity(new ResponseMessage("deleted"), HttpStatus.OK);
	}
}
