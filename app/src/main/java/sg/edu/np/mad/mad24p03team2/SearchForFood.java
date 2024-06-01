package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.FoodItemClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetAllFood;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetFood;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonFoodSearchResult;

interface RecyclerViewInterface {
    void onItemClick(int itemPos);
}

public class SearchForFood extends Fragment implements IDBProcessListener, RecyclerViewInterface {
    GetFood getFood = null;
    GetAllFood getAllFood = null;
    private RecyclerView recyclerView;
    private List<FoodItemClass> itemList;
    private FoodAdapter foodAdapter;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_for_food, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemList = new ArrayList<FoodItemClass>();
        getFood = new GetFood(requireContext().getApplicationContext(), this);
        getAllFood = new GetAllFood(requireContext().getApplicationContext(), this);
        foodAdapter = new FoodAdapter(getView().getContext(), itemList, this);

        // TODO: LOOK HERE FOR DATABASE ACCESS
        getAllFood.execute(); // This is to get all food in database


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus(); // Remove cursor from search bar

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 1. User enter query text, send the text to search the db
            @Override
            public boolean onQueryTextChange(String newText) {
                getFood.execute(newText); // This is to get from search query, Result get from Singleton in afterProcess
                return true;
            }
        });
    }

    @Override
    public void afterProcess(Boolean executeStatus) {
        // ALL PROCESSES AFTER DATABASE CALL MUST BE WRITTEN HERE !!

        itemList = SingletonFoodSearchResult.getInstance().getFoodSearchResult();
        foodAdapter.setFilteredList(itemList);
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }

    @Override
    public void onItemClick(int itemPos) {
        // Move on to addfood page
      switchFragment();
    }

    private void switchFragment() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity2) {
            ((MainActivity2) activity).replaceFragment(new AddFood());
        }
    }
}
