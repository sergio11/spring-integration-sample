package sanchez.sanchez.sergio.bullkeeper.web.dto.response;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SentimentLevelEnum;

/**
 * @author sergio
 */
public class SentimentAnalysisStatisticsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("subtitle")
	private String subtitle;
	
	@JsonProperty("sentiment_data")
	private List<SentimentDTO> data;
    
	public SentimentAnalysisStatisticsDTO(){}
	
    public SentimentAnalysisStatisticsDTO(String title, String subtitle, List<SentimentDTO> data) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.data = data;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public List<SentimentDTO> getData() {
		return data;
	}

	public void setData(List<SentimentDTO> data) {
		this.data = data;
	}

	public static class SentimentDTO {
		
		@JsonProperty("type")
		private String type;
		
		@JsonProperty("score")
		private float score;
		
		@JsonProperty("label")
		private String label;

		public SentimentDTO(String type, float score, String label) {
			super();
			this.type = type;
			this.score = score;
			this.label = label;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return "SentimentDTO [type=" + type + ", score=" + score + ", label=" + label + "]";
		}

		
	}

	@Override
	public String toString() {
		return "SentimentAnalysisStatisticsDTO [title=" + title + ", subtitle=" + subtitle + ", data=" + data + "]";
	}


	
}
