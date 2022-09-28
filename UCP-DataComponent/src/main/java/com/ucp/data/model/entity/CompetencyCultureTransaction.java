package com.ucp.data.model.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the competency_culture_transaction database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="competency_culture_transaction")
public class CompetencyCultureTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="fact_order")
	private int factOrder;

	@Column(name="fact_starring")
	private byte factStarring;

	//bi-directional many-to-one association to CollabParticipant
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="collab_participant_id")
	private CollabParticipants collabParticipant;

	//bi-directional many-to-one association to SortFactorMaster
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="fact_id")
	private SortFactorMaster sortFactorMaster;

	//bi-directional many-to-one association to SortTypeMaster
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="sort_type_id")
	private SortTypeMaster sortTypeMaster;


}