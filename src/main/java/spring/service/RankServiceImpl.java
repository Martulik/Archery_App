package spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Rank;
import spring.repositories.RankRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService
{
    private final RankRepository rankRepository;

    public List<Rank> findAll()
    {
        return rankRepository.findAll();
    }

   /* public void changeColor(String color, String name)
    {
        rankRepository.changeColor(color, name);
    }*/
}
