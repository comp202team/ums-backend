package com.comp202.ums.Dto.invitation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvitationRequestDto {
    private Long courseId;
    private List<String> studentEmail;
}
