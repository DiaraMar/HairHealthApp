package com.kamikarow.hairCareProject.exposition.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteAccountCustomizationRequest {

    private boolean newsletter;
    private boolean sms;
    private String username;


}
