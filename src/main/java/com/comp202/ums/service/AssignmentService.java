package com.comp202.ums.service;

import com.comp202.ums.Dto.assignment.AssignmentCreateDto;
import com.comp202.ums.Dto.assignment.AssignmentDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Map.AssignmentMapper;
import com.comp202.ums.Repository.AssignmentRepository;
import com.comp202.ums.Repository.CourseRepository;
import org.springframework.stereotype.Service;
import com.comp202.ums.Entity.Assignment;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    public AssignmentService(AssignmentRepository assignmentRepository,CourseRepository courseRepository){
        this.assignmentRepository=assignmentRepository;
        this.courseRepository=courseRepository;
    }
    public List<AssignmentDto> getAllFromCourse(String code){
        return AssignmentMapper.INSTANCE.toAssignmentListDto(assignmentRepository.getAssignmentsByCourse_CourseCode(code));
    }

    public AssignmentDto getByAssignmentId(Long id){
        return AssignmentMapper.INSTANCE.toDto(assignmentRepository.getAssignmentsById(id));
    }
    public Assignment getAssignmentEntity(Long id){
        return assignmentRepository.getAssignmentsById(id);
    }

    public AssignmentDto createAssignment(String code, AssignmentCreateDto assignmentCreateDto) {
        Course course = courseRepository.getByCourseCode(code);

        if (course==null)
            throw new IllegalArgumentException("Invalid course code: " + code);

        return AssignmentMapper.INSTANCE.toDto(createFromDto(course.getId(),assignmentCreateDto));
    }

    public AssignmentDto updateAssignmentNameAndDeadline(Long assignmentId, AssignmentDto assignment) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assignment id: " + assignmentId));

        existingAssignment.setName(assignment.getName());
        existingAssignment.setDeadline(LocalDate.parse(assignment.getDeadline()));

        return AssignmentMapper.INSTANCE.toDto(assignmentRepository.save(existingAssignment));
    }

    public void deleteAssignment(Long assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }
    public Assignment createFromDto(Long id,AssignmentCreateDto assignmentDto){
        Assignment assignment = new Assignment();
        assignment.setName(assignmentDto.getName());
        assignment.setDescription(assignmentDto.getDescription());
        assignment.setDeadline(LocalDate.parse(assignmentDto.getDeadline()));
        assignment.setCourse(courseRepository.getById(id));
        return assignmentRepository.save(assignment);
    }
}
