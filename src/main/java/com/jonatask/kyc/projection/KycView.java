package com.jonatask.kyc.projection;

import com.jonatask.kyc.domain.enums.EKycStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("kyc_view")
@AllArgsConstructor
@Data
public class KycView {

    @Id
    private String kycId;
    private String customerId;
    private EKycStatus status;
}

