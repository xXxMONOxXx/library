package by.mishastoma.libraryweb.controller;

public final class AttributeName {

    private AttributeName(){

    }

    public static final String USER = "user";
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
    public static final String AUTHOR_ID = "author_id";
    public static final String GENRE = "genre";
    public static final String BOOK = "book";
    public static final String BOOK_ID = "book_id";
    public static final String ACTUAL_QUANTITY = "actual_quantity";
    public static final String CURRENT_PAGE = "current_page";
    public static final String NUMBER_OF_PAGES = "number_of_pages";
    public static final String SEARCH_INPUT = "search_input";

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

    public static final String FAILED_TO_GET_AUTHOR = "failed_to_get_author";

    public static final String UPDATE_AUTHOR_SUCCESS = "updated_author_successfully";
    public static final String UPDATE_AUTHOR_FAILED = "author_update_failed";

    public static final String UPDATE_PASSWORD_SUCCESS = "updated_users_password_successfully";
    public static final String UPDATE_PASSWORD_FAILED = "update_users_password_failed";

    public static final String UPDATE_GENRE_FAILED = "genre_update_failed";
    public static final String UPDATE_GENRE_SUCCESS = "updated_genre_successfully";

    public static final String UPDATE_BOOK_SUCCESS = "update_book_success";

    public static final String FAILED_TO_GET_GENRE = "failed_to_get_genre";

    public static final String FAILED_TO_GET_BOOK = "failed_to_get_book";

    public static final String FAILED_TO_DELETE_AUTHOR = "failed_to_delete_author";
    public static final String DELETE_AUTHOR_SUCCESS = "delete_author_success";

    public static final String FAILED_TO_DELETE_GENRE = "failed_to_delete_genre";
    public static final String DELETE_GENRE_SUCCESS = "delete_genre_success";

    public static final String FAILED_TO_DELETE_BOOK = "failed_to_delete_book";
    public static final String DELETE_BOOK_SUCCESS = "delete_book_success";
}
