package com.comp202.ums.service;

import com.comp202.ums.Entity.Announcement;
import com.comp202.ums.Repository.AnnouncementRepository;
import com.comp202.ums.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;


    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }


    public List<Announcement> getAllAnnouncements(){
        return announcementRepository.findAll();
    }

    public Optional<Announcement> getAnnouncementById(Long id){
        Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new NotFoundException("Announcement", "no announcement found with this id"));
        return announcementRepository.findById(id);
    }

    public Announcement updateAnnouncement(Announcement announcement){
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setId(announcement.getId());
        newAnnouncement.setTitle(announcement.getTitle());
        newAnnouncement.setDate(announcement.getDate());
        newAnnouncement.setDescription(announcement.getDescription());
        newAnnouncement.setCourse(announcement.getCourse());
        return announcementRepository.save(newAnnouncement);
    }
    public void deleteAnnouncement(Long id){
        Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new NotFoundException("Announcement", "no announcement found with this id"));
        announcementRepository.deleteById(id);
    }


}
