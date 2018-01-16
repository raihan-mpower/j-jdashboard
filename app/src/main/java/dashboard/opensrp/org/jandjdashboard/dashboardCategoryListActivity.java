package dashboard.opensrp.org.jandjdashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import dashboard.opensrp.org.jandjdashboard.dummy.DummyContent;
import dashboard.opensrp.org.jandjdashboard.fragments.anc_pnc_encc_StatusDetailFragment;
import dashboard.opensrp.org.jandjdashboard.fragments.dashboardCategoryDetailFragment;
import dashboard.opensrp.org.jandjdashboard.fragments.familyPlanningStatusDetailFragment;
import dashboard.opensrp.org.jandjdashboard.fragments.upcomingScheduleStatusDetailFragment;

import java.util.List;

import static dashboard.opensrp.org.jandjdashboard.dummy.DummyContent.addDrawables;
import static dashboard.opensrp.org.jandjdashboard.dummy.DummyContent.menudrawable;

/**
 * An activity representing a list of dashboardCategories. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link dashboardCategoryDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class dashboardCategoryListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static View previousViewSelectedInLIST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDrawables(this);
        setContentView(R.layout.activity_dashboardcategory_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        toolbar.setTitle("");
        getSupportActionBar().setTitle("");

        View recyclerView = findViewById(R.id.dashboardcategory_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.dashboardcategory_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
                Bundle arguments = new Bundle();
                arguments.putString(upcomingScheduleStatusDetailFragment.ARG_ITEM_ID,"" );
                upcomingScheduleStatusDetailFragment fragment = new upcomingScheduleStatusDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.dashboardcategory_detail_container, fragment)
                        .commit();

            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.date_filter:
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                View popupView = getLayoutInflater().inflate(R.layout.advanced_date_picker, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                popupWindow.showAsDropDown(toolbar);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dashboardcategory_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
            holder.mContentView.setText(mValues.get(position).content);
            holder.mIdView.setImageDrawable(menudrawable.get(position));


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (previousViewSelectedInLIST == null){
                        previousViewSelectedInLIST = holder.mView;
                    }else{
                        previousViewSelectedInLIST.setBackgroundColor(getResources().getColor(R.color.mainblue));
                        previousViewSelectedInLIST = holder.mView;
                    }
                    holder.mView.setBackground(getResources().getDrawable(R.drawable.rotatepointer));
                    if (mTwoPane) {
                        switch (position) {
                            case 0: {
                                Bundle arguments = new Bundle();
                                arguments.putString(upcomingScheduleStatusDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                                upcomingScheduleStatusDetailFragment fragment = new upcomingScheduleStatusDetailFragment();
                                fragment.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.dashboardcategory_detail_container, fragment)
                                        .commit();
                                break;
                            }
                            case 1: {
                                Bundle arguments = new Bundle();
                                arguments.putString(familyPlanningStatusDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                                familyPlanningStatusDetailFragment fragment = new familyPlanningStatusDetailFragment();
                                fragment.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.dashboardcategory_detail_container, fragment)
                                        .commit();
                                break;
                            }
                            case 2: {
                                Bundle arguments = new Bundle();
                                arguments.putString(anc_pnc_encc_StatusDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                                anc_pnc_encc_StatusDetailFragment fragment = new anc_pnc_encc_StatusDetailFragment();
                                fragment.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.dashboardcategory_detail_container, fragment)
                                        .commit();
                                break;
                            }

                        }
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, dashboardCategoryDetailActivity.class);
                        intent.putExtra(upcomingScheduleStatusDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (ImageView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
