package space.rebot.micro.userservice.formatter;

public class PhoneFormatter {
    public String format(String phone) {
        if (phone.charAt(0) == '8' || phone.charAt(0) == '7') {
            phone = "+7" + phone.substring(1);
        } else if (phone.charAt(0) == '+' && phone.charAt(1) == '8') {
            phone = "+7" + phone.substring(2);
        }
        return phone;
    }
}
