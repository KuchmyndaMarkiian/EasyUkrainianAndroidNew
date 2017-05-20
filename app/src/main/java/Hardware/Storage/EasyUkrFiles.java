package Hardware.Storage;


import java.io.File;

/**
 * Created by Markan on 08.02.2017.
 */
public class EasyUkrFiles {
    public static String fileUser = "user.kmt";
    public static String fileWords = "words.kmt";
    public static String fileTopics = "topic.kmt";
    public static String fileGrammar = "grammar.kmt";
    public static String fileGrammarTask = "grammarTask.kmt";
    public static String fileRecomendation = "recomendation.kmt";
    public static String fileRecomendationList = "recomendationList.kmt";
    public static String fileDialogue = "dialogue.kmt";
    public static File defaultpath;


    public static String setFile(Type mainType) {
        switch (mainType) {
            case WORD:
                return fileWords;
            case TOPIC:
                return fileTopics;
            case GRAMMAR:
                return fileGrammar;
            case GRAMMARTASK:
                return fileGrammarTask;
            case RECOMENDATION:
                return fileRecomendation;
            case RECOMENDATIONLIST:
                return fileRecomendationList;
            case USER:
                return fileUser;
            case DIALOGUE:
                return fileDialogue;
        }
        return null;
    }

    public enum Type {TOPIC, WORD, USER, GRAMMAR, GRAMMARTASK, RECOMENDATION, RECOMENDATIONLIST, DIALOGUE}
}
