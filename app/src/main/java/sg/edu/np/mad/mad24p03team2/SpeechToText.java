package sg.edu.np.mad.mad24p03team2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.FoodItemClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetAllFood;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetFood;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonFoodSearchResult;

public class SpeechToText extends Fragment implements IDBProcessListener, RecyclerViewInterface  {
    GetFood getFood = null;
    GetAllFood getAllFood = null;
    private ArrayList<FoodItemClass> itemListInDB = null;

    // For voice input display
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private TextView tvRecognizedText;

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speech_to_text, container, false);

        Button btnStartSpeech = view.findViewById(R.id.btnStartSpeech);
        tvRecognizedText = view.findViewById(R.id.tvRecognizedText);
        recyclerView = view.findViewById(R.id.recyclerView);

        btnStartSpeech.setOnClickListener(v -> startSpeechToText());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemListInDB = new ArrayList<FoodItemClass>();
        getFood = new GetFood(requireContext().getApplicationContext(), this);
        getAllFood = new GetAllFood(requireContext().getApplicationContext(), this);
        getAllFood.execute();

        if (recyclerView!= null) {
            foodAdapter = new FoodAdapter(getView().getContext(), itemListInDB, this, false);
            recyclerView.setAdapter(foodAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            Log.e("SpeechToText", "RecyclerView not found in layout");
        }
    }


    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the meal name");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String recognizedText = result.get(0);
                tvRecognizedText.setText(recognizedText);
                filterMealName(recognizedText);
            }
        }
    }

    private void filterMealName(String recognizedText) {
        String mealName = recognizedText.toLowerCase(Locale.ROOT);

        // Filtering logic here to find the meal name
        // Run through the recognized text to find matching food item names in DB
        if (itemListInDB == null || itemListInDB.isEmpty()) {
            Log.d("Food2Nom", "Fail to access Food DB");
            return;
        }

        ArrayList<FoodItemClass> filteredList = new ArrayList<>();
        boolean mealFound = false;
        for (FoodItemClass fItem : itemListInDB) {
            if (mealName.contains(fItem.getName().toLowerCase(Locale.ROOT))) {
                // List to display food item and calories
                filteredList.add(fItem);
                mealFound = true;
            }
        }

        if (mealFound) {
            foodAdapter.setFilteredList(filteredList);

            if (!filteredList.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
            }

        } else {
            // Show a message to the user
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Meal not found, would you like to add a new meal?")
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentActivity activity = getActivity();
                            if (activity instanceof MainActivity2) {
                                ((MainActivity2) activity).replaceFragment(new InputNewFood(), "inputNewFood");
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
    }

    @Override
    public void afterProcess(Boolean executeStatus, Class<? extends AsyncTaskExecutorService> returnClass) {
        itemListInDB = SingletonFoodSearchResult.getInstance().getFoodSearchResult();
        if (itemListInDB != null) {
            Log.d("Food2Nom", "Food DB fetched successfully");
        } else {
            Log.d("Food2Nom", "Food DB fetch failed");
        }

        foodAdapter.setFilteredList(itemListInDB);
    }

    @Override
    public void onItemClick(int itemPos) {
        if (itemPos != RecyclerView.NO_POSITION) {
            FoodItemClass item = itemListInDB.get(itemPos);
            String mealName = item.getName();
            // Move on to addfood page
            switchFragment(item);
        }
    }

    private void switchFragment(FoodItemClass foodItemSelected) {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity2) {
            SingletonFoodSearchResult.getInstance().setSelectedFoodFromSearchResult(foodItemSelected);
            ((MainActivity2) activity).replaceFragment(new AddFood(), "addFood");
        }
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {
    }

    @Override
    public void afterProcess(Boolean executeStatus, String msg, Class<? extends AsyncTaskExecutorService> returnClass) {
    }
}
