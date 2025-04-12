package project.Licenta.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceResponse {
    private Long provider_id;
    private String username;
    private String status;
    private Double amount;
    private Date due_date;
}
