package web_driver_configuration;

public class ChromeUserDataPath {

    public static String getChromeUserDataPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String userProfilePath = System.getProperty("user.home");

        if (os.contains("win")) {
            return userProfilePath + "\\AppData\\Local\\Google\\Chrome\\User Data";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return userProfilePath + "/.config/google-chrome";
        } else {
            // Unsupported operating system
            return "";
        }
    }
}
