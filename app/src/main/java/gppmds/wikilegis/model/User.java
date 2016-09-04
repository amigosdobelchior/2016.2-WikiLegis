package gppmds.wikilegis.model;

import android.util.Patterns;

import java.io.IOException;

import gppmds.wikilegis.controller.RegisterUser;
import gppmds.wikilegis.exception.UserException;

/**
 * Created by thiago on 9/1/16.
 */
public class User {
    private static final int MAX_LENGTH_FIRST_NAME = 30;
    private static final int MAX_LENGTH_LAST_NAME = 30;
    private static final int MAX_LENGTH_EMAIL = 150;
    private static final int MAX_LENGTH_PASSWORD = 10;
    private static final int MIN_LENGTH_PASSWORD = 6;

    public static final String FIRST_NAME_CANT_BE_EMPTY = "Inválido, o nome não pode ser vazio.";
    public static final String FIRST_NAME_CANT_BE_HIGHER_THAN_30 = "Inválido, o nome deve ter no máximo 30 caractéres";
    public static final String LAST_NAME_CANT_BE_EMPTY = "Inválido, o sobrenome não pode ser vazio.";
    public static final String LAST_NAME_CANT_BE_HIGHER_THAN_30 = "Inválido, o sobrenome deve ter no máximo 30 caractéres";
    public static final String EMAIL_CANT_BE_EMPTY_EMAIL = "Inválido, o email não pode ser vazio.";
    public static final String EMAIL_CANT_BE_HIGHER_THAN_150 = "Inválido, o email deve ter no máximo 30 caractéres";
    public static final String INVALID_EMAIL = "Ops, esse e-mail é inválido.";
    public static final String PASSWORD_CANT_BE_EMPTY = "Inválido, a senha não pode ser vazia.";
    public static final String PASSWORD_CANT_BE_LESS_THAN_6 = "Inválido, a senha deve conter no mínimo 6 caractéres.";
    public static final String PASSWORD_CANT_BE_HIGHER_THAN_10 = "Inválido, a senha deve ter no máximo 10 caractéres";
    public static final String PASSWORD_ISNT_EQUALS = "As senhas digitadas sao diferentes";
    public static final String EMAIL_IS_REPEATED = "Este email já está cadastrado";

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(String firstName, String lastName, String email, String password,
                String passwordConfimation) throws UserException, IOException {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password, passwordConfimation);
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) throws UserException {
        if(stringIsNull(firstName)){
            if(validateStringLengthLessThanMax(firstName,MAX_LENGTH_FIRST_NAME)){
                this.firstName = firstName;
            }else {
                throw  new UserException(FIRST_NAME_CANT_BE_HIGHER_THAN_30);
            }

        }else {
            throw  new UserException(FIRST_NAME_CANT_BE_EMPTY);
        }
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) throws UserException {
        if(stringIsNull(lastName)){
            if(validateStringLengthLessThanMax(lastName,MAX_LENGTH_LAST_NAME)){
                this.lastName = lastName;
            }else {
                throw  new UserException(LAST_NAME_CANT_BE_HIGHER_THAN_30);
            }

        }else {
            throw  new UserException(LAST_NAME_CANT_BE_EMPTY);
        }
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) throws UserException, IOException {
        if (stringIsNull(email)) {
            if(validateStringLengthLessThanMax(email, MAX_LENGTH_EMAIL)){
                CharSequence emailCharSequence = email;
                if(validateEmailFormat(email)) {
                    if(!RegisterUser.emailIsRepeated(email)) {
                        this.email=email;
                    }else {
                        throw new UserException(EMAIL_IS_REPEATED);
                    }
                }else{
                    throw new UserException(INVALID_EMAIL);
                }
            }else{
                throw  new UserException(EMAIL_CANT_BE_HIGHER_THAN_150);
            }
        }else{
            throw  new UserException(EMAIL_CANT_BE_EMPTY_EMAIL);
        }
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password, String passwordConfirmation) throws UserException {
        if(stringIsNull(password)){
            if(validateStringLengthLessThanMax(password,MAX_LENGTH_PASSWORD)){
                if(validateStringLengthMoreThanMin(password,MIN_LENGTH_PASSWORD))
                    if(passwordIsEqual(password, passwordConfirmation)){
                        this.password = password;
                    }else {
                        throw new UserException(PASSWORD_ISNT_EQUALS);
                    }
                else {
                    throw  new UserException(PASSWORD_CANT_BE_LESS_THAN_6);
                }
            }else {
                throw  new UserException(PASSWORD_CANT_BE_HIGHER_THAN_10);
            }

        }else {
            throw  new UserException(PASSWORD_CANT_BE_EMPTY);
        }
    }

    //Validation methods

    private boolean stringIsNull(final String string){
        if(string == null || string.isEmpty()) {
            return false;
        }else {
            return  true;
        }
    }


    private boolean validateStringLengthLessThanMax(final String string, final int MAX){
        if(string.length() > MAX) {
            return false;
        }else {
            return  true;
        }
    }

    private boolean validateStringLengthMoreThanMin(final String string, final int MIN){
        if(string.length() < MIN) {
            return false;
        }else {
            return  true;
        }
    }


    private boolean passwordIsEqual(final String password, final String passwordConfimation){
        if(password.equals(passwordConfimation)) {
            return true;
        }else {
            return  false;
        }
    }

    //Validar tipo de email
    private boolean validateEmailFormat(final String string){
        String emailCharSequence = string;
        if(Patterns.EMAIL_ADDRESS.matcher(emailCharSequence).matches()) {
            return true;
        }else {
            return  false;
        }
    }
}