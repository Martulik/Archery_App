package spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.ProfileStatus;
import spring.repositories.ProfileStatusRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileStatusServiceImpl implements ProfileStatusService{


    private final ProfileStatusRepository profileStatusRepository;

    @Override
    public List<ProfileStatus> getStatusList() {
        return profileStatusRepository.findAll();
    }

    @Override
    public void addStatus(String status) {
        ProfileStatus profileStatus = new ProfileStatus(status);
        profileStatusRepository.save(profileStatus);
    }

    @Override
    public void deleteStatus(String status) {
        Optional<ProfileStatus> optionalStatus = profileStatusRepository.findByStatus(status);
        if (optionalStatus.isPresent()) {
            profileStatusRepository.deleteById(status);
        }
    }

    @Override
    public ProfileStatus findByProfileStatus(String status) {
        Optional<ProfileStatus> optionalProfileStatus = profileStatusRepository.findByStatus(status);
        if (optionalProfileStatus.isPresent()) {
            return optionalProfileStatus.get();
        }
        throw new NoSuchElementException("There aren't such profile status");
    }

    @Override
    public ProfileStatus findByProfileStatus(ProfileStatus status) {
        String st = status.getStatus();
        Optional<ProfileStatus> optionalProfileStatus = profileStatusRepository.findByStatus(st);
        if (optionalProfileStatus.isPresent()) {
            return optionalProfileStatus.get();
        }
        throw new NoSuchElementException("There aren't such profile status");
    }
}
