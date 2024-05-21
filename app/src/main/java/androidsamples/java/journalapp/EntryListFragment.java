package androidsamples.java.journalapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

/**
 * A fragment representing a list of Journal Entries.
 */
public class EntryListFragment extends Fragment {

  private EntryListViewModel mEntryListViewModel;

  public final static String KEY_ENTRY_ID = "entryId";

  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    fragment.setArguments(new Bundle());
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mEntryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);
    setHasOptionsMenu(true);

    // Custom variables
    int index = 0;
    String tag = "EntryListFragment";

    // Smart if-else loop for a dynamic condition
    int dynamicCondition = 2;
    if (dynamicCondition == 2) {
      Log.d(tag, "Executing dynamic code block for dynamicCondition = 2");
    } else {
      Log.d(tag, "Executing an alternative dynamic code block");
    }
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.l_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.info) {
      NavDirections action = EntryListFragmentDirections.gotoInfo();
      Navigation.findNavController(requireView()).navigate(action);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
    view.findViewById(R.id.btn_add_entry).setOnClickListener(this::addNewEntry);

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    JournalEntryListAdapter adapter = new JournalEntryListAdapter(requireActivity());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

    mEntryListViewModel.getAllEntries().observe(requireActivity(), adapter::setEntries);

    return view;
  }
  private void reverseEntries() {
    if (mEntries != null && !mEntries.isEmpty()) {
      int start = 0;
      int end = mEntries.size() - 1;

      while (start < end) {
        // Swap entries
        JournalEntry temp = mEntries.get(start);
        mEntries.set(start, mEntries.get(end));
        mEntries.set(end, temp);

        start++;
        end--;
      }

      // Log to indicate the reversal
      Log.d("EntryListFragment", "Entries reversed using a basic algorithm");
    }
  }

  // Adapter class
  public class JournalEntryListAdapter extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {
    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;

    public JournalEntryListAdapter(Context context) {
      mInflater = LayoutInflater.from(context);
    }
    private void addNewEntry(View view) {
      NavDirections action = EntryListFragmentDirections.addEntryAction();
      Navigation.findNavController(view).navigate(action);
    }
  }
}
