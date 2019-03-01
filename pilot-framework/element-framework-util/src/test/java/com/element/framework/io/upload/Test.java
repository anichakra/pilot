package me.anichakra.poc.pilot.framework.io.upload;

import me.anichakra.poc.pilot.framework.annotation.Export;
import me.anichakra.poc.pilot.framework.annotation.Upload;

public class Test {
	@Export(columnName = "cli_no", columnSeq = 0)
	@Upload(columnName = "cli_no", columnSeq = 0, isMandatory = false)
	private String id;
	
	private String name;
	
	@Export(columnName = "empl_id", columnSeq = 1)
	@Upload(columnName = "empl_id", columnSeq = 1, isMandatory = true)	
	private String nameId;


	public String getId() { 
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	

}
