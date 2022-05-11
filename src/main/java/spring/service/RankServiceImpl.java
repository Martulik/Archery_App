package spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import spring.entity.Rank;
import spring.repositories.RankRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService
{
    private final RankRepository rankRepository;

    public List<Rank> findAll()
    {
        return rankRepository.findAll();
    }

    @Override
    public Rank findByRankName(String name) {
        Optional<Rank> optionalRank = rankRepository.findByRank_name(name);
        if (optionalRank.isPresent()) {
            return optionalRank.get();
        }
        throw new NoSuchElementException("rank not found");
    }

   /* public void changeColor(String color, String name)
    {
        rankRepository.changeColor(color, name);
    }*/
}
