package me.anichakra.poc.pilot.framework.io.pdf;

import me.anichakra.poc.pilot.framework.annotation.Export;


public class TestPdfData {
	@Export(columnName = "name", columnSeq = 0)
    private String name;
	
	@Export(columnName = "id", columnSeq = 1)
    private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
