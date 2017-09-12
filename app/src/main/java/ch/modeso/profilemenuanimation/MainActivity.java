package ch.modeso.profilemenuanimation;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;
import ch.modeso.mprofileanimatedmenu.widgets.AnimatedMenuItem;
import ch.modeso.mprofileanimatedmenu.widgets.ProfileAnimatedMenu;


public class MainActivity extends AppCompatActivity implements ProfileAnimatedMenu.OnOpenCloseListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProfileAnimatedMenu profileAnimatedMenu;
    private TextView selectedOptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileAnimatedMenu = (ProfileAnimatedMenu) findViewById(R.id.profile_menu);
        selectedOptionText = (TextView) findViewById(R.id.selected_option_item);
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
                        profileAnimatedMenu.setOptionFourIconRes(R.drawable.option4_icon);
                        //profileAnimatedMenu.setOptionFiveIconRes(R.drawable.option5_icon);
                        profileAnimatedMenu.setNumberOfMenuItems(4);
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
        if(actionType== ProfileAnimatedMenu.OnOpenCloseListener.ACTION_TYPE_OPEN)
            selectedOptionText.setText("Menu opened !");
        else
            selectedOptionText.setText("Menu closed !");
    }
}
