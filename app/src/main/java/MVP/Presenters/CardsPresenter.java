package MVP.Presenters;

import Hardware.MyFile;
import Hardware.Storage.EasyUkrFiles;
import Infrastructure.Patterns.Builders.CardAdapterBuilder;
import Infrastructure.Patterns.Builders.IBuilder;
import Infrastructure.Serialization.Deserializer;
import MVP.Views.IView;
import Models.LearningResources.*;
import android.content.Intent;
import android.util.SparseArray;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * Created by Markan on 10.04.2017.
 */
public class CardsPresenter implements IPresenter {
    IView view;
    EasyUkrFiles.Type type;
    SparseArray elements;
    SparseArray<Integer> childrens;
    SparseArray<MyFile> grammarFiles;
    GridView gridView = null;

    public GridView getGridView() {
        return gridView;
    }

    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    public void init() {
        //region Deserializator
        Deserializer des = null;
        switch (type) {
            case TOPIC:
                des = new Deserializer<Word>(type, view.getCurrentContext().getExternalFilesDir(null));
                break;
            case WORD:
                des = new Deserializer<Word>(type, view.getCurrentContext().getExternalFilesDir(null));
                break;
            case GRAMMAR:
                des = new Deserializer<Grammar>(type, view.getCurrentContext().getExternalFilesDir(null));
                break;
            case RECOMENDATION:
                des = new Deserializer<RecommendationCategory>(type, view.getCurrentContext().getExternalFilesDir(null));
                break;
            case RECOMENDATIONLIST:
                des = new Deserializer<Recommendation>(type, view.getCurrentContext().getExternalFilesDir(null));
                gridView.setNumColumns(1);
                break;
            case DIALOGUE:
                des = new Deserializer<Dialogue>(type, view.getCurrentContext().getExternalFilesDir(null));
                break;
        }
        ArrayList tmp = des.readObject();
        elements = new SparseArray();
        int index = 0;
        for (Object obj : tmp) {
            elements.put(index++, obj);
        }
//endregion
        //region init
        if (elements == null || elements.size() == 0)
            return;
        int i = 0;
        int id = -1;
        Intent intent = view.getCurrentContext().getIntent();
        //endregion
        int length;
        IBuilder builder = new CardAdapterBuilder(this);
        {
            switch (type) {
                //region GRAMMAR
                case GRAMMAR:
                    grammarFiles = new SparseArray<>();
                    SparseArray<Grammar> grammar = elements;
                    length = grammar.size();
                    for (int k = 0; k < length; k++)
                        grammarFiles.put(k, grammar.get(k).getFile());
                    break;
                //endregion
                //region RECOMMENDATION
                case RECOMENDATION:
                    childrens = new SparseArray<>();
                    SparseArray<RecommendationCategory> recomendationCategories = elements;
                    length = recomendationCategories.size();
                    for (int k = 0; k < length; k++)
                        childrens.put(k, recomendationCategories.get(k).getID());
                    break;
                //endregion
                //region RECOMENDATIONLIST
                case RECOMENDATIONLIST: {
                    id = intent.getIntegerArrayListExtra("topic").get(0);
                    if (id == -1)
                        return;
                    SparseArray<Recommendation> recommendations = elements;
                    SparseArray<Recommendation> tmpRec = new SparseArray<>();
                    int k = 0;
                    for (int j = 0; j < elements.size(); j++)
                        if (recommendations.get(j).getParent() == id)
                            tmpRec.put(k++, recommendations.get(j));
                    elements = tmpRec;
                    break;
                }
                //endregion
                //region DIALOGUE
                case DIALOGUE:
                    childrens = new SparseArray<>();
                    SparseArray<Dialogue> dialogues = elements;
                    length = dialogues.size();
                    for (int k = 0; k < length; k++)
                        childrens.put(k, dialogues.get(k).getId());
                    break;
                //endregion
                //region TOPIC or WORD
                default: {
                    if (type == EasyUkrFiles.Type.TOPIC) {
                        childrens = new SparseArray<>();
                        SparseArray<Word> words = elements;
                        length = words.size();
                        for (int k = 0; k < length; k++)
                            childrens.put(k, words.get(k).getID());
                        break;

                    } else {
                        id = intent.getIntegerArrayListExtra("topic").get(0);
                        if (id == -1)
                            return;
                        SparseArray<Word> words = elements;
                        SparseArray<Word> tmpw = new SparseArray<>();
                        int k = 0;
                        for (int j = 0; j < elements.size(); j++)
                            if (words.get(j).getParent() == id)
                                tmpw.put(k++, words.get(j));
                        elements = tmpw;
                    }

                }
                //endregion

            }
            des.close();

            builder.setParts();
        }
    }

    public EasyUkrFiles.Type getType() {
        return type;
    }

    public void setType(EasyUkrFiles.Type type) {
        this.type = type;
    }

    @Override
    public IView getView() {
        return view;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    public SparseArray getCollection() {
        return elements;
    }

    public SparseArray<Integer> getSubTags() {
        return childrens;
    }

    public SparseArray<MyFile> getFiles() {
        return grammarFiles;
    }
}
