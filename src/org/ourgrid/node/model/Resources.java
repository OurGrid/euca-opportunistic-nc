package org.ourgrid.node.model;


/**
 * @author Ourgrid Team
 * Class used as a Data Structure to store machine/node available resources information
 */
public class Resources {

	private int mem, disk, cores;
	
	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}
}
