package info.androidhive.glide.model;

import java.util.ArrayList;

public class Movie {
	private String id;
	private String url;
	private String imdbCode;
	private String title;
	private String titleEnglish;
	private String titleLong;
	private String slug;
	private String year;
	private String rating;
	private String runtime;
	private String summary;
	private String descriptionFull;
	private String synopsis;
	private String ytTrailerCode;
	private String genres;
	private String language;
	private String mpaRating;
	private String backgroundImage;
	private String backgroundImageOriginal;
	private String smallCoverImage;
	private String mediumCoverImage;
	private String largeCoverImage;

	public boolean isCategoryDescriptorTab() {
		return isCategoryDescriptorTab;
	}

	public void setCategoryDescriptorTab(boolean categoryDescriptorTab) {
		isCategoryDescriptorTab = categoryDescriptorTab;
	}

	private String state;
	private String dateUploaded;
	private String dateUploadedUnix;
	private Torrent torrent;
	private String likeCount;
	private Cast cast;
	private ArrayList<Cast> castArr = new ArrayList<Cast>();
	private ArrayList<Torrent> torrentArr = new ArrayList<Torrent>();
	private boolean isCategoryDescriptorTab;



	public ArrayList<Torrent> getTorrentArr() {
		return torrentArr;
	}

	public void setTorrentArr(ArrayList<Torrent> torrentArr) {
		this.torrentArr = torrentArr;
	}

	public Cast getCast() {
		return cast;
	}

	public ArrayList<Cast> getCastArr() {
		return castArr;
	}

	public void setCastArr(ArrayList<Cast> castArr) {
		this.castArr = castArr;
	}

	public void setCast(Cast cast) {
		this.cast = cast;
	}

	public String getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}

	public Torrent getTorrent() {
		return torrent;
	}
	public void setTorrent(Torrent torrent) {
		this.torrent = torrent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImdbCode() {
		return imdbCode;
	}
	public void setImdbCode(String imdbCode) {
		this.imdbCode = imdbCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleEnglish() {
		return titleEnglish;
	}
	public void setTitleEnglish(String titleEnglish) {
		this.titleEnglish = titleEnglish;
	}
	public String getTitleLong() {
		return titleLong;
	}
	public void setTitleLong(String titleLong) {
		this.titleLong = titleLong;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescriptionFull() {
		return descriptionFull;
	}
	public void setDescriptionFull(String descriptionFull) {
		this.descriptionFull = descriptionFull;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getYtTrailerCode() {
		return ytTrailerCode;
	}
	public void setYtTrailerCode(String ytTrailerCode) {
		this.ytTrailerCode = ytTrailerCode;
	}
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getMpaRating() {
		return mpaRating;
	}
	public void setMpaRating(String mpaRating) {
		this.mpaRating = mpaRating;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public String getBackgroundImageOriginal() {
		return backgroundImageOriginal;
	}
	public void setBackgroundImageOriginal(String backgroundImageOriginal) {
		this.backgroundImageOriginal = backgroundImageOriginal;
	}
	public String getSmallCoverImage() {
		return smallCoverImage;
	}
	public void setSmallCoverImage(String smallCoverImage) {
		this.smallCoverImage = smallCoverImage;
	}
	public String getMediumCoverImage() {
		return mediumCoverImage;
	}
	public void setMediumCoverImage(String mediumCoverImage) {
		this.mediumCoverImage = mediumCoverImage;
	}
	public String getLargeCoverImage() {
		return largeCoverImage;
	}
	public void setLargeCoverImage(String largeCoverImage) {
		this.largeCoverImage = largeCoverImage;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
		return "Movie [id=" + id + ", url=" + url + ", imdbCode=" + imdbCode + ", title=" + title + ", titleEnglish="
				+ titleEnglish + ", titleLong=" + titleLong + ", slug=" + slug + ", year=" + year + ", rating=" + rating
				+ ", runtime=" + runtime + ", summary=" + summary + ", descriptionFull=" + descriptionFull
				+ ", synopsis=" + synopsis + ", ytTrailerCode=" + ytTrailerCode + ", genres=" + genres + ", language="
				+ language + ", mpaRating=" + mpaRating + ", backgroundImage=" + backgroundImage
				+ ", backgroundImageOriginal=" + backgroundImageOriginal + ", smallCoverImage=" + smallCoverImage
				+ ", mediumCoverImage=" + mediumCoverImage + ", largeCoverImage=" + largeCoverImage + ", state=" + state
				+ ", dateUploaded=" + dateUploaded + ", dateUploadedUnix=" + dateUploadedUnix + ", torrent=" + torrent
				+ "]";
	}

	
	
	
	
	
}
