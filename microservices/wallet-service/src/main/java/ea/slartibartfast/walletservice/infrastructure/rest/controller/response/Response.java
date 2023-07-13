package ea.slartibartfast.walletservice.infrastructure.rest.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private String status;
    private String errorMessage;
    private long systemTime;
}
