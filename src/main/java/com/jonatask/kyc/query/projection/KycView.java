package com.jonatask.kyc.query.projection;

import com.jonatask.kyc.command.aggregate.enums.EDocumentType;
import com.jonatask.kyc.command.aggregate.enums.EKycStatus;
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
    private EDocumentType documentType;
    private EKycStatus status;
}

