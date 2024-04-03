package security.service.model.validation;

import security.persistence.entity.UserEntity;
import security.service.model.dto.ResponseDTO;

import java.util.Objects;

public class UserValidation {

    public ResponseDTO validate(UserEntity user) {
        ResponseDTO response = new ResponseDTO();
        response.setNumOfErrors(0);

        if (Objects.isNull(user.getFirstName()) ||
            user.getFirstName().length() < 3 ||
            user.getFirstName().length() > 15) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("The name cannot be null and must be between 3 and 15 characters.");
        }

        if (Objects.isNull(user.getLastName()) ||
            user.getLastName().length() < 3 ||
            user.getLastName().length() > 30) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("The last name cannot be null and must be between 3 and 30 characters.");
        }

        if (Objects.isNull(user.getEmail()) ||
            !user.getEmail().matches("^[\\w~.]+@([\\w~]+\\.)+[\\w~]{2,4}$")) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("The email is not valid.");
        }

        if (Objects.isNull(user.getPassword()) ||
            !user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("The password must be between 8 and 16 characters, " +
                    "at least one number, one lowercase letter and one uppercase letter.");
        }

        return response;
    }
}
