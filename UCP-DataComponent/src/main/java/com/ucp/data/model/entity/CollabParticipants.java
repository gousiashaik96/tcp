package com.ucp.data.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the `collab_ participants` database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`collab_participants`")
public class CollabParticipants implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "access_level")
	private String accessLevel;

	private String consent;

	@Column(name = "email_status")
	private String emailStatus;

	@Column(name = "participant_id")
	private String participantId;

	// bi-directional many-to-one association to SpCollabMain
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "collab_id")
	private SpCollabMain spCollabMain;

	@OneToMany(mappedBy = "collabParticipant", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<CollabParticipantStatus> collabParticipantStatuses;

	// bi-directional many-to-one association to CompetencyCultureTransaction
	@OneToMany(mappedBy = "collabParticipant", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<CompetencyCultureTransaction> competencyCultureTransactions;

	// bi-directional many-to-one association to JobAnalysisTransaction
	@OneToMany(mappedBy = "collabParticipant", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<JobAnalysisTransaction> jobAnalysisTransactions;
}