package com.ucp.data.model.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The persistent class for the sort_factor_master database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sort_factor_master")
public class SortFactorMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fact_id")
	private String factId;

	@Column(name = "fact_name")
	private String factName;

	// bi-directional many-to-one association to CompetencyCultureTransaction
	@OneToMany(mappedBy = "sortFactorMaster", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<CompetencyCultureTransaction> competencyCultureTransactions;

	// bi-directional many-to-one association to JobAnalysisTransaction
	@OneToMany(mappedBy = "sortFactorMaster", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<JobAnalysisTransaction> jobAnalysisTransactions;

}