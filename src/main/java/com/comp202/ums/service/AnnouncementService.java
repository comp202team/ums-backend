package com.comp202.ums.service;

import com.comp202.ums.Dto.announcement.AnnouncementCreateDto;
import com.comp202.ums.Dto.announcement.AnnouncementDto;
import com.comp202.ums.Entity.Announcement;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Map.AnnouncementMapper;
import com.comp202.ums.Repository.AnnouncementRepository;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.EnrollmentRepository;
import com.comp202.ums.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;



    public AnnouncementService(AnnouncementRepository announcementRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.announcementRepository = announcementRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }


    public List<AnnouncementDto> getAllAnnouncements(){
        return AnnouncementMapper.INSTANCE.toDtoList(announcementRepository.findAll());
    }
    public List<AnnouncementDto> getAllAnnouncementsFromCourse(Long id){
        return AnnouncementMapper.INSTANCE.toDtoList(announcementRepository.getAnnouncementsByCourse_Id(id));
    }

    public AnnouncementDto getAnnouncementById(Long id){

        return AnnouncementMapper.INSTANCE.toDto(announcementRepository.findById(id).orElseThrow(() -> new NotFoundException("Announcement", "no announcement found with this id")));
    }
    public AnnouncementDto createAnnouncement(AnnouncementCreateDto announcementCreateDto){
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setTitle(announcementCreateDto.getTitle());
        newAnnouncement.setDescription(announcementCreateDto.getDescription());
        newAnnouncement.setDate(LocalDate.now());
        newAnnouncement.setCourse(courseRepository.getById(announcementCreateDto.getCourseId()));
        return AnnouncementMapper.INSTANCE.toDto(announcementRepository.save(newAnnouncement));
    }
    public AnnouncementDto updateAnnouncement(AnnouncementCreateDto announcementCreateDto,Long id){
        Announcement newAnnouncement = announcementRepository.findById(id).orElseThrow(() -> new NotFoundException("Announcement", "no announcement found with this id"));
        newAnnouncement.setTitle(announcementCreateDto.getTitle());
        newAnnouncement.setDescription(announcementCreateDto.getDescription());
        newAnnouncement.setDate(LocalDate.now());
        newAnnouncement.setCourse(courseRepository.getById(announcementCreateDto.getCourseId()));
        return AnnouncementMapper.INSTANCE.toDto(announcementRepository.save(newAnnouncement));
    }

    public void deleteAnnouncement(Long id){
        Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new NotFoundException("Announcement", "no announcement found with this id"));
        announcementRepository.deleteById(id);
    }
    public List<AnnouncementDto> getAnnouncementsByStudentId(Long id){
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentsByStudentId(id);
        List<Course> courses = new java.util.ArrayList<>(Collections.emptyList());
        List<Announcement> announcements = new java.util.ArrayList<>(Collections.emptyList());

        for (Enrollment enrollment: enrollments) {
            courses.add(enrollment.getCourse());
        }
        for (Course course: courses) {
            announcements.addAll(course.getAnnouncements());
        }

        return AnnouncementMapper.INSTANCE.toDtoList(announcements);
    }


    public Announcement getAnnouncementEntityById(Long id) {
        return announcementRepository.findById(id).orElseThrow(() -> new NotFoundException("Announcement", "no announcement found with this id"));
    }
}
