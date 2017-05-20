package Infrastructure.MainOperations;

import Hardware.Storage.EasyUkrFiles;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.OkHttp;
import Infrastructure.RESTful.WebAPI.WebApiGet;
import Infrastructure.Serialization.Serializer;
import Models.LearningResources.*;
import Models.ModelsFromServer.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Markan on 14.02.2017.
 */
public class Download {
    public static void DownloadDictionary(Serializer serial) {
        WebApiGet apiGet = new WebApiGet();
        try {
            if (serial.mainType == EasyUkrFiles.Type.TOPIC) {
                ArrayList<Word> elements = new ArrayList<>();
                ArrayList<TopicJson> tmp = (ArrayList<TopicJson>) apiGet.getContent(ConstURL.getTopicUrl(),
                        new TypeToken<ArrayList<TopicJson>>() {
                        }.getType());
                for (TopicJson topic : tmp) {
                    elements.add(new Word(null, topic.id, topic.text, topic.translate,
                            apiGet.getFile(null, ConstURL.getFileUrl(), OkHttp.ParameterType.PARAMETER_TYPE, "word", String.valueOf(topic.translateImageId))));
                }
                serial.writeObject(elements);
            } else if (serial.mainType == EasyUkrFiles.Type.WORD) {
                ArrayList<Word> elements = new ArrayList<>();
                ArrayList<WordJson> tmp = (ArrayList<WordJson>) apiGet.getContent(ConstURL.getWordUrl(),
                        new TypeToken<ArrayList<WordJson>>() {
                        }.getType());

                for (WordJson word : tmp) {
                    elements.add(new Word(word.parentId, word.id, word.text, word.translate,
                            apiGet.getFile(null, ConstURL.getFileUrl(), OkHttp.ParameterType.PARAMETER_TYPE, "word", String.valueOf(word.translateImageId))));
                }
                serial.writeObject(elements);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serial.close();
        }
    }

    public static void DownloadGrammar(Serializer serial) {
        WebApiGet apiGet = new WebApiGet();
        try {
            ArrayList<Grammar> elements = new ArrayList<>();
            ArrayList<GrammarJson> tmp = (ArrayList<GrammarJson>) apiGet.getContent(ConstURL.getGrammarUrl(),
                    new TypeToken<ArrayList<GrammarJson>>() {
                    }.getType());

            for (GrammarJson grammar : tmp) {
                elements.add(new Grammar(grammar.text, grammar.translate,
                        apiGet.getFile(null, ConstURL.getFileUrl(), OkHttp.ParameterType.PARAMETER_TYPE, "grammar", String.valueOf(grammar.id))));
            }
            serial.writeObject(elements);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serial.close();
        }
    }

    public static void DownloadGrammarTasks(Serializer serial) {
        WebApiGet apiGet = new WebApiGet();
        try {
            ArrayList<GrammarTask> elements = (ArrayList<GrammarTask>) apiGet.getContent(ConstURL.getGrammarTasksUrl(),
                    new TypeToken<ArrayList<GrammarTask>>() {
                    }.getType());
            serial.writeObject(elements);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serial.close();
        }
    }

    public static void DownloadRecommendationCategories(Serializer serial) {
        WebApiGet apiGet = new WebApiGet();
        try {
            ArrayList<RecommendationCategory> elements = new ArrayList<>();
            ArrayList<RecommendationCategoryJson> tmp = (ArrayList<RecommendationCategoryJson>) apiGet.getContent(ConstURL.getRecommendationCategoryUrl(),
                    new TypeToken<ArrayList<RecommendationCategoryJson>>() {
                    }.getType());

            for (RecommendationCategoryJson recommcat : tmp) {
                elements.add(new RecommendationCategory(recommcat.id, recommcat.text,
                        recommcat.translate,
                        apiGet.getFile(null,
                                ConstURL.getFileUrl(),
                                OkHttp.ParameterType.PARAMETER_TYPE,
                                "recommendc",
                                String.valueOf(recommcat.id))));
            }
            serial.writeObject(elements);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serial.close();
        }
    }

    public static void DownloadRecommendations(Serializer serial) {
        WebApiGet apiGet = new WebApiGet();
        try {
            ArrayList<Recommendation> elements = new ArrayList<>();
            ArrayList<RecommendationJson> tmp = (ArrayList<RecommendationJson>) apiGet.getContent(ConstURL.getRecommendationUrl(),
                    new TypeToken<ArrayList<RecommendationJson>>() {
                    }.getType());
            for (RecommendationJson recommend : tmp) {
                elements.add(new Recommendation(recommend.text,
                        recommend.translate, recommend.urlLink,
                        apiGet.getFile(null,
                                ConstURL.getFileUrl(),
                                OkHttp.ParameterType.PARAMETER_TYPE,
                                "recommend",
                                String.valueOf(recommend.id)),
                        recommend.parentId));
            }
            serial.writeObject(elements);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serial.close();
        }
    }

    public static void DownloadDialogues(Serializer serial) {
        WebApiGet apiGet = new WebApiGet();
        try {
            ArrayList<Dialogue> elements = new ArrayList<>();
            ArrayList<DialogueJson> tmp = (ArrayList<DialogueJson>) apiGet.getContent(ConstURL.getDialogueUrl(),
                    new TypeToken<ArrayList<DialogueJson>>() {
                    }.getType());
            for (DialogueJson dialogue : tmp) {
                elements.add(new Dialogue(dialogue.id, dialogue.header, dialogue.dialogueUkr, dialogue.dialogueEng,
                        apiGet.getFile(null, ConstURL.getFileUrl(),
                                OkHttp.ParameterType.PARAMETER_TYPE,
                                "dialogue",
                                String.valueOf(dialogue.id))));
            }
            serial.writeObject(elements);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serial.close();
        }
    }
}