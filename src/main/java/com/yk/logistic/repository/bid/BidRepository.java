package com.yk.logistic.repository.bid;

import com.yk.logistic.domain.bid.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid,Long> {
}
