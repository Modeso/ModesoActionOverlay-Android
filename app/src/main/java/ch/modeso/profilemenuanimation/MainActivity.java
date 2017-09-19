package ch.modeso.profilemenuanimation;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import ch.modeso.mprofileanimatedmenu.widgets.AnimatedMenuItem;
import ch.modeso.mprofileanimatedmenu.widgets.ProfileAnimatedMenu;


public class MainActivity extends AppCompatActivity implements ProfileAnimatedMenu.OnOpenCloseListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProfileAnimatedMenu profileAnimatedMenu;
    private TextView selectedOptionText;
    private ImageView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileAnimatedMenu = (ProfileAnimatedMenu) findViewById(R.id.profile_menu);
        selectedOptionText = (TextView) findViewById(R.id.selected_option_item);
        searchView =  (ImageView) findViewById(R.id.search_view);
        List<AnimatedMenuItem> items  = profileAnimatedMenu.getMenuItems();

        for(final AnimatedMenuItem item:items){
            item.setOnItemClicked(new AnimatedMenuItem.OnMenuItemClicked() {
                @Override
                public void onMenuItemClicked(AnimatedMenuItem item) {
                    selectedOptionText.setText(String.format("Option No :%d is Clicked !",
                            item.getIndex()));
                    if(item.getIndex() == 1){
                        profileAnimatedMenu.setNumberOfMenuItems(3);
                    }
                    if(item.getIndex() == 2){
                        profileAnimatedMenu.setOptionFourIconRes(R.drawable.ic_outline_blank_black_24dp);
                        profileAnimatedMenu.setOptionFiveIconRes(R.drawable.icons_download);
                        profileAnimatedMenu.setNumberOfMenuItems(5);
                    }
                }
            });
        }
//      Adding open-close listener
        profileAnimatedMenu.setOnOpenCloseListener(this);
    }


    @Override
    public void onBackPressed() {
        if(profileAnimatedMenu.isMenuOpened()) {
            profileAnimatedMenu.closeMenu();
        }else
             super.onBackPressed();
    }

    @Override
    public void onActionStarted(int actionType) {
        //Track animation actions here
    }

    @Override
    public void onActionEnded(int actionType) {
        if(actionType== ProfileAnimatedMenu.OnOpenCloseListener.ACTION_TYPE_OPEN) {
            //Menu opened
            selectedOptionText.setText("Menu opened !");
            searchView.setVisibility(View.INVISIBLE);
        }
        else {
            //Menu closed
            selectedOptionText.setText("Menu closed !");
            searchView.setVisibility(View.VISIBLE);
        }
    }
}
