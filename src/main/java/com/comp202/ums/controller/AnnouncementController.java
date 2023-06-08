package com.comp202.ums.controller;

import com.comp202.ums.Dto.announcement.AnnouncementCreateDto;
import com.comp202.ums.Dto.announcement.AnnouncementDto;
import com.comp202.ums.Dto.assignment.AssignmentCreateDto;
import com.comp202.ums.Dto.assignment.AssignmentDto;
import com.comp202.ums.Entity.Announcement;
import com.comp202.ums.Entity.Assignment;
import com.comp202.ums.exception.NotFoundException;
import com.comp202.ums.service.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService){
        this.announcementService = announcementService;
    }
    @GetMapping()
    public ResponseEntity<List<AnnouncementDto>> getAll(){
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }
    @GetMapping("/course/{id}")
    public ResponseEntity<List<AnnouncementDto>> getAllFromCourse(@PathVariable Long id){
        return ResponseEntity.ok(announcementService.getAllAnnouncementsFromCourse(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(announcementService.getAnnouncementById(id));
    }
    @GetMapping("student/{studentId}")
    public ResponseEntity<List<AnnouncementDto>> getByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(announcementService.getAnnouncementsByStudentId(studentId));
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<AnnouncementDto> createAnnouncement( @RequestBody AnnouncementCreateDto announcementCreateDto){
        return ResponseEntity.ok(announcementService.createAnnouncement(announcementCreateDto));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<AnnouncementDto> updateAnnouncement(@PathVariable Long id, @RequestBody AnnouncementCreateDto announcementDto) {
        return ResponseEntity.ok(announcementService.updateAnnouncement(announcementDto, id));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<AnnouncementDto> deleteAnnouncementById(@PathVariable Long id) {
        Announcement announcement = announcementService.getAnnouncementEntityById(id);
        if (announcement!=null) {
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
