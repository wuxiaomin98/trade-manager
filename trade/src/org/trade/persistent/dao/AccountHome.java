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

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.trade.core.dao.EntityManagerHelper;

/**
 */
@Stateless
public class AccountHome {
	private EntityManager entityManager = null;

	public AccountHome() {

	}

	/**
	 * Method findById.
	 * 
	 * @param id
	 *            Integer
	 * @return Account
	 */
	public Account findById(Integer id) {

		try {
			entityManager = EntityManagerHelper.getEntityManager();
			entityManager.getTransaction().begin();
			Account instance = entityManager.find(Account.class, id);
			entityManager.getTransaction().commit();
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.rollback();
			throw re;
		} finally {
			EntityManagerHelper.close();
		}
	}

	/**
	 * Method findByAccountNumber.
	 * 
	 * @param accountNumber
	 *            String
	 * @return Account
	 */
	public Account findByAccountNumber(String accountNumber) {

		try {
			entityManager = EntityManagerHelper.getEntityManager();
			entityManager.getTransaction().begin();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Account> query = builder.createQuery(Account.class);
			Root<Account> from = query.from(Account.class);
			query.select(from);
			query.where(builder.equal(from.get("accountNumber"), accountNumber));
			List<Account> items = entityManager.createQuery(query)
					.getResultList();
			for (Account account : items) {
				account.getPortfolioAccounts().size();
			}
			entityManager.getTransaction().commit();
			if (items.size() > 0) {
				return items.get(0);
			}
			return null;

		} catch (RuntimeException re) {
			EntityManagerHelper.rollback();
			throw re;
		} finally {
			EntityManagerHelper.close();
		}
	}

	/**
	 * Method resetDefaultAccount.
	 * 
	 * @param defaultAccount
	 *            Account
	 * 
	 * @param portfolio
	 *            Portfolio
	 */
	public void resetDefaultAccount(Portfolio portfolio, Account defaultAccount) {

		try {
			entityManager = EntityManagerHelper.getEntityManager();
			entityManager.getTransaction().begin();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<PortfolioAccount> query = builder
					.createQuery(PortfolioAccount.class);
			Root<PortfolioAccount> from = query.from(PortfolioAccount.class);
			query.select(from);
			List<Predicate> predicates = new ArrayList<Predicate>();
			Join<PortfolioAccount, Portfolio> portfolios = from
					.join("portfolio");
			Predicate predicate = builder.equal(portfolios.get("idPortfolio"),
					portfolio.getIdPortfolio());
			predicates.add(predicate);
			query.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<PortfolioAccount> typedQuery = entityManager
					.createQuery(query);
			List<PortfolioAccount> items = typedQuery.getResultList();

			for (PortfolioAccount item : items) {
				if (defaultAccount.getIdAccount().equals(
								item.getAccount().getIdAccount())) {
					item.getAccount().setIsDefault(true);
					
				}else{
					item.getAccount().setIsDefault(false);
				}
				entityManager.persist(item);
			}
			entityManager.getTransaction().commit();
		} catch (RuntimeException re) {
			EntityManagerHelper.rollback();
			throw re;
		} finally {
			EntityManagerHelper.close();
		}
	}
}
