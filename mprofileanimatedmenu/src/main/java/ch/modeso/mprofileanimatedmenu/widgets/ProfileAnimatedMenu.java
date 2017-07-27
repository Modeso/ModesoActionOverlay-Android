package ch.modeso.mprofileanimatedmenu.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.modeso.mprofileanimatedmenu.R;


/**
 * Created by user on 25/07/2017.
 */

public class ProfileAnimatedMenu extends FrameLayout {
    private static final String TAG = ProfileAnimatedMenu.class.getSimpleName();

    private ImageView handle;
    private ImageView frontHandle;
    private ImageView profileImage;
    private TextView shieldView;
    private LinearLayout menuItemsContainer;
    private FrameLayout containerView;
    private ImageView closeMenuBtn;
    private float originalHandleX  =-1;
    private float originalHandleY  =-1;

    private boolean menuOpened = false;
    private static final float FINAL_ALPHA = 0.91f;
    private AnimatorSet openMenuAnimatorSet ;
    private AnimatorSet closeMenuAnimatorSet ;
    private boolean openRunning = false;
    private boolean closeRunning = false;

    ///////////////////////////////////////////////////////////
    //      Style attributes
    ///////////////////////////////////////////////////////////
    private int numberOfMenuItems = 3;
    private boolean animateItemOnclick = false;
    private boolean allowItemRotationAnim = false;
    private int profileImageRes = -1;
    private int handleImageRes = -1;

    private int optionOneIconRes = -1;
    private int optionTwoIconRes = -1;
    private int optionThreeIconRes = -1;
    private  int optionFourIconRes = -1;
    private  int optionFiveIconRes = -1;
    private OnOpenCloseListener onOpenCloseListener;

    public ProfileAnimatedMenu(Context context) {
        super(context);
        init(null);
    }

