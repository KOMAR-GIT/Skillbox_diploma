package main.dto.errorMessages;

public class ErrorsForProfile {

    public final static String email = "Этот e-mail уже зарегистрирован";
    public final static String name = "Имя указано неверно";
    public final static String password = "Пароль короче 6-ти символов";
    public final static String captcha = "Код с картинки введён неверно";
    public final static String photo = "Фото слишком большое, либо неверного формата";
    public final static String code = "Ссылка для восстановления пароля устарела.\n" +
            " <a href=\n" +
            " \\\"/auth/restore\\\">Запросить ссылку снова</a>";

}
