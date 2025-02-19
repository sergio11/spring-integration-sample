package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Drugs Results Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class DrugsResultsEntity extends ResultsEntity {
	
	/**
	 * Total Comments Drugs
	 */
	@Field("total_comments_drugs")
	private long totalCommentsDrugs;
	
	/**
	 * Total Comments No Drugs
	 */
	@Field("total_comments_nodrugs")
	private long totalCommentsNoDrugs;
	

	public DrugsResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public DrugsResultsEntity(Boolean obsolete, Date date, long totalCommentsDrugs, long totalCommentsNoDrugs) {
		super(obsolete, date);
		this.totalCommentsDrugs = totalCommentsDrugs;
		this.totalCommentsNoDrugs = totalCommentsNoDrugs;
	}

	public long getTotalCommentsDrugs() {
		return totalCommentsDrugs;
	}

	public void setTotalCommentsDrugs(long totalCommentsDrugs) {
		this.totalCommentsDrugs = totalCommentsDrugs;
	}

	public long getTotalCommentsNoDrugs() {
		return totalCommentsNoDrugs;
	}

	public void setTotalCommentsNoDrugs(long totalCommentsNoDrugs) {
		this.totalCommentsNoDrugs = totalCommentsNoDrugs;
	}
	
}
