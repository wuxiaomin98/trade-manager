/* ===========================================================
 * TradeManager : a application to trade strategies for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2011-2011, by Simon Allen and Contributors.
 *
 * Project Info:  org.trade
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Oracle, Inc.
 * in the United States and other countries.]
 *
 * (C) Copyright 2011-2011, by Simon Allen and Contributors.
 *
 * Original Author:  Simon Allen;
 * Contributor(s):   -;
 *
 * Changes
 * -------
 *
 */
package org.trade.persistent.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.trade.core.dao.Aspect;

/**
 */
@Entity
@Table(name = "portfolioaccount")
public class PortfolioAccount extends Aspect implements java.io.Serializable {

	private static final long serialVersionUID = 2273276207080568947L;

	private Portfolio portfolio;
	private Account account;

	public PortfolioAccount() {
	}

	public PortfolioAccount(Portfolio portfolio, Account account) {
		this.portfolio = portfolio;
		this.account = account;
	}

	/**
	 * Method getIdPortfolioAccount.
	 * 
	 * @return Integer
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idPortfolioAccount", unique = true, nullable = false)
	public Integer getIdPortfolioAccount() {
		return this.id;
	}

	/**
	 * Method setIdPortfolioAccount.
	 * 
	 * @param idPortfolioAccount
	 *            Integer
	 */
	public void setIdPortfolioAccount(Integer idPortfolioAccount) {
		this.id = idPortfolioAccount;
	}

	/**
	 * Method getPortfolio.
	 * 
	 * @return Portfolio
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "idPortfolio", nullable = false)
	public Portfolio getPortfolio() {
		return this.portfolio;
	}

	/**
	 * Method setPortfolio.
	 * 
	 * @param portfolio
	 *            Portfolio
	 */
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * Method getAccount.
	 * 
	 * @return Account
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "idAccount", nullable = false)
	public Account getAccount() {
		return this.account;
	}

	/**
	 * Method setAccount.
	 * 
	 * @param account
	 *            Account
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * Method getVersion.
	 * 
	 * @return Integer
	 */
	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * Method setVersion.
	 * 
	 * @param version
	 *            Integer
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Method equals.
	 * 
	 * @param objectToCompare
	 *            Object
	 * @return boolean
	 */
	public boolean equals(Object objectToCompare) {

		if (super.equals(objectToCompare))
			return true;

		if (!(objectToCompare instanceof PortfolioAccount))
			return false;

		PortfolioAccount theOtherOrder = (PortfolioAccount) objectToCompare;

		if (!theOtherOrder.getAccount().getAccountNumber().equals((this.getAccount().getAccountNumber()))) {
			return false;
		}

		if (!theOtherOrder.getPortfolio().getName().equals((this.getPortfolio().getName()))) {
			return false;
		}
		return true;
	}
}
