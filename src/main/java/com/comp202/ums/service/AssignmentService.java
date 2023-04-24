package com.comp202.ums.service;

import com.comp202.ums.Dto.assignment.AssignmentDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Map.AssignmentMapper;
import com.comp202.ums.Repository.AssignmentRepository;
import com.comp202.ums.Repository.CourseRepository;
import org.springframework.stereotype.Service;
import com.comp202.ums.Entity.Assignment;

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

    public AssignmentDto createAssignment(String code, Assignment assignment) {
        Course course = courseRepository.getByCourseCode(code);

        if (course==null)
            throw new IllegalArgumentException("Invalid course code: " + code);

        assignment.setCourse(course);
        return AssignmentMapper.INSTANCE.toDto(assignmentRepository.save(assignment));
    }

    public AssignmentDto updateAssignmentNameAndDeadline(Long assignmentId, Assignment assignment) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assignment id: " + assignmentId));

        existingAssignment.setName(assignment.getName());
        existingAssignment.setDeadline(assignment.getDeadline());

        return AssignmentMapper.INSTANCE.toDto(assignmentRepository.save(existingAssignment));
    }

    public void deleteAssignment(Long assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }
}
