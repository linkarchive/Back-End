package project.linkarchive.backend.advice.success;

import lombok.Getter;

@Getter
public class SuccessResponse {

    private int status;
    private String code;
    private String message;

    public SuccessResponse(SuccessCodeConst successCodeConst) {
        this.status = successCodeConst.getStatus();
        this.code = successCodeConst.getCode();
        this.message = successCodeConst.getMessage();
    }

}