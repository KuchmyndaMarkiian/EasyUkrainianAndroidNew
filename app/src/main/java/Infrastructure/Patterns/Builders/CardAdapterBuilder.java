package Infrastructure.Patterns.Builders;

import Hardware.MyFile;
import Hardware.Storage.EasyUkrFiles;
import Infrastructure.CustomAdapters.*;
import MVP.Presenters.CardsPresenter;
import MVP.Presenters.IPresenter;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.mark0.easyukrainian.CardsActivity;
import com.example.mark0.easyukrainian.DialogueDatailsActivity;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Markan on 10.04.2017.
 */
public class CardAdapterBuilder implements IBuilder {

    IPresenter presenter;
    //region Adapters
    private GridView.OnItemClickListener topicOnItemClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            Intent i = new Intent(presenter.getView().getCurrentContext(),
                    CardsActivity.class);
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(((CardsPresenter) presenter).getSubTags().get(position));
            i.putExtra("topic", tmp);
            i.putExtra("type", EasyUkrFiles.Type.WORD);
            presenter.getView().getCurrentContext().startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(
                            presenter.getView().getCurrentContext()).toBundle());
        }
    };
    private GridView.OnItemClickListener recommendOnItemClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            Intent i = new Intent(presenter.getView().getCurrentContext(),
                    CardsActivity.class);
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(((CardsPresenter) presenter).getSubTags().get(position));
            i.putExtra("topic", tmp);
            i.putExtra("type", EasyUkrFiles.Type.RECOMENDATIONLIST);
            presenter.getView().getCurrentContext().startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(
                            presenter.getView().getCurrentContext()).toBundle());
        }
    };
    private GridView.OnItemClickListener gridviewOpenPDF = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            Intent reader = new Intent(Intent.ACTION_VIEW);
            MyFile file = ((CardsPresenter) presenter).getFiles().get(position);
            try {
                File pdf = new File(presenter.getView().getCurrentContext().getExternalCacheDir(), file.getName());
                FileUtils.writeByteArrayToFile(pdf, file.getContent());
                reader.setDataAndType(Uri.fromFile(pdf), "application/pdf");
                reader.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                presenter.getView().getCurrentContext().startActivity(Intent.createChooser(reader, "Open file"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ActivityNotFoundException e) {
                Toast.makeText(
                        presenter.getView().getCurrentContext(), "Install PDF reader!", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private GridView.OnItemClickListener dialogOpen = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            Intent i = new Intent(presenter.getView().getCurrentContext(),
                    DialogueDatailsActivity.class);
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(((CardsPresenter) presenter).getSubTags().get(position));
            i.putExtra("dialogue", tmp.get(0));
            presenter.getView().getCurrentContext().startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(
                            presenter.getView().getCurrentContext()).toBundle());
        }
    };

    //endregion
    public CardAdapterBuilder(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setParts() {
        CardsPresenter _presenter = (CardsPresenter) presenter;
        GridView gridView = _presenter.getGridView();
        SparseArray collection = _presenter.getCollection();
        switch (_presenter.getType()) {
            case GRAMMAR:
                gridView.setOnItemClickListener(gridviewOpenPDF);
                gridView.setAdapter(new GrammarAdapter(presenter.getView().getCurrentContext(), collection));
                break;
            case RECOMENDATION:
                gridView.setOnItemClickListener(recommendOnItemClickListener);
                gridView.setAdapter(new RecommendationAdapter(presenter.getView().getCurrentContext(), collection));
                break;
            case RECOMENDATIONLIST:
                gridView.setAdapter(new RecommendationListAdapter(presenter.getView().getCurrentContext(), collection));
                break;
            case TOPIC:
                gridView.setOnItemClickListener(topicOnItemClickListener);
                gridView.setAdapter(new WordAdapter(presenter.getView().getCurrentContext(), collection));
                break;
            case WORD:
                gridView.setAdapter(new WordAdapter(presenter.getView().getCurrentContext(), collection));
                break;
            case DIALOGUE:
                gridView.setOnItemClickListener(dialogOpen);
                gridView.setAdapter(new DialogueAdapter(presenter.getView().getCurrentContext(), collection));
                break;
        }
    }
}