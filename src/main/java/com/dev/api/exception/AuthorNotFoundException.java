package com.dev.api.exception;

import javax.persistence.NoResultException;

public class AuthorNotFoundException extends NoResultException {
	
	private static final long serialVersionUID = 1577527311916107219L;

	public AuthorNotFoundException(Long id){
		super("Could not find author " + id);
	}
}
