package com.tarento.sec.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tarento.sec.dto.AssignmentDto;
import com.tarento.sec.exception.UserNotFound;
import com.tarento.sec.model.Assignment;
import com.tarento.sec.model.Grievance;
import com.tarento.sec.model.User;
import com.tarento.sec.repo.AssignmentRepo;
import com.tarento.sec.repo.GrievanceRepo;
import com.tarento.sec.repo.UserRepo;

@Service
public class AssignmentService {
    
    private final AssignmentRepo assignmentRepo;
    private final UserRepo userRepo;
    private final GrievanceRepo grievanceRepo;

    public AssignmentService(AssignmentRepo assignmentRepo, UserRepo userRepo, GrievanceRepo grievanceRepo) {
        this.assignmentRepo = assignmentRepo;
        this.userRepo = userRepo;
        this.grievanceRepo = grievanceRepo;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepo.findAll();
    }

    public Assignment getAssignmentById(Long id) {
        return assignmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public Assignment createAssignment(AssignmentDto assignmentDto) {
        
        User supervisor = userRepo.findById(assignmentDto.getSupervisorId()).orElseThrow(() -> new RuntimeException("Supervisor not found"));
        User assignee = userRepo.findById(assignmentDto.getAssigneeId()).orElseThrow(() -> new RuntimeException("Assignee not found"));
        Grievance grievance = grievanceRepo.findById(assignmentDto.getGrievanceId()).orElseThrow(() -> new RuntimeException("Grievance not found"));

        Assignment assignment = new Assignment(grievance, supervisor, assignee);
        return assignmentRepo.save(assignment);
    }

    public Assignment updateAssignment(Long id, AssignmentDto assignmentDto) {
        Assignment assignment = assignmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignment.setSupervisor(userRepo.findById(assignmentDto.getSupervisorId()).orElseThrow(() -> new RuntimeException("Supervisor not found")));
        assignment.setAssignee(userRepo.findById(assignmentDto.getAssigneeId()).orElseThrow(() -> new RuntimeException("Assignee not found")));
        return assignmentRepo.save(assignment);
    }
    
    public List<Assignment> getAssignmentsBySupervisor(Long supervisorId) {
        try {
            User supervisor = userRepo.findById(supervisorId).orElseThrow(() -> new UserNotFound("Supervisor not found"));
            return assignmentRepo.findBySupervisor(supervisor);
        } catch (UserNotFound e) {
            return null;
        }
    }

    public List<Assignment> getAssignmentsByAssignee(Long assigneeId) {
        try {
            User assignee = userRepo.findById(assigneeId).orElseThrow(() -> new UserNotFound("Assignee not found"));
            return assignmentRepo.findByAssignee(assignee);
        } catch (UserNotFound e) {
            return null;
        }
    }

    public ResponseEntity<String> deleteAssignment(Long id) {
        try {
            Assignment assignment = assignmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
            assignmentRepo.delete(assignment);
            return ResponseEntity.ok("Assignment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
