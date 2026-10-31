package in.rajaram.userLogin.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    Long from;
    Long to;
    BigDecimal amount;
}
