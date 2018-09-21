package com;

class BuildEnvironmentNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BuildEnvironmentNotFoundException() {
		super();
		System.out.println("Build Environment Not Found");
	}
	
}