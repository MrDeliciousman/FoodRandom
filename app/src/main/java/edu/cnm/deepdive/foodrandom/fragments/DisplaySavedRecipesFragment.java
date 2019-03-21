package edu.cnm.deepdive.foodrandom.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import edu.cnm.deepdive.foodrandom.R;
import edu.cnm.deepdive.foodrandom.model.FoodDB;
import edu.cnm.deepdive.foodrandom.model.dao.RecipeDao;
import edu.cnm.deepdive.foodrandom.model.entity.Recipe;
import java.util.Objects;

public class DisplaySavedRecipesFragment extends Fragment {

  private ListView recipesListView;
  private ImageView imageView;
  private TextView textView;

  public static DisplaySavedRecipesFragment newInstance(Long id) {
    DisplaySavedRecipesFragment f = new DisplaySavedRecipesFragment();

    // Supply index input as an argument.
    Bundle args = new Bundle();
    args.putLong("id", id);
    f.setArguments(args);

    return f;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.display_saved_recipes_fragment, container, false);

    recipesListView = view.findViewById(R.id.random_recipe_result);
    imageView = view.findViewById(R.id.recipe_image);
    textView = view.findViewById(R.id.recipe_name);

    long id = getArguments().getLong("id", 0);
    new LoadRecipeTask().execute(id);
    return view;
  }


  private class LoadRecipeTask extends AsyncTask<Long, Void, Recipe> {

    @Override
    protected Recipe doInBackground(Long... ids) {
      return FoodDB.getInstance().getRecipeDao().findById(ids[0]);
    }

    @Override
    protected void onPostExecute(Recipe recipe) {

      ArrayAdapter<String> adapter = new ArrayAdapter<>(
          Objects.requireNonNull(DisplaySavedRecipesFragment.this.getActivity()),
          android.R.layout.simple_list_item_1, recipe.getIngredients());

      textView.setText(recipe.getRecipeName());
      Glide.with(imageView).load(recipe.getSmallImageUrls()[0]).into(imageView);
      recipesListView.setAdapter(adapter);

    }
  }
}
