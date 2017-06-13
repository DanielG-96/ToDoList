package com.danielg.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class RecyclerViewFragment extends Fragment {

    private Context mContext;
    private SortType sortType;

    static final int REQ_ADD_ENTRY = 10;
    static final int REQ_EDIT_ENTRY = 11;

    @BindView(R.id.fab_add) FloatingActionButton fabAdd;
    @BindView(R.id.rv) RecyclerView mRecyclerView;
    private List<Entry> mEntries;
    private RecyclerView.LayoutManager mLayoutManager;
    private EntryRecyclerAdapter mAdapter;
    private static final String ENTRIES_LIST = "ENTRIES";

    public RecyclerViewFragment() {
        // Mandatory constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);

        Toolbar mainToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mainToolbar);

        setHasOptionsMenu(true);
        mEntries = Utilities.deserializeList(mContext, ENTRIES_LIST);

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EntryRecyclerAdapter(mEntries);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @OnClick(R.id.fab_add)
    public void fabClick() {
        Intent addItemIntent = new Intent(mContext, AddEntryActivity.class);
        startActivityForResult(addItemIntent, REQ_ADD_ENTRY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_ADD_ENTRY) {
            if(resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                if(bundle != null) {
                    Entry entry = (Entry) bundle.getSerializable(AddEntryActivity.KEY_ENTRY);
                    mAdapter.addItem(entry);
                }
            }
        } else if(requestCode == REQ_EDIT_ENTRY) {
            if(resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                if(bundle != null) {
                    // TODO: Edit entry
                }
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("sortType", sortType.ordinal());

        editor.commit();

        Utilities.serializeList(mContext, mEntries, ENTRIES_LIST);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        int prefSortType = sp.getInt("sortType", 0);

        sortType = SortType.fromInteger(prefSortType);

        switch(sortType) {
            case Default:
                menu.findItem(R.id.sort_default_button).setChecked(true);
                break;
            case NameAscending:
                menu.findItem(R.id.sort_name_asc_button).setChecked(true);
                break;
            case NameDescending:
                menu.findItem(R.id.sort_name_desc_button).setChecked(true);
                break;
            case DateAdded:
                menu.findItem(R.id.sort_date_added_button).setChecked(true);
                break;
            default:
                menu.findItem(R.id.sort_default_button).setChecked(true);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.sort_default_button:
                item.setChecked(true);
                sortType = SortType.Default;
                return true;
            case R.id.sort_name_asc_button:
                item.setChecked(true);
                sortType = SortType.NameAscending;
                return true;
            case R.id.sort_name_desc_button:
                item.setChecked(true);
                sortType = SortType.NameDescending;
                return true;
            case R.id.sort_date_added_button:
                item.setChecked(true);
                sortType = SortType.DateAdded;
                return true;
            case R.id.menu_settings_button:
                Toast.makeText(mContext, "Not implemented", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private enum SortType {
        Default, NameAscending, NameDescending, DateAdded, DateModified;

        private static SortType[] cachedValues = null;
        /**
         * Returns an enum from a specified value from 0 to 3
         * @param i The value in an integer (0=Default, 1=NameAscending, 2=NameDescending, 3=DateAdded, Default otherwise)
         * @return SortType enum value
         */
        public static SortType fromInteger(int i) {
            if(cachedValues == null) {
                cachedValues = SortType.values();
            }
            try {
                return cachedValues[i];
            }
            catch(Exception ex) {
                return cachedValues[0];
            }
        }
    }
}
