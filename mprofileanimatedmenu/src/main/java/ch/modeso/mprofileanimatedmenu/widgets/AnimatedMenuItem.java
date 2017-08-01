package ch.modeso.mprofileanimatedmenu.widgets;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;


import ch.modeso.mprofileanimatedmenu.R;

/**
 * Created by user on 26/07/2017.
 */

public class AnimatedMenuItem extends android.support.v7.widget.AppCompatImageView  {


    private int index =-1;
    public AnimatedMenuItem(Context context) {
        super(context);
        init();
    }

    public AnimatedMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatedMenuItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public boolean isAnimateOnClickEnabled() {
        return animateOnClickEnabled;
    }

    public void setAnimateOnClickEnabled(boolean animateOnClickEnabled) {
        this.animateOnClickEnabled = animateOnClickEnabled;
    }

    public OnMenuItemClicked getOnItemClicked() {
        return onItemClicked;
    }

    public void setOnItemClicked(OnMenuItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
        setOnClickListener(new PrivateOnclickListener() {
            @Override
            public void onClick(View view) {
                if(animateOnClickEnabled)
                    animateClickableView(view);
                AnimatedMenuItem.this.onItemClicked.onMenuItemClicked(AnimatedMenuItem.this);
            }
        });
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if(l instanceof PrivateOnclickListener)
            super.setOnClickListener(l);
        else
            throw new IllegalArgumentException("OnClickListener is not " +
                    "acceptable here ,use OnMenuItemClicked ");
    }

    private void animateClickableView(View v){
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(v,"scaleX",1.0f,1.2f,1.0f);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(v,"scaleY",1.0f,1.2f,1.0f);

        animScaleX.setDuration(180);
        animScaleY.setDuration(180);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setInterpolator(new BounceInterpolator());
        animSet.playTogether(animScaleX, animScaleY);
        animSet.start();
    }
    public final int getIndex(){
        if(index>0)
            return  index;
        int id = getId();
        if(R.id.option1 == id )
            return  1;
        if(R.id.option2 == id )
            return  2;
        if(R.id.option3 == id )
            return  3;
        if(R.id.option4 == id )
            return  4;
        if(R.id.option5 == id )
            return  5;
        return -1;
    }
    void setIndex(int index){
        this.index = index;
    }

    private void init(){
        VectorDrawableCompat vdc =  VectorDrawableCompat.create(getResources(),
                R.drawable.bg_vector,getContext().getTheme());
        setBackground(vdc);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private boolean animateOnClickEnabled =  false;
    private  OnMenuItemClicked onItemClicked;
    public interface OnMenuItemClicked{
        void onMenuItemClicked(AnimatedMenuItem item);
    }

    static abstract class PrivateOnclickListener implements OnClickListener{

    }
}
