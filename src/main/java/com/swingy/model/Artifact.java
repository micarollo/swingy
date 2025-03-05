package com.swingy.model;

public abstract class Artifact {
	private final String type;
	private final int boost;

    public Artifact(String type, int boost) {
		this.type = type;
		this.boost = boost;
    }

	public int getBoost() { return boost; }
	public String getType() { return type; }
}