package com.springboot.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, String> {

	// Spring automatically gives implementation for filtering rows
	// Syntax is"findBy<variable name>"
	// If filtering has to be done based on custom object/entity type , then syntax
	// is "findBy<variable Name><variable in entity>"
	public List<Course> findByTopicId(String topicId);

}
