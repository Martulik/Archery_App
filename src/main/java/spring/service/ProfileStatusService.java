package spring.service;

import spring.entity.ProfileStatus;

import java.util.List;

public interface ProfileStatusService {
    List<ProfileStatus> getStatusList();
    void addStatus(String status);
    void deleteStatus(String status);
    ProfileStatus findByProfileStatus(String status);
    ProfileStatus findByProfileStatus(ProfileStatus status);
}
