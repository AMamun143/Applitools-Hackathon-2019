package APP_Pages;

public class Login_Page {
    public String LP_image = "//img[@src='img/logo-big.png']";
    public String LP_header = "//*[@class='auth-header']";
    public String LP_username_logo = "//div[@class='pre-icon os-icon os-icon-user-male-circle']";
    public String LP_username_input_field = "//input[@id='username']";
    public String LP_username_label = LP_username_input_field+"/preceding-sibling::label";
    public String LP_password_logo = "//div[@class='pre-icon os-icon os-icon-fingerprint']";
    public String LP_password_input_field = "//input[@id='password']";
    public String LP_password_label = LP_password_input_field+"/preceding-sibling::label";
    public String LP_login_button = "//button[@id='log-in']";
    public String LP_remember_me_check_box = "//input[@class='form-check-input']";
    public String LP_remember_me_label = "//label[@class='form-check-label']";
    public String LP_twitter_image = "//img[@src='img/social-icons/twitter.png']";
    public String LP_facebook_image = "//img[@src='img/social-icons/facebook.png']";
    public String LP_linkedin_image = "//img[@src='img/social-icons/linkedin.png']";
    public String LP_alert_warning = "//div[@class='alert alert-warning']";
}
