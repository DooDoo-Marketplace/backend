package space.rebot.micro.userservice.validator;

import space.rebot.micro.userservice.dto.user.UserDto;


public class UserDtoValidator extends Validator<UserDto>{
    private boolean validatePhone(String phone){
        return (new PhoneValidator()).validate(phone, true);
    }
    private boolean validateFirstName(String firstName){
        return true;
    }
    private boolean validateLastName(String lastName){
        return true;
    }
    private boolean validateEmail(String email){
        return true;
    }

    @Override
    public boolean isValid(UserDto value) {
        return  validatePhone(value.getPhone())
                && validateFirstName(value.getFirstName())
                && validateLastName(value.getLastName())
                && validateEmail(value.getEmail());

    }
}
