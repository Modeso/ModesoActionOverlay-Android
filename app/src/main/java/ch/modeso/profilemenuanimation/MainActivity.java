package ch.modeso.profilemenuanimation;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ch.modeso.mprofileanimatedmenu.widgets.AnimatedMenuItem;
import ch.modeso.mprofileanimatedmenu.widgets.ProfileAnimatedMenu;


public class MainActivity extends AppCompatActivity {

    private static  final String TAG = MainActivity.class.getSimpleName();
    private ProfileAnimatedMenu profileAnimatedMenu;
    private TextView selectedOptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileAnimatedMenu = (ProfileAnimatedMenu) findViewById(R.id.profile_menu);
        selectedOptionText = (TextView) findViewById(R.id.selected_option_item);
        List<AnimatedMenuItem> items  = profileAnimatedMenu.getMenuItems();

        for(AnimatedMenuItem item:items){
            item.setOnItemClicked(new AnimatedMenuItem.OnMenuItemClicked() {
                @Override
                public void onMenuItemClicked(AnimatedMenuItem item) {
                    selectedOptionText.setText(String.format("Option No :%d is Clicked !",
                            item.getIndex()));
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        if(profileAnimatedMenu.isMenuOpened()) {
            profileAnimatedMenu.closeMenu();
        }else
             super.onBackPressed();
    }
}