    public ProfileAnimatedMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProfileAnimatedMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    @TargetApi(21)
    public ProfileAnimatedMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs){

        if(attrs != null){
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ProfileAnimatedMenu,
                    0, 0);
            //Grabbing styles from xml
            try {
                animateItemOnclick = a.getBoolean(R.styleable.ProfileAnimatedMenu_animateItemOnClick, false);
                allowItemRotationAnim = a.getBoolean(R.styleable.ProfileAnimatedMenu_allowItemRotationAnim, false);
                numberOfMenuItems = a.getInteger(R.styleable.ProfileAnimatedMenu_numberOfMenuItems, 0);
                profileImageRes = a.getResourceId(R.styleable.ProfileAnimatedMenu_profileImage,-1);
                handleImageRes = a.getResourceId(R.styleable.ProfileAnimatedMenu_handleIconRes,-1);

                optionOneIconRes = a.getResourceId(R.styleable.ProfileAnimatedMenu_itemOneIconRes,-1);
                optionTwoIconRes = a.getResourceId(R.styleable.ProfileAnimatedMenu_itemTwoIconRes,-1);
                optionThreeIconRes = a.getResourceId(R.styleable.ProfileAnimatedMenu_itemThreeIconRes,-1);
                optionFourIconRes = a.getResourceId(R.styleable.ProfileAnimatedMenu_itemFourIconRes,-1);
                optionFiveIconRes = a.getResourceId(R.styleable.ProfileAnimatedMenu_itemFiveIconRes,-1);
            } finally {
                a.recycle();
            }
        }

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.profile_animated_menu,
                this,true);

        shieldView = (TextView) findViewById(R.id.sheild);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        menuItemsContainer = (LinearLayout) findViewById(R.id.menue_items_container);
        handle = (ImageView) findViewById(R.id.handle_view);
        frontHandle = (ImageView) findViewById(R.id.front_handle);
        containerView = (FrameLayout) findViewById(R.id.profile_container);
        closeMenuBtn = (ImageView) findViewById(R.id.close_menu);
        handle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!menuOpened) {
                    openMenu();
                    handle.setEnabled(false);
                }

            }
        });
        closeMenuBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuOpened) {
                    closeMenu();
                    closeMenuBtn.setEnabled(false);
                }
            }
        });

        if(handleImageRes>0){
            frontHandle.setImageResource(handleImageRes);
        }
        if(profileImageRes > 0)
            profileImage.setImageResource(profileImageRes);

        initMenuItems();
    }

    void initMenuItems(){
        /*if( !isInEditMode())*/ {
            int childCount = menuItemsContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = menuItemsContainer.getChildAt(i);
                if ((!(view instanceof AnimatedMenuItem)))
                    throw new IllegalArgumentException("menu Items should be instances of AnimatedMenuItem !!");
                AnimatedMenuItem item = (AnimatedMenuItem) view;
                if (animateItemOnclick)
                    item.setAnimateOnClickEnabled(true);

                if (i == 0 && optionOneIconRes > 0)
                    item.setImageResource(optionOneIconRes);
                if (i == 1 &&optionTwoIconRes > 0)
                    item.setImageResource(optionTwoIconRes);
                if (i == 2 && optionThreeIconRes > 0)
                    item.setImageResource(optionThreeIconRes);
                if (i == 3 && optionFourIconRes > 0)
                    item.setImageResource(optionFourIconRes);
                if (i == 4 && optionFiveIconRes > 0)
                    item.setImageResource(optionFiveIconRes);

                if (i >= numberOfMenuItems)
                    item.setVisibility(GONE);
            }
        }
    }

    /**
     * Opens the menu with animation
     */
    public void openMenu(){
        if(openRunning)
            return;

        //animating fronhandle disappearance
        ObjectAnimator disappearAnim = ObjectAnimator.ofFloat(frontHandle,"alpha",0f);

        //frontHandle.setImageResource(0);
        openRunning =  true;
        originalHandleX = handle.getX();
        originalHandleY = handle.getY();
        //Move with scale animation
        float finalX = (profileImage.getWidth()-handle.getWidth())/2;
        float finalY = (profileImage.getHeight()-handle.getHeight())/2;
        ////////////////////////////////////////
        //Move animes//
        /////////////////////////////////////////

        ObjectAnimator animX = ObjectAnimator.ofFloat(handle,"x"/*,point1X,point2X*/,finalX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(handle,"y"/*,point1Y,point2Y*/,finalY);

        ObjectAnimator frontHandleAnimX = ObjectAnimator.ofFloat(frontHandle,"x"/*,point1X,point2X*/,finalX);
        ObjectAnimator frontHandleAnimY = ObjectAnimator.ofFloat(frontHandle,"y"/*,point1Y,point2Y*/,finalY);

        //Compute the final radius of the circle
        double finalRadius = Math.sqrt(Math.pow(profileImage.getWidth(),2) + Math.pow(profileImage.getHeight(),2));
        float finalScaleX = ((float) finalRadius/handle.getWidth())+1;
        float finalScaleY = finalScaleX;

        //Scale animes
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(handle,"scaleX",finalScaleX);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(handle,"scaleY",finalScaleY);

        //Alpha anim
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(handle,"alpha",FINAL_ALPHA);

        //Shield visibility anim
        ObjectAnimator shieldAnimAlpha = ObjectAnimator.ofFloat(shieldView,"alpha",1.0f);

        //Close Menu Button animations
        ObjectAnimator closeBtnAnimAlpha = ObjectAnimator.ofFloat(closeMenuBtn,"alpha",1.0f);
        ObjectAnimator closeBtnAnimRotation = ObjectAnimator.ofFloat(closeMenuBtn,"rotation",180.0f);


        animX.setDuration(200);
        animY.setDuration(200);

        frontHandleAnimX.setDuration(200);
        frontHandleAnimY.setDuration(200);

        //A cubic-bezier curve interpolator for move animes
        Interpolator interpolator = PathInterpolatorCompat.create(0.2f,0.4f,//First Point
                0f,0.5f);//Second control point
        animX.setInterpolator(interpolator);
        frontHandleAnimX.setInterpolator(interpolator);

        animScaleX.setDuration(200);
        animScaleY.setDuration(200);
        animScaleX.setStartDelay(200);
        animScaleY.setStartDelay(200);
        animAlpha.setDuration(400);
        disappearAnim.setDuration(150);
        shieldAnimAlpha.setDuration(200);

        closeBtnAnimAlpha.setDuration(350);
        closeBtnAnimRotation.setDuration(350);


        openMenuAnimatorSet = new AnimatorSet();
        openMenuAnimatorSet.playTogether(animX, animY,animScaleX,animScaleY,animAlpha,shieldAnimAlpha,
                closeBtnAnimAlpha,closeBtnAnimRotation,disappearAnim,frontHandleAnimX,frontHandleAnimY);
        //openMenuAnimatorSet.setInterpolator(new AccelerateInterpolator(1.5f));
        openMenuAnimatorSet.start();
        if(onOpenCloseListener != null)
            onOpenCloseListener.onActionStarted(OnOpenCloseListener.
                    ACTION_TYPE_OPEN);
        openMenuAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //Not invoked !!
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(TAG,"animator with duration:"+animator.getDuration());
                if(animator.getDuration()>=400) {
                    menuOpened = true;
                }
                menuOpened = true;
                handle.setEnabled(true);
                animateMenuItemsOpen();
                openRunning = false;
                if(onOpenCloseListener != null)
                    onOpenCloseListener.onActionEnded(OnOpenCloseListener.
                            ACTION_TYPE_OPEN);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    void animateMenuItemsOpen(){
        int childCount = menuItemsContainer.getChildCount();
        for(int i = 0;i<childCount;i++){
            View view = menuItemsContainer.getChildAt(i);
            animateMenuItemOpen(view,i*10);
        }
    }
    void animateMenuItemOpen(View view,long delay){
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(view,"scaleX",0.0f,1.0f);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(view,"scaleY",0.0f,1.0f);
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(view,"alpha",0,1.0f);
        ObjectAnimator animRotation = ObjectAnimator.ofFloat(view,"rotation",0,360.0f);

        animScaleX.setDuration(100);
        animScaleY.setDuration(100);
        //animAlpha.setDuration(100);
        animRotation.setDuration(200);

        AnimatorSet animSet = new AnimatorSet();
        if(allowItemRotationAnim)
            animSet.playTogether(animScaleX,animScaleY,animAlpha,animRotation);
        else
            animSet.playTogether(animScaleX,animScaleY,animAlpha);
        animSet.setStartDelay(delay);
        animSet.setInterpolator(new OvershootInterpolator(5));
        animSet.start();
    }

    /**
     * Animates the close of menu items container
     */
    void animateMenuItemsClose(){
        int childCount = menuItemsContainer.getChildCount();
        for(int i = childCount-1;i>-1;i--){
            View view = menuItemsContainer.getChildAt(i);
            animateMenuItemClose(view,(childCount-i-1)*30);
        }
    }

    /**
     * Animates the passed view as following :scale downed to 0 ,
     * reduced alpha to 0 and a 360 degree rotation if allowed
     * @param view
     * @param delay delay before starting the animation
     */
    void animateMenuItemClose(View view,long delay){
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(view,"scaleX",1.0f,0.2f);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(view,"scaleY",1.0f,0.2f);
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(view,"alpha",1,0.0f);
        ObjectAnimator animRotation = ObjectAnimator.ofFloat(view,"rotation",360.0f,0);

        animScaleX.setDuration(200);
        animScaleY.setDuration(200);
        animAlpha.setDuration(300);
        animRotation.setDuration(200);

        AnimatorSet animSet = new AnimatorSet();
        if(allowItemRotationAnim)
            animSet.playTogether(animScaleX,animScaleY,animAlpha,animRotation);
        else
            animSet.playTogether(animScaleX,animScaleY,animAlpha);
        animSet.setInterpolator(new OvershootInterpolator(2));
        animSet.setStartDelay(delay);
        animSet.start();
    }

    /**
     * Closes the opened menu with animation
     */
    public void closeMenu(){

        if(closeRunning)
            return;
        closeRunning = true;
        //animating fronhandle appearance
        ObjectAnimator appearAnim = ObjectAnimator.ofFloat(frontHandle,"alpha",1f);
        //Move with scale animation
        float finalX = originalHandleX;
        float finalY = originalHandleY;
        //Move animes
        ObjectAnimator animX = ObjectAnimator.ofFloat(handle,"x",finalX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(handle,"y",finalY);

        ObjectAnimator frontHandleAnimX = ObjectAnimator.ofFloat(frontHandle,"x"/*,point1X,point2X*/,finalX);
        ObjectAnimator frontHandleAnimY = ObjectAnimator.ofFloat(frontHandle,"y"/*,point1Y,point2Y*/,finalY);

        //Scale animes
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(handle,"scaleX",1);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(handle,"scaleY",1);

        //Alpha anim
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(handle,"alpha",1);

        //Shield visibility anim
        ObjectAnimator shieldAnimAlpha = ObjectAnimator.ofFloat(shieldView,"alpha",0.0f);


        //Close Menu Button animations
        ObjectAnimator closeBtnAnimAlpha = ObjectAnimator.ofFloat(closeMenuBtn,"alpha",0.0f);
        ObjectAnimator closeBtnAnimRotation = ObjectAnimator.ofFloat(closeMenuBtn,"rotation",0.0f);

        animX.setDuration(400);
        animY.setDuration(400);

        frontHandleAnimX.setDuration(400);
        frontHandleAnimY.setDuration(400);

        appearAnim.setDuration(200);
        appearAnim.setStartDelay(200);

        animScaleX.setDuration(200);
        animScaleY.setDuration(200);
        animAlpha.setDuration(200);
        shieldAnimAlpha.setDuration(398);

        closeBtnAnimAlpha.setDuration(350);
        closeBtnAnimRotation.setDuration(350);

        closeMenuAnimatorSet = new AnimatorSet();
        closeMenuAnimatorSet.playTogether(animX, animY,animScaleX,animScaleY,animAlpha,shieldAnimAlpha,
                closeBtnAnimAlpha,closeBtnAnimRotation,appearAnim,frontHandleAnimX,frontHandleAnimY);
        closeMenuAnimatorSet.setStartDelay(150);
        //closeMenuAnimatorSet.setInterpolator(OvershootInterpolator);
        animateMenuItemsClose();
        closeMenuAnimatorSet.start();
        if(onOpenCloseListener != null)
            onOpenCloseListener.onActionStarted(OnOpenCloseListener.ACTION_TYPE_CLOSE);
        closeMenuAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.d(TAG,"animator started with duration:"+animator.getDuration());

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(TAG,"animator ended with duration:"+animator.getDuration());
                menuOpened =false;
                closeMenuBtn.setEnabled(true);

                if(animator.getDuration() >= 400)
                    menuOpened =false;
                closeRunning = false;
                if(handleImageRes>0) {
                    frontHandle.setAlpha(1.0f);
                    //frontHandle.setImageResource(handleImageRes);
                }
                if(onOpenCloseListener != null)
                    onOpenCloseListener.onActionEnded(OnOpenCloseListener.
                            ACTION_TYPE_CLOSE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /**
     * Returns all Menu Items Views....
     * @return list of menu items views
     */
    public List<AnimatedMenuItem> getMenuItems(){
        List<AnimatedMenuItem>  ret = new ArrayList<>();
        int childCount = menuItemsContainer.getChildCount();
        for(int i =0 ;i<childCount;i++){
            View view = menuItemsContainer.getChildAt(i);
            ret.add((AnimatedMenuItem)view);
        }
        return ret;
    }

    /**
     * Use to detect the open & close actions of the menu
     */
    public interface OnOpenCloseListener{
        int ACTION_TYPE_OPEN = 0;
        int ACTION_TYPE_CLOSE = 1;

        /**
         * Called at the start of the open or close actions
         * @param actionType open or close
         */
        void onActionStarted( int actionType );

        /**
         * Called at the end of the open or close actions
         * @param actionType open or close
         */
        void onActionEnded(int actionType);
    }


    public OnOpenCloseListener getOnOpenCloseListener() {
        return onOpenCloseListener;
    }

    public void setOnOpenCloseListener(OnOpenCloseListener onOpenCloseListener) {
        this.onOpenCloseListener = onOpenCloseListener;
    }

    public boolean isMenuOpened() {
        return menuOpened;
    }
    public boolean isOpenRunning() {
        return openRunning;
    }

    public boolean isCloseRunning() {
        return closeRunning;
    }



}
