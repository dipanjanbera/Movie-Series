package info.androidhive.glide.model;
public class Torrent {

	private String url;
	private String quality;
	private String seeds;
	private String peers;
	private String size;
	private String sizeBytes;
	private String dateUploaded;
	private String dateUploadedUnix;
	private String hash;
	private String movieTitle;

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getSeeds() {
		return seeds;
	}
	public void setSeeds(String seeds) {
		this.seeds = seeds;
	}
	public String getPeers() {
		return peers;
	}
	public void setPeers(String peers) {
		this.peers = peers;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSizeBytes() {
		return sizeBytes;
	}
	public void setSizeBytes(String sizeBytes) {
		this.sizeBytes = sizeBytes;
	}
	public String getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(String dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	public String getDateUploadedUnix() {
		return dateUploadedUnix;
	}
	public void setDateUploadedUnix(String dateUploadedUnix) {
		this.dateUploadedUnix = dateUploadedUnix;
	}
	@Override
	public String toString() {
		return "Torrent [url=" + url + ", quality=" + quality + ", seeds=" + seeds + ", peers=" + peers + ", size="
				+ size + ", sizeBytes=" + sizeBytes + ", dateUploaded=" + dateUploaded + ", dateUploadedUnix="
				+ dateUploadedUnix + "]";
	}
	
	
	
	
}
