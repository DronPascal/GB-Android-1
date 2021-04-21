package com.pascal.notes_java.list;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pascal.notes_java.R;
import com.pascal.notes_java.model.CardData;
import com.pascal.notes_java.model.CardsSource;
import com.pascal.notes_java.model.CardsSourceImpl;
import com.pascal.notes_java.note.NoteFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class NotesListFragment extends Fragment implements noteOpenerCallback {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private CardsSource data;
    private NotesListAdapter adapter;
    private RecyclerView recyclerView;

    public NotesListFragment() {
    }

    public static NotesListFragment newInstance(int columnCount) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        initView(view);
        setHasOptionsMenu(true);

        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.notes_list);
        data = new CardsSourceImpl(getResources()).init();
        Log.d("", "recreated");

        initRecyclerView(recyclerView, data);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                SimpleDateFormat format = new SimpleDateFormat("dd.MM YYYY");
                CardData cardData = new CardData(
                        "",
                        "",
                        format.format(new Date()),
                        data.getNewCardId());
                data.addCardData(cardData);
                adapter.notifyItemInserted(0);
                //openNote(cardData);
                recyclerView.smoothScrollToPosition(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(RecyclerView recyclerView, CardsSource data) {
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumnCount, StaggeredGridLayoutManager.VERTICAL));
        }

        adapter = new NotesListAdapter(this, data);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecorationHorizontal = new DividerItemDecoration(getContext(), StaggeredGridLayoutManager.VERTICAL);
        itemDecorationHorizontal.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        DividerItemDecoration itemDecorationVertical = new DividerItemDecoration(getContext(), StaggeredGridLayoutManager.HORIZONTAL);
        itemDecorationVertical.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecorationHorizontal);
        recyclerView.addItemDecoration(itemDecorationVertical);
    }

    @Override
    public void openNote(CardData cardData) {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.container_main, NoteFragment.newInstance(cardData.getTitle(), cardData.getDescription(), cardData.getId()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

}