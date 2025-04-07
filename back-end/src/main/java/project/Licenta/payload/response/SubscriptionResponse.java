package project.Licenta.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubscriptionResponse {
    private Long provider_id;
    private String username;
    private String status;
    private Long index_old;
    private Long index_read;
}
