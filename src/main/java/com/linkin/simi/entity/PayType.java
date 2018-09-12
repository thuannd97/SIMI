package com.linkin.simi.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pay_type database table.
 * 
 */
@Entity
@Table(name="pay_type")
public class PayType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "type")
	private double type;

	//bi-directional many-to-one association to Installment
	@OneToMany(mappedBy="payType")
	private List<Installment> installments;

	public PayType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getType() {
		return this.type;
	}

	public void setType(double type) {
		this.type = type;
	}

	public List<Installment> getInstallments() {
		return this.installments;
	}

	public void setInstallments(List<Installment> installments) {
		this.installments = installments;
	}

	public Installment addInstallment(Installment installment) {
		getInstallments().add(installment);
		installment.setPayType(this);

		return installment;
	}

	public Installment removeInstallment(Installment installment) {
		getInstallments().remove(installment);
		installment.setPayType(null);

		return installment;
	}

}