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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sp_collab_main")
public class SpCollabMain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "collab_id")
	private int collabId;

	@Column(name = "profile_manager_id")
	private String profileManagerId;

	@Column(name = "sp_id")
	private String spId;

	private String status;
	
	@Column(name="sp_title")
	private String spTitle;

	// bi-directional many-to-one association to Collab_participant
	@OneToMany(mappedBy = "spCollabMain", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private List<CollabParticipants> collabParticipants;

	// bi-directional many-to-one association to SpConfigOptionsValue
	@OneToMany(mappedBy = "spCollabMain", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private List<SpConfigOptionsValue> spConfigOptionsValues;
}