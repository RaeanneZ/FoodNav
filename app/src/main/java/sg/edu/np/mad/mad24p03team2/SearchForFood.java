package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.FoodItemClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetFood;

public class SearchForFood extends Fragment implements IDBProcessListener {
    GetFood getFood = null;
    private RecyclerView recyclerView;
    private List<Item> itemList;
    private FoodAdapter foodAdapter;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getFood = new GetFood(requireContext().getApplicationContext(), this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_for_food, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 1. User enter query text, send the text to search the db
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        //SIAN KIM TODO:
        ArrayList<FoodItemClass> foodItemList= getFood.execute(queryText);
        // what does this mean?


        //TODO: 3. User select from the list displayed and UI switched to <Add Food> Page


        // Get all foodItem from MSSQL and display it here
        List<Item> items = new ArrayList<Item>();
        for (FoodItemClass foodItemClass: foodItemList) {
            items.add(new Item(foodItemClass.getName(), foodItemClass.getCalories(), foodItemClass.getServing_size_g()));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FoodAdapter(getContext(),items, this));
    }

    private void filterList(String text) {
        List<Item> filteredList = new ArrayList<>();
        //2. UI Display <foodItemList> for user to choose
        for (Item item : itemList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(),"No data found", Toast.LENGTH_SHORT).show();
        } else {
            foodAdapter.setFilteredList(filteredList);
        }

    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }

    @Override
    public void afterProcess(Boolean executeStatus) {

    }
}