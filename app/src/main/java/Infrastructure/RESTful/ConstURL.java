package Infrastructure.RESTful;

/**
 * Created by MARKAN on 13.05.2017.
 */
public final class ConstURL {
    private static final String mainUrl = "http://easyukr.azurewebsites.net/";
    private static final String registerUrl = "api/Account/Register";
    private static final String loginUrl = "Token";
    private static final String uploadUrl = "api/Upload";
    private static final String logOutUrl = "api/Account/LogOut";
    private static final String userUrl = "api/UserManaging/UserInfo";
    private static final String avatarUrl = "api/UserManaging/UserAvatar";

    private static final String topicUrl = "api/Transmit/Topics";
    private static final String wordUrl = "api/Transmit/Words";
    private static final String grammarUrl = "api/Transmit/Grammars";
    private static final String grammarTasksUrl = "api/Transmit/GrammarTasks";
    private static final String recommCatUrl = "api/Transmit/RecommendationCategories";
    private static final String recommUrl = "api/Transmit/Recommendations";
    private static final String dialogueUrl = "api/Transmit/Dialogues";
    private static final String fileUrl = "api/Transmit/GetFile";


    private static final String updateUserUrl = "api/UserManaging/UpdateUserInfo";

    public static String getUpdateUserInfo() {
        return mainUrl + updateUserUrl;
    }

    public static String getRegisterUrl() {
        return mainUrl + registerUrl;
    }

    public static String getLoginUrl() {
        return mainUrl + loginUrl;
    }

    public static String getUserUrl() {
        return mainUrl + userUrl;
    }

    public static String getUploadUrl() {
        return mainUrl + uploadUrl;
    }

    public static String getTopicUrl() {
        return mainUrl + topicUrl;
    }

    public static String getWordUrl() {
        return mainUrl + wordUrl;
    }

    public static String getGrammarUrl() {
        return mainUrl + grammarUrl;
    }

    public static String getGrammarTasksUrl() {
        return mainUrl + grammarTasksUrl;
    }

    public static String getRecommendationCategoryUrl() {
        return mainUrl + recommCatUrl;
    }

    public static String getRecommendationUrl() {
        return mainUrl + recommUrl;
    }

    public static String getDialogueUrl() {
        return mainUrl + dialogueUrl;
    }

    public static String getFileUrl() {
        return mainUrl + fileUrl;
    }

    public static String getAvatarUrl() {
        return mainUrl + avatarUrl;
    }

    public static String getLogOutUrl() {
        return mainUrl + logOutUrl;
    }
}
