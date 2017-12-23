package com.wsc.qa.meta;


public class ActivityBanner extends AbstractEntity {

	private static final long serialVersionUID = 6391917172951097003L;

	private String id;

	private String name;

	private String imageUrl;

	private String redirectUrl;

	private String sortno;

	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl == null ? null : imageUrl.trim();
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl == null ? null : redirectUrl.trim();
	}

	public String getSortno() {
		return sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno == null ? null : sortno.trim();
	}

	@Override
	public String primaryKey() {
		return this.id;
	}
}