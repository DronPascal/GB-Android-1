package com.pascal.notes_java.list;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.pascal.notes_java.model.CardsSourceFirebaseImpl;
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
//        data = new CardsSourceImpl(getResources()).init(cardsSource ->
//                adapter.notifyDataSetChanged());
        data = new CardsSourceFirebaseImpl().init(cardsSource -> {
            adapter.notifyDataSetChanged();
        });
        initView(view);
        setHasOptionsMenu(true);

        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.notes_list);
        initRecyclerView(recyclerView);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumnCount, StaggeredGridLayoutManager.VERTICAL));
        }

        DividerItemDecoration itemDecorationVertical = new DividerItemDecoration(getContext(), StaggeredGridLayoutManager.VERTICAL);
        itemDecorationVertical.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        DividerItemDecoration itemDecorationHorizontal = new DividerItemDecoration(getContext(), StaggeredGridLayoutManager.HORIZONTAL);
        itemDecorationHorizontal.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecorationVertical);
        recyclerView.addItemDecoration(itemDecorationHorizontal);

        adapter = new NotesListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setDataSource(data);
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
                        format.format(new Date()));
                data.addCardData(cardData);
                adapter.notifyItemInserted(0);
                //openNote(cardData);
                recyclerView.smoothScrollToPosition(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        if (item.getItemId() == R.id.action_delete) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.dialog_delete_note)
                    .setMessage(R.string.dialog_delete_note_message)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        data.deleteCardData(position);
                        adapter.notifyItemRemoved(position);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        Toast.makeText(requireContext(), "closed", Toast.LENGTH_SHORT).show();
                    })
                    .setCancelable(true)
                    .create()
                    .show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void openNote(CardData cardData) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.container_main, NoteFragment.newInstance(cardData.getTitle(), cardData.getDescription(), cardData.getId()))
                .addToBackStack(null)
                .commit();
    }
}