package swt6.schwarz.dao.orm.repositories.impl;

import swt6.schwarz.dao.orm.domain.Bid;
import swt6.schwarz.dao.orm.repositories.BaseRepository;

public class BidRepository extends BaseRepository<Bid> {

    @Override
    public Bid update(Bid entity) {
        return getEntityHandler().updateEntity(entity.getId(), entity, Bid.class);
    }
}
