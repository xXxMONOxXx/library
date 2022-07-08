package by.mishastoma.libraryweb.controller;

public final class AttributeName {

    private AttributeName(){

    }

    public static final String USER = "user";
    public static final String ID = "user_id"; //todo ???
    public static final String USER_ID = "user_id";
    public static final String LOGIN = "user_login";
    public static final String PASSWORD = "user_password";
    public static final String EMAIL = "user_email";
    public static final String FIRST_NAME = "user_first_name";
    public static final String LAST_NAME = "user_last_name";
    public static final String BIRTHDATE = "user_birthdate";
    public static final String COMMAND = "command";
    public static final String USERS_LIST = "users_list";
    public static final String PASSWORD_REPEAT = "password_confirm";
    public static final String ROLE = "user_role";
    public static final String GENRES_LIST = "genres_list";
    public static final String AUTHORS_LIST = "authors_list";
    public static final String BOOKS_LIST = "books_list";
    public static final String AUTHOR = "author";

    public static final String SIGN_IN_MESSAGE = "sign_in_msg";
    public static final String SIGN_UP_LOGIN_IS_INVALID = "sign_up_login_is_invalid";
    public static final String SIGN_UP_FIRSTNAME_IS_INVALID = "sign_up_firstname_is_invalid";
    public static final String SIGN_UP_LASTNAME_IS_INVALID = "sign_up_lastname_is_invalid";
    public static final String SIGN_UP_EMAIL_IS_INVALID = "sign_up_email_is_invalid";
    public static final String SIGN_UP_BIRTHDATE_IS_INVALID = "sign_up_birthdate_is_invalid";
    public static final String SIGN_UP_PASSWORD_IS_INVALID = "sign_up_password_is_invalid";
    public static final String SIGN_UP_PASSWORD_CONFIRM_IS_INVALID = "sign_up_password_repeat_is_invalid";

    public static final String ADD_GENRE_INVALID_NAME = "add_genre_invalid_or_exists";
    public static final String ADD_GENRE_SUCCESS = "added_genre_successfully";
    public static final String ADD_AUTHOR_SUCCESS = "added_author_successfully";
    public static final String ADD_BOOK_SUCCESS = "added_book_successfully";

    public static final String BALANCE_CHANGE_FAILED = "balance_changed_failed";
    public static final String BALANCE_CHANGE_SUCCESS = "balance_changed_successfully";

    public static final String GOT_BOOK_FAILED = "got_book_failed";
    public static final String GOT_BOOK_SUCCESS = "got_book_successfully";
    public static final String RETURNED_BOOK_SUCCESS = "returned_book_successfully";
    public static final String RETURN_BOOK_FAILED = "returned_book_failed";

    public static final String BLOCKED_OR_UNBLOCKED_USER_SUCCESS = "blocked_unblocked_success";
    public static final String BLOCKED_OR_UNBLOCKED_USER_FAILED = "blocked_unblocked_failed";
}
