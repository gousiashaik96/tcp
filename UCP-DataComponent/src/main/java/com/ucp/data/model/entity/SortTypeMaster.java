package com.ucp.data.model.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The persistent class for the sort_type_master database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sort_type_master")
public class SortTypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "sort_type")
	private String sortType;

	// bi-directional many-to-one association to CompetencyCultureTransaction
	@OneToMany(mappedBy = "sortTypeMaster", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<CompetencyCultureTransaction> competencyCultureTransactions;

}