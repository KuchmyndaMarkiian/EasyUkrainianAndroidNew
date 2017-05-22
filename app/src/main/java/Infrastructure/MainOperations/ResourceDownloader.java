package Infrastructure.MainOperations;

import Hardware.Storage.EasyUkrFiles;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.OkHttp3Manager;
import Infrastructure.Serialization.Serializer;
import Models.LearningResources.*;
import Models.ModelsFromServer.*;
import android.content.Context;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Markan on 14.02.2017.
 */
//Клас для завантаження ресурсів
public class ResourceDownloader {
    public static void DownloadDictionary(Context baseContext, Serializer serial) {
        try {
            if (serial.mainType == EasyUkrFiles.Type.TOPIC) {
                ArrayList<Word> elements = new ArrayList<>();
                ArrayList<TopicJson> tmp = (new ServerDownloader<ArrayList<TopicJson>>(baseContext)).getContent(ConstURL.getTopicUrl(),
                        new TypeToken<ArrayList<TopicJson>>() {
                        }.getType());
                for (TopicJson topic : tmp) {
                    elements.add(new Word(null, topic.id, topic.text, topic.transcription, topic.translate,
                            ServerDownloader.getFile(null, ConstURL.getFileUrl(),
                                    OkHttp3Manager.ParameterType.PARAMETER, "word", String.valueOf(topic.translateImageId))));
                }
                serial.writeObject(elements);
            } else if (serial.mainType == EasyUkrFiles.Type.WORD) {
                ArrayList<Word> elements = new ArrayList<>();
                ArrayList<WordJson> tmp = (new ServerDownloader<ArrayList<WordJson>>(baseContext)).getContent(ConstURL.getWordUrl(),
                        new TypeToken<ArrayList<WordJson>>() {
                        }.getType());
                for (WordJson word : tmp) {
                    elements.add(new Word(word.parentId, word.id, word.text, word.transcription, word.translate,
                            ServerDownloader.getFile(null, ConstURL.getFileUrl(),
                                    OkHttp3Manager.ParameterType.PARAMETER, "word", String.valueOf(word.translateImageId))));
                }
                serial.writeObject(elements);
            }
        } finally {
            serial.close();
        }
    }
    public static void DownloadGrammar(Context baseContext, Serializer serial) {
        try {
            ArrayList<Grammar> elements = new ArrayList<>();
            ArrayList<GrammarJson> tmp = (new ServerDownloader<ArrayList<GrammarJson>>(baseContext)).getContent(ConstURL.getGrammarUrl(),
                    new TypeToken<ArrayList<GrammarJson>>() {
                    }.getType());

            for (GrammarJson grammar : tmp) {
                elements.add(new Grammar(grammar.text, grammar.translate,
                        ServerDownloader.getFile(null, ConstURL.getFileUrl(),
                                OkHttp3Manager.ParameterType.PARAMETER, "grammar", String.valueOf(grammar.id))));
            }
            serial.writeObject(elements);
        } finally {
            serial.close();
        }
    }
    public static void DownloadGrammarTasks(Context baseContext, Serializer serial) {
        try {
            ArrayList<GrammarTask> elements = (new ServerDownloader<ArrayList<GrammarTask>>(baseContext)).getContent(ConstURL.getGrammarTasksUrl(),
                    new TypeToken<ArrayList<GrammarTask>>() {
                    }.getType());
            serial.writeObject(elements);
        } finally {
            serial.close();
        }
    }
    public static void DownloadRecommendationCategories(Context baseContext, Serializer serial) {
        try {
            ArrayList<RecommendationCategory> elements = new ArrayList<>();
            ArrayList<RecommendationCategoryJson> tmp = (new ServerDownloader<ArrayList<RecommendationCategoryJson>>(baseContext)).getContent(ConstURL.getRecommendationCategoryUrl(),
                    new TypeToken<ArrayList<RecommendationCategoryJson>>() {
                    }.getType());
            for (RecommendationCategoryJson recommcat : tmp) {
                elements.add(new RecommendationCategory(recommcat.id, recommcat.text,
                        recommcat.translate,
                        ServerDownloader.getFile(null, ConstURL.getFileUrl(),
                                OkHttp3Manager.ParameterType.PARAMETER,
                                "recommendc",
                                String.valueOf(recommcat.id))));
            }
            serial.writeObject(elements);
        } finally {
            serial.close();
        }
    }
    public static void DownloadRecommendations(Context baseContext, Serializer serial) {
        try {
            ArrayList<Recommendation> elements = new ArrayList<>();
            ArrayList<RecommendationJson> tmp = (new ServerDownloader<ArrayList<RecommendationJson>>(baseContext)).getContent(ConstURL.getRecommendationUrl(),
                    new TypeToken<ArrayList<RecommendationJson>>() {
                    }.getType());
            for (RecommendationJson recommend : tmp) {
                elements.add(new Recommendation(recommend.text,
                        recommend.translate, recommend.urlLink,
                        ServerDownloader.getFile(null, ConstURL.getFileUrl(),
                                OkHttp3Manager.ParameterType.PARAMETER,
                                "recommend",
                                String.valueOf(recommend.id)),
                        recommend.parentId));
            }
            serial.writeObject(elements);
        } finally {
            serial.close();
        }
    }
    public static void DownloadDialogues(Context baseContext, Serializer serial) {
        try {
            ArrayList<Dialogue> elements = new ArrayList<>();
            ArrayList<DialogueJson> tmp = (new ServerDownloader<ArrayList<DialogueJson>>(baseContext)).getContent(ConstURL.getDialogueUrl(),
                    new TypeToken<ArrayList<DialogueJson>>() {
                    }.getType());
            for (DialogueJson dialogue : tmp) {
                elements.add(new Dialogue(dialogue.id, dialogue.header, dialogue.dialogueUkr, dialogue.dialogueEng,
                        ServerDownloader.getFile(null, ConstURL.getFileUrl(),
                                OkHttp3Manager.ParameterType.PARAMETER,
                                "dialogue",
                                String.valueOf(dialogue.id))));
            }
            serial.writeObject(elements);
        } finally {
            serial.close();
        }
    }
}