package com.example.uploadpoc_db.message;

public class ResponseFile {
	private String name;
	private String url;
	private String type;
	private long size;
	public ResponseFile(String _name, String _url, String _type, long _size) {
		name = _name;
		url = _url;
		type = _type;
		size = _size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
