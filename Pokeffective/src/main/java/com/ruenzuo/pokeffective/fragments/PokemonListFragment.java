package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.PokemonAdapter;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.tasks.SQLiteTask;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class PokemonListFragment extends ListFragment {

    public interface OnPokemonSelectedListener {
        public void onCountrySelected(Pokemon pokemon);
    }

    private OnPokemonSelectedListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnPokemonSelectedListener) {
            listener = (OnPokemonSelectedListener)activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement PokemonListFragment.OnPokemonSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PokemonAdapter adapter = new PokemonAdapter(getActivity(), R.layout.pokemon_row);
        setListAdapter(adapter);
        Groundy.create(SQLiteTask.class).callback(this).queueUsing(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pokemon_list_fragment, container, false);
        return view;
    }

    @OnSuccess(SQLiteTask.class)
    public void onSuccess(@Param("Pokemons") ArrayList<Pokemon> pokemons) {
        PokemonAdapter adapter = (PokemonAdapter)getListAdapter();
        adapter.clear();
        adapter.addAll(pokemons);
        adapter.notifyDataSetChanged();
    }

}