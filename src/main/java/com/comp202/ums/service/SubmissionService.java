package com.comp202.ums.service;

import com.comp202.ums.Dto.submission.SubmissionCreateDto;
import com.comp202.ums.Dto.submission.SubmissionDto;
import com.comp202.ums.Dto.submission.SubmissionGradingDto;
import com.comp202.ums.Entity.Assignment;
import com.comp202.ums.Entity.Submission;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.SubmissionMapper;
import com.comp202.ums.Repository.AssignmentRepository;
import com.comp202.ums.Repository.SubmissionRepository;
import com.comp202.ums.Repository.UserRepository;
import com.comp202.ums.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    public SubmissionService(SubmissionRepository submissionRepository,UserRepository userRepository,AssignmentRepository assignmentRepository){
        this.userRepository=userRepository;
        this.assignmentRepository=assignmentRepository;
        this.submissionRepository=submissionRepository;
    }

    public List<SubmissionDto> getAllSubmissionsFromAssignmentId(Long id){
        return SubmissionMapper.INSTANCE.toSubmissionDtoList(submissionRepository.getSubmissionsByAssignment_Id(id));
    }
    public Submission getSubmissionEntity(Long id){
        return submissionRepository.findById(id).orElseThrow(()->new NotFoundException("Submission","Submission not found"));
    }
    public SubmissionDto createSubmission(String email, Long assignmentId, SubmissionCreateDto submissionCreateDto) {
        User student = userRepository.findByEmail(email);
        if (student==null)
            throw new NotFoundException("User","Student with this email not found: "+email);
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment","Invalid assignment id: " + assignmentId));
        Submission submission= new Submission();
        submission.setTitle(submissionCreateDto.getTitle());
        submission.setContent(submissionCreateDto.getContent());
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setSubmissionTime(LocalDateTime.now());
        return SubmissionMapper.INSTANCE.toDto(submissionRepository.save(submission));
    }
    public SubmissionDto updateGrading(Long id,SubmissionGradingDto submissionGradingDto){
        Submission submission= submissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Submission","Invalid Submission id: " + id));
        submission.setGrade(submissionGradingDto.getGrade());
        return SubmissionMapper.INSTANCE.toDto(submissionRepository.save(submission));
    }


    public SubmissionDto updateSubmission(Long submissionId, SubmissionCreateDto submission) {
        Submission existingSubmission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException("Submission","Invalid Submission id: " + submissionId));

        existingSubmission.setTitle(submission.getTitle());
        existingSubmission.setContent(submission.getContent());

        return SubmissionMapper.INSTANCE.toDto(submissionRepository.save(existingSubmission));
    }

    public void deleteSubmission(Long submissionId) {
        submissionRepository.deleteById(submissionId);
    }
}