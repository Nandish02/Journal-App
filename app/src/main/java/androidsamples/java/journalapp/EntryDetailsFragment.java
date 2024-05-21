package androidsamples.java.journalapp;

// Import statements

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {

  // Declare variables with handwritten names
  private EntryDetailsViewModel mEntryDetailsViewModel;
  private JournalEntry mEntry;
  private EditText mTitleEditText;
  private Button mDateButton;
  private Button mStartTimeButton;
  private Button mEndTimeButton;
  private Toast errorToast;
  private Toast resultToast;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    // Initialize ViewModel
    mEntryDetailsViewModel = new ViewModelProvider(getActivity()).get(EntryDetailsViewModel.class);

    // Load entry if provided
    Bundle arguments = getArguments();
    if (arguments != null && arguments.get(EntryListFragment.KEY_ENTRY_ID) != null) {
      UUID entryId = (UUID) UUID.fromString(arguments.getString(EntryListFragment.KEY_ENTRY_ID));
      mEntryDetailsViewModel.loadEntry(entryId);
    }

    // Handle back button press
    OnBackPressedCallback backPressCallback = new OnBackPressedCallback(true) {
      @Override
      public void handleOnBackPressed() {
        mEntryDetailsViewModel.clearTemp();
        Navigation.findNavController(getView()).popBackStack();
      }
    };
    requireActivity().getOnBackPressedDispatcher().addCallback(this, backPressCallback);
  }

  // Override onCreateOptionsMenu
  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.d_menu, menu);
  }

  // Override onOptionsItemSelected
  @SuppressLint("NonConstantResourceId")
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.share:
        mEntry != null ? shareEntry() : break;
      case R.id.delete:
        mEntry != null ? showDeleteConfirmationDialog() : break;
    }

    return super.onOptionsItemSelected(item);
  }

  // Share entry method
  private void shareEntry() {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.putExtra(Intent.EXTRA_TEXT,"Look what I've been up to: "+mEntry.getTitle()+" on "+mEntry.getDate()+", "+mEntry.getStartTime()+" to "+mEntry.getEndTime());
    intent.setType("text/plain");
    Intent shareIntent = Intent.createChooser(intent,null);
    startActivity(shareIntent);
  }

  // Show delete confirmation dialog method
  private void showDeleteConfirmationDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(R.string.alert_title)
            .setMessage(R.string.alert_message);
    builder.setPositiveButton(R.string.yes, (dialog, which) -> {
      showResultToast(getString(R.string.delete_entry));
      mEntryDetailsViewModel.clearTemp();
      mEntryDetailsViewModel.deleteEntry(mEntry);
      getActivity().onBackPressed();
    });
    builder.setNegativeButton(R.string.no, (dialog, which) -> {
      // Do nothing
    });
    builder.create().show();
  }

  // Override onCreateView
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_details, container, false);
    mTitleEditText = view.findViewById(R.id.edit_title);
    mDateButton = view.findViewById(R.id.btn_entry_date);
    mStartTimeButton = view.findViewById(R.id.btn_start_time);
    mEndTimeButton = view.findViewById(R.id.btn_end_time);
    Button saveButton = view.findViewById(R.id.btn_save);

    mDateButton.setOnClickListener(this::launchDateFragment);
    mStartTimeButton.setOnClickListener(this::launchTimeFragment);
    mEndTimeButton.setOnClickListener(this::launchTimeFragment);
    saveButton.setOnClickListener(this::saveEntry);
    return view;
  }

  // Override onViewCreated
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Observe entry LiveData
    mEntryDetailsViewModel.getEntryLiveData().observe(getActivity(), entry -> {
      this.mEntry = entry;
      updateUI();
    });
    updateUI();
    NavController navController = NavHostFragment.findNavController(this);
    NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.entryDetailsFragment);

    // TextWatcher for title EditText
    mTitleEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do nothing
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Do nothing
      }

      @Override
      public void afterTextChanged(Editable s) {
        String titleToSet = mTitleEditText.getText().toString();
        if (!titleToSet.isEmpty()) {
          mEntryDetailsViewModel.setTempTitle(titleToSet);
        }
      }
    });

    // LifecycleEventObserver
    LifecycleEventObserver observer = (source, event) -> {
      if (event.equals(Lifecycle.Event.ON_RESUME) && navBackStackEntry.getSavedStateHandle().contains(DatePickerFragment.NEW_DATE)) {
        String dateToSet = navBackStackEntry.getSavedStateHandle().get(DatePickerFragment.NEW_DATE);
        mDateButton.setText(dateToSet);
        mEntryDetailsViewModel.setTempDate(dateToSet);
      }
      if (event.equals(Lifecycle.Event.ON_RESUME) && navBackStackEntry.getSavedStateHandle().contains(TimePickerFragment.NEW_TIME)) {
        String timeToSet = navBackStackEntry.getSavedStateHandle().get(TimePickerFragment.NEW_TIME);
        int viewId = Integer.parseInt(timeToSet.substring(timeToSet.indexOf(';') + 1));
        timeToSet = timeToSet.substring(0, timeToSet.indexOf(';'));
        if (viewId == R.id.btn_start_time) {
          mStartTimeButton.setText(timeToSet);
          mEntryDetailsViewModel.setTempStartTime(timeToSet);
        } else if (viewId == R.id.btn_end_time) {
          mEndTimeButton.setText(timeToSet);
          mEntryDetailsViewModel.setTempEndTime(timeToSet);
        }
      }
    };

    navBackStackEntry.getLifecycle().addObserver(observer);
    getViewLifecycleOwner().getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
      if (event.equals(Lifecycle.Event.ON_DESTROY)) {
        navBackStackEntry.getLifecycle().removeObserver(observer);
      }
    });
  }

  // Launch date fragment method
  private void launchDateFragment(View view) {
    NavDirections action = EntryDetailsFragmentDirections.datePickerAction();
    Navigation.findNavController(view).navigate(action);
  }

  // Launch time fragment method
  private void launchTimeFragment(View view) {
    Bundle args = new Bundle();
    args.putInt("viewId", view.getId());
    Navigation.findNavController(view).navigate(R.id.timePickerAction, args); // crashed here once
  }

  // Save entry method
  private void saveEntry(View view) {
    if (mTitleEditText.hasFocus()) {
      mTitleEditText.clearFocus();
    }
    if (checkFields()) {
      if (checkTime()) {
        if (mEntry == null) {
          showResultToast(getString(R.string.success_entry));
          mEntry = new JournalEntry(mTitleEditText.getText().toString(), mDateButton.getText().toString(), mStartTimeButton.getText().toString(), mEndTimeButton.getText().toString());
          mEntryDetailsViewModel.insertEntry(mEntry);
        } else {
          showResultToast(getString(R.string.update_entry));
          mEntry.setTitle(mTitleEditText.getText().toString());
          mEntry.setDate(mDateButton.getText().toString());
          mEntry.setStartTime(mStartTimeButton.getText().toString());
          mEntry.setEndTime(mEndTimeButton.getText().toString());
          mEntryDetailsViewModel.updateEntry(mEntry);
        }
        mEntryDetailsViewModel.clearTemp();
        getActivity().onBackPressed();
      } else {
        showErrorToast(getString(R.string.check_time));
      }
    } else {
      showErrorToast(getString(R.string.check_fields));
    }
  }

  // Check time method
  private boolean checkTime() {
    String startTime = mStartTimeButton.getText().toString();
    String endTime = mEndTimeButton.getText().toString();

    SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
    try {
      Date start = format.parse(startTime);
      Date end = format.parse(endTime);
      assert start != null;
      return !start.after(end);
    } catch (ParseException pe) {
      Log.d("EntryDetailsFragment", "Couldn't parse time");
      return false;
    }
  }

  // Show error toast method
  private void showErrorToast(String errorMsg) {
    if (errorToast != null) {
      errorToast.cancel();
    }
    LayoutInflater inflater = getLayoutInflater();
    View errorView = inflater.inflate(R.layout.error_toast, null);
    TextView errorTextView = errorView.findViewById(R.id.error_txt);
    errorTextView.setText(errorMsg);

    errorToast = new Toast(getActivity());
    errorToast.setView(errorView);
    errorToast.setDuration(Toast.LENGTH_SHORT);
    errorToast.show();
  }

  // Show result toast method
  public void showResultToast(String resultMsg) {
    if (resultToast != null) {
      resultToast.cancel();
    }

    LayoutInflater inflater = getLayoutInflater();
    View resultView = inflater.inflate(R.layout.result_toast, null);
    TextView resultTextView = resultView.findViewById(R.id.result_txt);
    resultTextView.setText(resultMsg);

    resultToast = new Toast(getActivity());
    resultToast.setView(resultView);
    resultToast.setDuration(Toast.LENGTH_SHORT);
    resultToast.show();
  }

  // Check fields method
  private boolean checkFields() {
    return !mTitleEditText.getText().toString().isEmpty() && !mDateButton.getText().toString().equals(getString(R.string.date)) && !mStartTimeButton.getText().toString().equals(getString(R.string.start_time)) && !mEndTimeButton.getText().toString().equals(getString(R.string.end_time));
  }

  // Update UI method
  private void updateUI() {
    if (mEntry != null && !tempState()) {
      mTitleEditText.setText(mEntry.getTitle());
      mDateButton.setText(mEntry.getDate());
      mStartTimeButton.setText(mEntry.getStartTime());
      mEndTimeButton.setText(mEntry.getEndTime());
    } else if (mEntry == null && tempState()) {
      if (mEntryDetailsViewModel.getTempTitle() != null) {
        mTitleEditText.setText(mEntryDetailsViewModel.getTempTitle());
      }
      if (mEntryDetailsViewModel.getTempDate() != null) {
        mDateButton.setText(mEntryDetailsViewModel.getTempDate());
      }
      if (mEntryDetailsViewModel.getTempStartTime() != null) {
        mStartTimeButton.setText(mEntryDetailsViewModel.getTempStartTime());
      }
      if (mEntryDetailsViewModel.getTempEndTime() != null) {
        mEndTimeButton.setText(mEntryDetailsViewModel.getTempEndTime());
      }
    } else if (mEntry != null && tempState()) {
      if (mEntryDetailsViewModel.getTempTitle() != null) {
        mTitleEditText.setText(mEntryDetailsViewModel.getTempTitle());
      } else {
        mTitleEditText.setText(mEntry.getTitle());
      }
      if (mEntryDetailsViewModel.getTempDate() != null) {
        mDateButton.setText(mEntryDetailsViewModel.getTempDate());
      } else {
        mDateButton.setText(mEntry.getDate());
      }
      if (mEntryDetailsViewModel.getTempStartTime() != null) {
        mStartTimeButton.setText(mEntryDetailsViewModel.getTempStartTime());
      } else {
        mStartTimeButton.setText(mEntry.getStartTime());
      }
      if (mEntryDetailsViewModel.getTempEndTime() != null) {
        mEndTimeButton.setText(mEntryDetailsViewModel.getTempEndTime());
      } else {
        mEndTimeButton.setText(mEntry.getEndTime());
      }
    }
  }

  // Check if there is any temporary state
  private boolean tempState() {
    return mEntryDetailsViewModel.getTempTitle() != null || mEntryDetailsViewModel.getTempDate() != null || mEntryDetailsViewModel.getTempStartTime() != null || mEntryDetailsViewModel.getTempEndTime() != null;
  }
}
